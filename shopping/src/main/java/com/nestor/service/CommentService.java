package com.nestor.service;

import java.util.ArrayList;

import com.nestor.entity.Comment;
import com.nestor.vo.CommentView;

public interface CommentService {
	public String addComment(Comment comment);
	//分页查询所有评论
	public ArrayList<CommentView> findComments(Integer page, Integer pageSize);
	
	//分页查询商品粒度评论
	public ArrayList<CommentView> findProComment(String productId,Integer page, Integer pageSize);

}
