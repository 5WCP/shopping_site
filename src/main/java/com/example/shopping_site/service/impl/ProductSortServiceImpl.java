package com.example.shopping_site.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shopping_site.repository.ProductSortDao;
import com.example.shopping_site.service.ifs.ProductSortService;

@Service
public class ProductSortServiceImpl implements ProductSortService {

	@Autowired
	private ProductSortDao productSortDao;
	
}
