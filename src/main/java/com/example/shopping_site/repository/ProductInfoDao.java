package com.example.shopping_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_site.entity.ProductInfo;

@Repository
public interface ProductInfoDao extends JpaRepository<ProductInfo, String> {
	
	List<ProductInfo> findByUserId(String userId);
}
