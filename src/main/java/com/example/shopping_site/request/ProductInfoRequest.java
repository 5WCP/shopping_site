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
	
}
