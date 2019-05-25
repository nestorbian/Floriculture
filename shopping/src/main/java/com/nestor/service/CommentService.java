package com.nestor.service;

import java.util.ArrayList;

import com.nestor.entity.Comment;
import com.nestor.vo.CommentView;

public interface CommentService {
	public String addComment(Comment comment);

	public ArrayList<CommentView> findComments(Integer page, Integer pageSize);

}
