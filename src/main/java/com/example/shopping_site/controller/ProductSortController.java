package com.example.shopping_site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping_site.service.ifs.ProductSortService;

@CrossOrigin
@RestController
public class ProductSortController {

	@Autowired
	private ProductSortService productSortService;
	
}
