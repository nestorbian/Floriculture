package com.nestor.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nestor.entity.Comment;
import com.nestor.repository.CommentRespository;
import com.nestor.repository.ProductImageRepository;
import com.nestor.service.CommentService;
import com.nestor.vo.CommentView;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRespository comRes;
	@Autowired
	private ProductImageRepository piRes;

	@Override
	public String addComment(Comment comment) {
		// TODO Auto-generated method stub
		String productImage = piRes.findByProductId(comment.getId().getProductId()).get(0).getImageUrl();
		comment.setProductImage(productImage);
		comRes.save(comment);
		return "保存成功！";
	}

	@Override
	public ArrayList<CommentView> findComments(Integer page, Integer pageSize) {
		// TODO Auto-generated method stub		
		return comRes.findComment(page, pageSize);
	}

}
