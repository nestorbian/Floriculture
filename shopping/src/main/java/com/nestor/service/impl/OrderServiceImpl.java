package com.nestor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nestor.entity.OrderParam;
import com.nestor.repository.ProductRepository;
import com.nestor.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional
	public void generateOrder(List<OrderParam> orderParams) {
//		OrderMaster orderMaster = new OrderMaster();
//		String orderMasterId = IdUtil.generateId();
//		orderMaster.setOrderMasterId(orderMasterId);
//		// 暂时写死
//		orderMaster.setBuyerName("卞泽阳");
//		orderMaster.setBuyerOpenid("111111");
//		orderMaster.setDiscountAmount(BigDecimal.ZERO);
//		orderMaster.setMerchantAddress("塘镇");
//		orderMaster.setMerchantName("肯德基");
//		orderMaster.setPayStatus(PayStatus.PENDING);
//		BigDecimal totalAmount = BigDecimal.ZERO;
//		List<OrderDetail> orderDetails = new ArrayList<>();
//		
//		for (OrderParam item : orderParams) {
//			if (item.getProductQuantity() <= 0) {
//				throw new BizException("商品数量不合法");
//			}
//			
//			Product product = productRepository.findById(item.getProductId()).get();
//			CheckUtil.isNull(product, "该商品不存在");
//			
//			OrderDetail orderDetail = new OrderDetail();
//			orderDetail.setOrderDetailId(IdUtil.generateId());
//			orderDetail.setOrderMasterId(orderMasterId);
//			orderDetail.setProductDescription(product.getProductDescription());
//			orderDetail.setProductIcon(product.getProductIcon());
//			orderDetail.setProductName(product.getProductName());
//			orderDetail.setProductPrice(product.getProductPrice());
//			orderDetail.setProductQuantity(item.getProductQuantity());
//			
//			totalAmount = totalAmount.add(product.getProductPrice().multiply(BigDecimal.valueOf(item.getProductQuantity())));
//			orderDetails.add(orderDetail);
//		}
//		
//		orderMaster.setTotalAmount(totalAmount);
//		orderMaster.setPayAmount(totalAmount.subtract(BigDecimal.ZERO));
//		orderMaster.setOrderDetails(orderDetails);
//		
//		orderMasterRepository.save(orderMaster);
	}

}
