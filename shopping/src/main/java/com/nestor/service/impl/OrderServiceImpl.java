package com.nestor.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.nestor.common.BizException;
import com.nestor.common.ParameterException;
import com.nestor.dto.SimpleOrder;
import com.nestor.entity.Result;
import com.nestor.entity.WxAddress;
import com.nestor.entity.WxOrder;
import com.nestor.enums.OrderStatus;
import com.nestor.enums.PayStatus;
import com.nestor.query.OrderQuery;
import com.nestor.repository.AddressRepository;
import com.nestor.repository.OrderRepository;
import com.nestor.repository.ProductRepository;
import com.nestor.service.OrderService;
import com.nestor.util.CheckUtil;
import com.nestor.util.JacksonUtil;
import com.nestor.util.RandomUtil;
import com.nestor.vo.CountOrderView;
import com.nestor.vo.ProductWithSingleImage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

	@Value("${image.product.base-url}")
	private String baseImageUrl;

	@Override
	@Transactional
	public PayResponse generateOrder(SimpleOrder simpleOrder) {
	    // 生订单
		WxOrder order = new WxOrder();
		while (true) {
			String orderNumber = RandomUtil.generateOrderNumber();
			WxOrder queryOrder = new WxOrder();
			queryOrder.setOrderNumber(orderNumber);
			Example<WxOrder> query = Example.of(queryOrder);
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
		order.setProductImageUrl(product.getImageUrl().substring(baseImageUrl.length()));
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
		WxOrder orderInDB = orderRepository.save(order);
		// 获取微信支付的预支付订单id
		PayResponse payResponse = getPreOrderInfo(orderInDB.getOrderId(), orderInDB.getOpenid(), orderInDB.getPayAmount(), orderInDB.getProductName());
		return payResponse;
	}

	@Override
	@Transactional
	public void handleCallback(String callBackBody) {
		PayResponse payResponse = wxPayService.asyncNotify(callBackBody);
		log.info("order id is {}, callback payResponse is {}", payResponse.getOrderId(), JacksonUtil.object2JsonStr(payResponse));
		// 验证金额
		String orderAmount = payResponse.getOrderAmount().toString();
		Optional<WxOrder> orderOptional = orderRepository.findById(payResponse.getOrderId());
		if (!orderOptional.isPresent()) {
			throw new BizException("微信支付回调时，发生订单不存在异常");
		}
		WxOrder orderInDB = orderOptional.get();
		String payAmount = orderInDB.getPayAmount().toString();
		if (!payAmount.equals(orderAmount)) {
			throw new BizException("微信支付回调时，发生订单支付金额不一致异常");
		}
		// 修改订单状态
		orderInDB.setPayStatus(PayStatus.SUCCESS.toString());
		orderInDB.setOrderStatus(OrderStatus.PENDING_DELIVERY.toString());
		orderInDB.setPayTime(LocalDateTime.now());
		orderRepository.save(orderInDB);
		// 商品减库存 增加销量
        productRepository.updateStockAndSaleVolume(orderInDB.getBuyAmount(), orderInDB.getProductId());
	}

	@Override
	public PayResponse continuePay(String openid, String id) {
		Optional<WxOrder> orderOptional = orderRepository.findById(id);
		if (!orderOptional.isPresent()) {
			throw new BizException("该订单不存在，请重新下单");
		}
		WxOrder order = orderOptional.get();
		if (!order.getOpenid().equals(openid)) {
			throw new BizException("不合法订单");
		}
		// 获取微信支付的预支付订单id
		PayResponse payResponse = getPreOrderInfo(order.getOrderId(), order.getOpenid(), order.getPayAmount(), order.getProductName());
		return payResponse;
	}

	@Override
	@Transactional
	public void updateTrackingNumber(WxOrder order) {
		Optional<WxOrder> orderOptional = orderRepository.findById(order.getOrderId());
		if (!orderOptional.isPresent()) {
			throw new BizException("该订单不存在，请重新下单");
		}
		WxOrder orderInDB = orderOptional.get();
		if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_DELIVERY.toString())
				&& !orderInDB.getOrderStatus().equals(OrderStatus.PENDING_RECEIVE.toString())) {
			throw new BizException("订单状态不正确");
		}
		orderInDB.setTrackingNumber(order.getTrackingNumber());
		orderInDB.setOrderStatus(OrderStatus.PENDING_RECEIVE.toString());
		orderRepository.save(orderInDB);
	}

	@Override
	public void updateOrderStatus(String id, OrderStatus orderStatus) {
		Optional<WxOrder> orderOptional = orderRepository.findById(id);
		if (!orderOptional.isPresent()) {
			throw new BizException("该订单不存在，请重新下单");
		}
		WxOrder orderInDB = orderOptional.get();
		// 验证当前订单的状态是否可以改变成目标状态
		if (orderStatus.equals(OrderStatus.PENDING_COMMENT)) {
			if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_RECEIVE.toString())) {
				throw new BizException("订单状态不正确");
			}
		}

		if (orderStatus.equals(OrderStatus.CLOSE)) {
			if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_PAY.toString())) {
				throw new BizException("订单状态不正确");
			}
		}

		if (orderStatus.equals(OrderStatus.PENDING_REFUND)) {
			if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_RECEIVE.toString())
					&& !orderInDB.getOrderStatus().equals(OrderStatus.PENDING_DELIVERY.toString())
					&& !orderInDB.getOrderStatus().equals(OrderStatus.PENDING_COMMENT.toString())) {
				throw new BizException("订单状态不正确");
			}
		}
		
		if (orderStatus.equals(OrderStatus.FINISH)) {
			if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_COMMENT.toString())) {
				throw new BizException("订单状态不正确");
			}
		}

		if (orderStatus.equals(OrderStatus.FINISH_REFUND)) {
			if (!orderInDB.getOrderStatus().equals(OrderStatus.PENDING_REFUND.toString())) {
				throw new BizException("订单状态不正确");
			}
			// 如果订单状态为申请退款就去调用微信退款api
			RefundRequest refundRequest = new RefundRequest();
			refundRequest.setOrderId(orderInDB.getOrderId());
			refundRequest.setOrderAmount(orderInDB.getPayAmount().doubleValue());
			refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
			try {
				RefundResponse response = wxPayService.refund(refundRequest);
			} catch (Exception e) {
				log.error("订单id: [{}], 订单编号: [{}], 退款金额: [{}], 退款失败！详细异常信息: [{}]",
						orderInDB.getOrderId(), orderInDB.getOrderNumber(), orderInDB.getPayAmount().doubleValue(),
						ExceptionUtils.getStackTrace(e));
				throw new BizException("退款失败");
			}

		}

		orderInDB.setOrderStatus(orderStatus.toString());
		orderRepository.save(orderInDB);
	}

	@Override
	public Page<WxOrder> listOrderByOrderStatus(String openid, String orderStatus, int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize,
				Sort.by(Sort.Direction.DESC, "orderTime"));
		WxOrder query = new WxOrder();
		query.setOpenid(openid);
		if (!"ALL".equals(orderStatus)) {
			query.setOrderStatus(orderStatus);
		}
		Example<WxOrder> wxOrderExample = Example.of(query);
		Page<WxOrder> page = orderRepository.findAll(wxOrderExample, pageable);
//		Page<WxOrder> page = orderRepository.findByOpenidAndOrderStatus(openid, orderStatus.toString(), pageable);
		page.getContent().stream().forEach(item -> {
		    item.setProductImageUrl(baseImageUrl.concat(item.getProductImageUrl()));
            item.setOrderStatus(OrderStatus.parse(item.getOrderStatus()).getDesc());
            item.setPayStatus(PayStatus.parse(item.getPayStatus()).getDesc());
        });
		return page;
	}

	@Override
	public Page<WxOrder> listOrderByOrderQuery(OrderQuery orderQuery) {
		Pageable pageable = PageRequest.of(orderQuery.getPageNumber() - 1, orderQuery.getPageSize(),
				Sort.by(Sort.Direction.DESC, "orderTime"));
		WxOrder order = new WxOrder();
		if (!StringUtils.isEmpty(orderQuery.getOrderNumber())) {
			order.setOrderNumber(orderQuery.getOrderNumber());
		}
		if (!StringUtils.isEmpty(orderQuery.getOrderStatus())) {
			order.setOrderStatus(orderQuery.getOrderStatus());
		}
		Example<WxOrder> example = Example.of(order);
		Page<WxOrder> page = orderRepository.findAll(example, pageable);
		page.getContent().stream().forEach(item -> {
			item.setProductImageUrl(baseImageUrl.concat(item.getProductImageUrl()));
			item.setOrderStatus(OrderStatus.parse(item.getOrderStatus()).getDesc());
			item.setPayStatus(PayStatus.parse(item.getPayStatus()).getDesc());
		});
		return page;
	}

	@Override
	public WxOrder getOrderById(String openid, String orderId) {
		Optional<WxOrder> orderOptional = orderRepository.findById(orderId);
		if (!orderOptional.isPresent()) {
			throw new BizException("该订单不存在");
		}
		WxOrder order = orderOptional.get();
		if (!openid.equals(order.getOpenid())) {
			throw new BizException("该用户订单不存在");
		}
		order.setProductImageUrl(baseImageUrl.concat(order.getProductImageUrl()));
		order.setOrderStatus(OrderStatus.parse(order.getOrderStatus()).getDesc());
		order.setPayStatus(PayStatus.parse(order.getPayStatus()).getDesc());
		return order;
	}

    @Override
    public WxOrder getOrderById(String orderId) {
        Optional<WxOrder> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new BizException("该订单不存在");
        }
        WxOrder order = orderOptional.get();
        order.setProductImageUrl(baseImageUrl.concat(order.getProductImageUrl()));
        order.setOrderStatus(OrderStatus.parse(order.getOrderStatus()).getDesc());
        order.setPayStatus(PayStatus.parse(order.getPayStatus()).getDesc());
        return order;
    }

    @Override
	public CountOrderView countOrder(String openid) {
		CountOrderView countOrderView = new CountOrderView();
		WxOrder query = new WxOrder();
		query.setOpenid(openid);
		// 待支付
		query.setOrderStatus(OrderStatus.PENDING_PAY.toString());
		Example<WxOrder> wxOrderExample = Example.of(query);
		long pendingPay = orderRepository.count(wxOrderExample);
		countOrderView.setPendingPay(pendingPay);
		// 待发货
		query.setOrderStatus(OrderStatus.PENDING_DELIVERY.toString());
		wxOrderExample = Example.of(query);
		long pendingDelivery = orderRepository.count(wxOrderExample);
		countOrderView.setPendingDelivery(pendingDelivery);
		// 待收货
		query.setOrderStatus(OrderStatus.PENDING_RECEIVE.toString());
		wxOrderExample = Example.of(query);
		long pendingReceive = orderRepository.count(wxOrderExample);
		countOrderView.setPendingReceive(pendingReceive);
		// 待评价
		query.setOrderStatus(OrderStatus.PENDING_COMMENT.toString());
		wxOrderExample = Example.of(query);
		long pendingComment = orderRepository.count(wxOrderExample);
		countOrderView.setPendingComment(pendingComment);
		// 待退款
		query.setOrderStatus(OrderStatus.PENDING_REFUND.toString());
		wxOrderExample = Example.of(query);
		long pendingRefund = orderRepository.count(wxOrderExample);
		countOrderView.setPendingRefund(pendingRefund);
		return countOrderView;
	}

	private PayResponse getPreOrderInfo(String orderId, String openid, BigDecimal payAmount, String productName) {
		PayRequest payRequest = new PayRequest();
		log.info("orderId: [{}], openid: [{}]", orderId, openid);
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
