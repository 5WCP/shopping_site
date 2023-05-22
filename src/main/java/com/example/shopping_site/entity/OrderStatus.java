package com.example.shopping_site.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "order_status")
@IdClass(value = UserBuyProduct.class)
public class OrderStatus {
	
	@Id
	@Column(name = "userid")
	private String userId;
	
	@Id
	@Column(name = "product_id")
	private String productId;
	
	@Id
	@Column(name = "update_time")
	private LocalDateTime updateTime;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "state")
	private String state;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public OrderStatus() {
		
	}

	public OrderStatus(String userId, String productId, LocalDateTime updateTime, 
			Integer amount, String state) {
		this.userId = userId;
		this.productId = productId;
		this.updateTime = updateTime;
		this.amount = amount;
		this.state = state;
	}

		
}
