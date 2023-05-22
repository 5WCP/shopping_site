package com.example.shopping_site.response;

import java.util.List;

import com.example.shopping_site.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductInfoResponse {

	private String message;
	
	@JsonProperty("pro_list")
	private List<ProductInfo> proList;
	
	@JsonProperty("pro_id_list")
	private List<String> proIdList;
	
	@JsonProperty("product_name")
	private String productName;
	
	@JsonProperty("price")
	private int price;
	
	@JsonProperty("stock")
	private int stock;
	
	@JsonProperty("product_picture")
	private String productPicture;
	
	@JsonProperty("sort_name")
	private List<String> sortName;
	
	@JsonProperty("state")
	private boolean state;
	
	@JsonProperty("re_pro_list")
	private List<RespProInfo> respProInfoList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProductInfo> getProList() {
		return proList;
	}

	public void setProList(List<ProductInfo> proList) {
		this.proList = proList;
	}

	public List<String> getProIdList() {
		return proIdList;
	}

	public void setProIdList(List<String> proIdList) {
		this.proIdList = proIdList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String productPicture) {
		this.productPicture = productPicture;
	}

	public List<String> getSortName() {
		return sortName;
	}

	public void setSortName(List<String> sortName) {
		this.sortName = sortName;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public ProductInfoResponse() {
		
	}

	public ProductInfoResponse(String message) {
		this.message = message;
	}

	public ProductInfoResponse(List<String> proIdList) {
		this.proIdList = proIdList;
	}

	public ProductInfoResponse(String productName, int price, int stock, String productPicture, List<String> sortName, boolean state) {
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.productPicture = productPicture;
		this.sortName = sortName;
		this.state = state;
	}

	public ProductInfoResponse(String message, List<RespProInfo> respProInfoList) {
		this.message = message;
		this.respProInfoList = respProInfoList;
	}

}
