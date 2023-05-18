package com.example.shopping_site.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_sort")
public class ProductSort {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sort_id")
	private Long sortId;
	
	@Column(name = "sort_name")
	private String sortName;
	
	@ManyToMany(mappedBy = "sorts" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<ProductInfo> productInfos = new ArrayList<>();
	
	public Long getSortId() {
		return sortId;
	}

	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}

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
