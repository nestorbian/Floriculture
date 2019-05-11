package com.nestor.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "t_comment")
@Data
public class Comment  {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CommentKey id;
	
	@Column(name="image_urls")
	private String imageUrls;//图片地址
	private String text;//评论内容
	private String value;//星级
	private String productImage;//商品图片
	@CreationTimestamp
	private LocalDateTime createTime;//创建时间
	@UpdateTimestamp
	private LocalDateTime updateTime;//修改时间
	public CommentKey getId() {
		return id;
	}
	public void setId(CommentKey id) {
		this.id = id;
	}

	public String getImageUrls() {
		return imageUrls;
	}
	public void setImageUrls(String imageUrls) {
		this.imageUrls = imageUrls;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public LocalDateTime getCreateTime() {
		return createTime;
	}
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

}
