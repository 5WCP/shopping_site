package com.example.shopping_site.entity;

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
	private Long productId;
	
	@Column(name = "amount")
	private int amount;
	
	@Column(name = "state")
	private String state;

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
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
		
}
