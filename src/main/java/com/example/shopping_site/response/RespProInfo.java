package com.example.shopping_site.response;

public class RespProInfo {
	
	private String productId;
	
	private String productName;
	
	private Integer price;
	
	private Integer stock;
	
	private String productPicture;
	
	private Integer amount;

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

	public RespProInfo() {
		
	}

	public RespProInfo(String productId, String productName, Integer price, Integer stock, String productPicture) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.stock = stock;
		this.productPicture = productPicture;
	}

	public RespProInfo(String productId, String productName, Integer price, String productPicture, Integer amount) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.productPicture = productPicture;
		this.amount = amount;
	}
		
}
