package com.nestor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommentKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "comment_number")
    private String commentNumber;
     
    @Column(name = "openid")
    private String openid;
    
   @Column(name = "product_id")
    private String productId;

	@Override
	public String toString() {
		return "CommentKey [commentNumber=" + commentNumber + ", openid=" + openid + ", productId=" + productId + "]";
	}

	public String getCommentNumber() {
		return commentNumber;
	}

	public void setCommentNumber(String commentNumber) {
		this.commentNumber = commentNumber;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
