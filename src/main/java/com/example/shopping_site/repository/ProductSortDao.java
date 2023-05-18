package com.example.shopping_site.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_site.entity.ProductSort;

@Repository
public interface ProductSortDao extends JpaRepository<ProductSort, String> {
	
}
