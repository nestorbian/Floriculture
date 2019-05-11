package com.nestor.repository;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.nestor.entity.Comment;
import com.nestor.entity.CommentKey;
import com.nestor.vo.CommentView;

public interface CommentRespository   extends JpaRepository<Comment, CommentKey> {
	@Transactional
	@Query(value = "select c.image_urls  as imageUrls ,c.product_image as productImage,c.value as value,"
			+ "c.update_time as updateTime ,c.text as text,c.product_id as productId,u.nickname as nickname,u.avatarurl as avatarurl ,u.location as  location \r\n" + 
			"	from t_comment  as c \r\n" + 
			"	left join t_wx_user u\r\n" + 
			"	  on c.openid = u.openid\r\n", nativeQuery = true)  
	@Modifying  
	public ArrayList<CommentView> findComment(String page, String pageSize); 
}
