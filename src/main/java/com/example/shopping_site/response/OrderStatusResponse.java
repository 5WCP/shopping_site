package com.example.shopping_site.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusResponse {
	
	private String message;
	
	@JsonProperty("cart_info_list")
	private	List<RespProInfo> cartInfoList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<RespProInfo> getCartProList() {
		return cartInfoList;
	}

	public void setCartProList(List<RespProInfo> cartProList) {
		this.cartInfoList = cartProList;
	}

	public OrderStatusResponse() {
		
	}

	public OrderStatusResponse(String message) {
		this.message = message;
	}

	public OrderStatusResponse(List<RespProInfo> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}
		
}
