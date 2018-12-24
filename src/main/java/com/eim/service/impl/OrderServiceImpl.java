package com.eim.service.impl;

import com.eim.convert.OrderMaster2OrderMasterDTOConvert;
import com.eim.dataObject.OrderDetail;
import com.eim.dataObject.OrderMaster;
import com.eim.dataObject.ProductInfo;
import com.eim.dto.CatDTO;
import com.eim.dto.OrderMasterDto;
import com.eim.enums.ExceptionEnum;
import com.eim.enums.OrderStatusEnum;
import com.eim.enums.PayStatusEnum;
import com.eim.exception.SellException;
import com.eim.repository.OrderDetailRepository;
import com.eim.repository.OrderMasterRepository;
import com.eim.service.OrderService;
import com.eim.service.PayService;
import com.eim.service.ProductInfoService;
import com.eim.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zy on 2018/12/11.
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderMasterDto createOrder(OrderMasterDto orderMasterDto) {

        String orderId = KeyUtil.uniKey();

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1.查询商品（数量，价格）
           //1)遍历购物车
        for (OrderDetail orderDetail:orderMasterDto.getOrderDetailList()){
           //2)根据购物车中的*商品id* 查询 商品详情
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                log.error("【创建订单】 商品不存在 productInfo={}",productInfo);
                throw new SellException(ExceptionEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单总价
            orderAmount= productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //3-1 orderdetail 入库
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.uniKey());
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3.写入订单数量（orderMaster,orderDetail）
        //3-2 orderMaster入库
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        List<CatDTO> catDTOList = orderMasterDto.getOrderDetailList().stream().map(e ->
                new CatDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseStock(catDTOList);

        return orderMasterDto;
    }

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    @Override
    public OrderMasterDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            log.error("【查询单个订单】 订单不存在 orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        OrderMasterDto orderMasterDto = OrderMaster2OrderMasterDTOConvert.convert(orderMaster);
        List<OrderDetail> byOrderId = orderDetailRepository.findByOrderId(orderId);
        if(byOrderId == null){
            log.error("【查询单个订单】 订单详情不存在 orderDetailList={}",byOrderId);
            throw new SellException(ExceptionEnum.ORDER_DETAIL_NOT_EXIST);
        }
        orderMasterDto.setOrderDetailList(byOrderId);
        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderMasterDto> orderMasterDtoList = OrderMaster2OrderMasterDTOConvert.convert(orderMasterList);
        Page<OrderMasterDto> page = new PageImpl<>(orderMasterDtoList, pageable, orderMasterPage.getTotalElements());
        return page;
    }


    /**
     * 取消订单
     * @param orderMasterDto
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDto cancleOrder(OrderMasterDto orderMasterDto) {
        //判断订单状态
        OrderMaster one = orderMasterRepository.findOne(orderMasterDto.getOrderId());
        if(one == null){
            log.error("【取消订单】 订单不存在 orderMaster={}",one);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        if(!one.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 orderStatus={}",one.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        one.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(one,orderMasterDto);
        OrderMaster save = orderMasterRepository.save(one);
        if(save == null){
            log.error("【取消订单】 更新状态失败 orderMaster={}",save);
            throw new SellException(ExceptionEnum.UPDATE_ERROR);
        }
        //返回库存
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMasterDto.getOrderId());
        if(orderDetailList == null){
            log.error("【取消订单】 订单详情不存在 orderDetailList={}",orderDetailList);
            throw new SellException(ExceptionEnum.ORDER_DETAIL_NOT_EXIST);
        }
        List<CatDTO> catDTOList = orderDetailList.stream().map(e ->
                new CatDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.increaseStock(catDTOList);
        //如果已支付 退款
        if(orderMasterDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payService.reFund(orderMasterDto);
        }
        return OrderMaster2OrderMasterDTOConvert.convert(one);
    }

    /**
     * 完结订单
     * @param orderMasterDto
     * @return
     */
    @Override
    @Transactional
    public OrderMasterDto finishOrder(OrderMasterDto orderMasterDto) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderMasterDto.getOrderId());
        if(orderMaster == null){
            log.error("【完结订单】 订单不存在 orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】 订单状态不正确 orderStatus={}",orderMaster.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //更新完结订单
        orderMaster.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        orderMasterRepository.save(orderMaster);
        BeanUtils.copyProperties(orderMaster,orderMasterDto);
        return orderMasterDto;
    }

    /**
     * 支付订单
     * @param orderMasterDto
     * @return
     */
    @Override
    public OrderMasterDto paidOrder(OrderMasterDto orderMasterDto) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderMasterDto.getOrderId());
        if(orderMaster == null){
            log.error("【支付订单】 订单不存在 orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        if(!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】 支付订单状态不正确 orderStatus={}",orderMaster.getOrderStatus());
            throw new SellException(ExceptionEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderMaster.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】 订单支付状态不正确 payStatus={}",orderMaster.getPayStatus());
            throw new SellException(ExceptionEnum.PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster save = orderMasterRepository.save(orderMaster);
        if(save == null){
            log.error("【支付订单】订单支付更新失败 orderMaster={}",orderMaster);
            throw new SellException(ExceptionEnum.UPDATE_ERROR);
        }
        return OrderMaster2OrderMasterDTOConvert.convert(orderMaster);
    }

    /**
     * 后台使用
     * 查询订单列表
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderMasterDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();

        List<OrderMasterDto> orderMasterDtoList = OrderMaster2OrderMasterDTOConvert.convert(orderMasterList);

        Page<OrderMasterDto> page = new PageImpl<>(orderMasterDtoList, pageable, orderMasterPage.getTotalElements());
        return page;
    }
}
