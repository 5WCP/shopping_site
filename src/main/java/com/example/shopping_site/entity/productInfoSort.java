package com.example.shopping_site.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_info_sort")
public class productInfoSort {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private ProductInfo productInfo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sort_id")
	private ProductSort productSort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public ProductSort getProductSort() {
		return productSort;
	}

	public void setProductSort(ProductSort productSort) {
		this.productSort = productSort;
	}

	public productInfoSort() {
		
	}
		
}
