package com.example.shopping_site.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderStatusResponse {
	
	private String message;
	
	@JsonProperty("cart_info_list")
	private	List<RespProInfo> cartInfoList;
	
	@JsonProperty("ord_num_info_list")
	private List<OrdNumInfo> ordNumList;
	
	private int re;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<RespProInfo> getCartInfoList() {
		return cartInfoList;
	}

	public void setCartInfoList(List<RespProInfo> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}

	public List<OrdNumInfo> getOrdNumList() {
		return ordNumList;
	}

	public void setOrdNumList(List<OrdNumInfo> ordNumList) {
		this.ordNumList = ordNumList;
	}

	public int getRe() {
		return re;
	}

	public void setRe(int re) {
		this.re = re;
	}

	public OrderStatusResponse() {
		
	}

	public OrderStatusResponse(String message) {
		this.message = message;
	}

	public OrderStatusResponse(List<RespProInfo> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}

	public OrderStatusResponse(List<OrdNumInfo> ordNumList, int re) {
		this.ordNumList = ordNumList;
		this.re = re;
	}
		
}
