package com.nestor.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.nestor.common.BizException;
import com.nestor.common.ParameterException;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.Order;
import com.nestor.entity.WxAddress;
import com.nestor.enums.OrderStatus;
import com.nestor.enums.PayStatus;
import com.nestor.repository.AddressRepository;
import com.nestor.repository.OrderRepository;
import com.nestor.repository.ProductRepository;
import com.nestor.service.OrderService;
import com.nestor.util.CheckUtil;
import com.nestor.util.IdUtil;
import com.nestor.util.JacksonUtil;
import com.nestor.util.RandomUtil;
import com.nestor.vo.ProductWithSingleImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	@Qualifier("wxPayService")
	private WxPayServiceImpl wxPayService;

	@Override
	@Transactional
	public PayResponse generateOrder(SimpleOrder simpleOrder) {
	    // 生订单
		Order order = new Order();
		while (true) {
			String orderNumber = RandomUtil.generateOrderNumber();
			Order queryOrder = new Order();
			queryOrder.setOrderNumber(orderNumber);
			Example<Order> query = Example.of(queryOrder);
			if (!orderRepository.exists(query)) {
				order.setOrderNumber(orderNumber);
				break;
			}
		}
		order.setOpenid(simpleOrder.getOpenid());
		order.setExpectedDeliveryTime(simpleOrder.getExpectedDeliveryTime());
		order.setLabel(simpleOrder.getLabel());
		order.setLeaveMessage(simpleOrder.getLeaveMessage());
		order.setRemark(simpleOrder.getRemark());
		// 根据product id获取商品信息
		ProductWithSingleImage product = productRepository.getProductWithSingleImage(simpleOrder.getProductId());
		// 验证商品是否存在
		CheckUtil.notNull(product, "product id 不正确或该商品已被删除");
		// 验证购买数量是否超过库存
		CheckUtil.notLessThan(product.getProductStock(), Long.valueOf(simpleOrder.getBuyAmount()), "库存不足");
		order.setProductId(product.getProductId());
		order.setProductName(product.getProductName());
		order.setProductPrice(product.getProductDiscountPrice() == null ? product.getProductOriginalPrice() : product.getProductDiscountPrice());
		order.setProductDescription(product.getProductDescription());
		order.setProductImageUrl(product.getImageUrl());
		order.setBuyAmount(simpleOrder.getBuyAmount());
		// 计算支付金额
		order.setPayAmount(order.getProductPrice().multiply(BigDecimal.valueOf(order.getBuyAmount())));
		// 根据address id 获取地址信息
		Optional<WxAddress> addressOptional = addressRepository.findById(simpleOrder.getAddressId());
		if (!addressOptional.isPresent()) {
			throw new ParameterException("address id 不正确或该地址已被删除");
		}
		WxAddress address = addressOptional.get();
		order.setArea(address.getLocation());
		order.setAddressDetail(address.getDetailAdd());
		order.setUsername(address.getUsername());
		order.setPhoneNumber(address.getTelnum());
		// 设置订单状态
		order.setOrderStatus(OrderStatus.PENDING_PAY.toString());
		order.setPayStatus(PayStatus.PENDING.toString());
		// 保存订单
		Order orderInDB = orderRepository.save(order);
		// 获取微信支付的预支付订单id
		PayResponse payResponse = getPreOrderInfo(orderInDB.getOrderId(), orderInDB.getOpenid(), order.getPayAmount(), order.getProductName());
		return payResponse;
	}

	@Override
	@Transactional
	public void handleCallback(String callBackBody) {
		PayResponse payResponse = wxPayService.asyncNotify(callBackBody);
		log.info("order id is {}, callback payResponse is {}", payResponse.getOrderId(), JacksonUtil.object2JsonStr(payResponse));
		// 验证金额
		String orderAmount = payResponse.getOrderAmount().toString();
		Optional<Order> orderOptional = orderRepository.findById(payResponse.getOrderId());
		if (!orderOptional.isPresent()) {
			throw new BizException("微信支付回调时，发生订单不存在异常");
		}
		Order orderInDB = orderOptional.get();
		String payAmount = orderInDB.getPayAmount().toString();
		if (!payAmount.equals(orderAmount)) {
			throw new BizException("微信支付回调时，发生订单支付金额不一致异常");
		}
		// 修改订单状态
		orderInDB.setPayStatus(PayStatus.SUCCESS.toString());
		orderInDB.setOrderStatus(OrderStatus.PENDING_DELIVERY.toString());
		orderInDB.setPayTime(LocalDateTime.now());
	}

	private PayResponse getPreOrderInfo(String orderId, String openid, BigDecimal payAmount, String productName) {
		PayRequest payRequest = new PayRequest();
		log.info("orderId: {}, openid: {}", orderId, openid);
		payRequest.setOpenid(openid);
		payRequest.setOrderAmount(payAmount.doubleValue());
		payRequest.setOrderId(orderId);
		payRequest.setOrderName(productName);
		payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

		PayResponse payResponse = wxPayService.pay(payRequest);
		payResponse.setOrderId(orderId);
		return payResponse;
	}

}
