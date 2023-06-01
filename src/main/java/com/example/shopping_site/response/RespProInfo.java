package com.example.shopping_site.response;

import java.time.LocalDateTime;

public class RespProInfo {
	
	private String userId;
	
	private String productId;
	
	private String productName;
	
	private Integer price;
	
	private Integer stock;
	
	private String productPicture;
	
	private Integer amount;
	
	private String state;
	
	private LocalDateTime updateTime;
	
	private String phone;
	
	private String address;
	
	private String orderNumber;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public RespProInfo() {
		
	}

	public RespProInfo(String productId, String productName, Integer price, Integer stock, 
			String productPicture, String userId) { // 搜尋商品
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.productPicture = productPicture;
		this.userId = userId;
	}

	public RespProInfo(String productId, String productName, Integer price, String productPicture, 
			Integer amount, LocalDateTime updateTime, Integer stock) { // 購物車商品
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.productPicture = productPicture;
		this.amount = amount;
		this.updateTime = updateTime;
		this.stock = stock;
	}

	public RespProInfo(String productId, String productName, Integer price, Integer amount, String state,
			LocalDateTime updateTime, String phone, String address, String orderNumber) { 
		// 買家商品資料(包含地址與訂單號碼)
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.amount = amount;
		this.state = state;
		this.updateTime = updateTime;
		this.phone = phone;
		this.address = address;
		this.orderNumber = orderNumber;
	}

	public RespProInfo(String userId, String productId, String productName, Integer price, 
			Integer amount, String state, LocalDateTime updateTime, String phone, String address, 
			String orderNumber) { // 賣家商品資料(包含地址與訂單號碼)
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.amount = amount;
		this.state = state;
		this.updateTime = updateTime;
		this.phone = phone;
		this.address = address;
		this.orderNumber = orderNumber;
	}
	
	public RespProInfo(String productId, String productName, Integer price, Integer amount) { 
		// 買家商品資料
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.amount = amount;
	}

	public RespProInfo(String userId, String productId, String productName, Integer price, 
			Integer amount) { // 賣家商品資料
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.amount = amount;
	}
}
