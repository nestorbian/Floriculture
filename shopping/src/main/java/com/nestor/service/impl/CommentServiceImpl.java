package com.nestor.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.common.BizException;
import com.nestor.entity.Comment;
import com.nestor.entity.WxOrder;
import com.nestor.repository.CommentRespository;
import com.nestor.repository.OrderRepository;
import com.nestor.repository.ProductImageRepository;
import com.nestor.service.CommentService;
import com.nestor.vo.CommentView;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRespository comRes;
	@Autowired
	private ProductImageRepository piRes;
	@Autowired
	private OrderRepository ordRes;

	@Override
	public String addComment(Comment comment) {
		// TODO Auto-generated method stub

		Optional<WxOrder> orderOptional = ordRes.findById(comment.getId().getOrderId());
		

		if (!orderOptional.isPresent()) {
			throw new BizException("该订单不存在，请重新下单");
		}
		WxOrder order = orderOptional.get();
		if (!comment.getId().getOpenid().equals(order.getOpenid())) {
			throw new BizException("不合法评论！");
		}
		
		comment.setProductImage(order.getProductImageUrl());
		comment.getId().setProductId(order.getProductId());
		comRes.save(comment);
		return "ok";
	}

	@Override
	public ArrayList<CommentView> findComments(Integer page, Integer pageSize) {
		// TODO Auto-generated method stub		
		return comRes.findComment(page, pageSize);
	}

	@Override
	public ArrayList<CommentView> findProComment(String productId, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		return comRes.findProComment(productId, page, pageSize);
	}

}
