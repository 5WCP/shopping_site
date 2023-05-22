package com.example.shopping_site.request;

import java.util.ArrayList;
import java.util.List;

import com.example.shopping_site.entity.ProductInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductInfoRequest {
	
	@JsonProperty("product_info")
	private ProductInfo productInfo;
	
	@JsonProperty("sorts_name")
	private List<String> sortsName = new ArrayList<>(); 
	
	@JsonProperty("userid")
	private String userId;
	
	@JsonProperty("product_id")
	private String productId;
	
	@JsonProperty("sort_name")
	private String sortName;

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public List<String> getSortsName() {
		return sortsName;
	}

	public void setSortsName(List<String> sortsName) {
		this.sortsName = sortsName;
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

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

}
