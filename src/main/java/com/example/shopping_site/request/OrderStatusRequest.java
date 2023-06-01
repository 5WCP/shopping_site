package com.example.shopping_site.request;

import com.example.shopping_site.entity.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusRequest {
	
	@JsonProperty("order_status")
	private OrderStatus orderStatus;
	
	@JsonProperty("userid")
	private String userId;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("address")
	private String address;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
