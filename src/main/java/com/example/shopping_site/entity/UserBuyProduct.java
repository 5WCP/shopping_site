package com.example.shopping_site.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

@SuppressWarnings("serial")
public class UserBuyProduct implements Serializable {

	private String userId;

	private String productId;
	
	private LocalDateTime updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public UserBuyProduct(String userId, String productId, LocalDateTime updateTime) {
		this.userId = userId;
		this.productId = productId;
		this.updateTime = updateTime;
	}

	public UserBuyProduct() {
		
	}
	
}
