package com.example.shopping_site.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserBuyProduct implements Serializable {

	private String userId;

	private Long productId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public UserBuyProduct(String userId, long productId) {
		this.userId = userId;
		this.productId = productId;
	}

	public UserBuyProduct() {
		
	}
	
}
