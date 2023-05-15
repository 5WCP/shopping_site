package com.example.shopping_site.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_sort")
public class ProductSort {
	
	@Id
	@Column(name = "sort_name")
	private String sortName;
	
	@ManyToMany(mappedBy = "sorts" , fetch = FetchType.LAZY)
	private List<ProductInfo> productInfos = new ArrayList<>();
	

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public List<ProductInfo> getProductInfos() {
		return productInfos;
	}

	public void setProductInfos(List<ProductInfo> productInfos) {
		this.productInfos = productInfos;
	}

	public ProductSort() {
		
	}

	public ProductSort(String sortName) {
		this.sortName = sortName;
	}
		
}
