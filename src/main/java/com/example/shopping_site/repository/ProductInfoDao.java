package com.example.shopping_site.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_site.entity.ProductInfo;

@Repository
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
	
	List<ProductInfo> findByUserId(String userId);
	
	List<ProductInfo> findBySortsSortName(String sortName, Sort sort);
	
	List<ProductInfo> findByProductNameContaining(String productName, Sort sort);
	
	List<ProductInfo> findByProductNameContainingAndSortsSortName
	(String sortName, String productName, Sort sort);
	
	List<ProductInfo> findByProductName(String productName);
}
