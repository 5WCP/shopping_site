package com.example.shopping_site.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_info")
public class ProductInfo {
	
	@Id
	@Column(name = "product_id")
	private String productId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "stock")
	private int stock;
	
	@Column(name = "product_picture")
	private String productPicture;
	
	@Column(name = "state")
	private boolean state;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "product_info_sort", joinColumns = {
	@JoinColumn(name = "product_id")}, inverseJoinColumns = {
	@JoinColumn(name = "sort_id")})
	private List<ProductSort> sorts = new ArrayList<>();
	
	@Column(name = "userid")
	private String userId;
	
	@Column(name = "add_time")
	private LocalDateTime addTime;
	
	@Column(name = "delete_pro")
	private boolean deletePro;

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

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public List<ProductSort> getSorts() {
		return sorts;
	}

	public void setSorts(List<ProductSort> sorts) {
		this.sorts = sorts;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDateTime getAddTime() {
		return addTime;
	}

	public void setAddTime(LocalDateTime addTime) {
		this.addTime = addTime;
	}

	public boolean isDeletePro() {
		return deletePro;
	}

	public void setDeletePro(boolean deletePro) {
		this.deletePro = deletePro;
	}

	public ProductInfo() {
		
	}
	
}
