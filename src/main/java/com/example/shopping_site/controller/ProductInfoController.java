package com.example.shopping_site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping_site.request.ProductInfoRequest;
import com.example.shopping_site.response.ProductInfoResponse;
import com.example.shopping_site.service.ifs.ProductInfoService;

@CrossOrigin
@RestController
public class ProductInfoController {
	
	@Autowired
	private ProductInfoService productInfoService;
	
	@PostMapping("add_product")
	public ProductInfoResponse addProduct(@RequestBody ProductInfoRequest request) {
		return productInfoService.addProduct(request);
	}
	
	@PostMapping("delete_product")
	public ProductInfoResponse deleteProduct(@RequestBody ProductInfoRequest request) {
		return productInfoService.deleteProduct(request);
	}
	
	@PostMapping("dele_product")
	public ProductInfoResponse deleProduct(@RequestBody ProductInfoRequest request) {
		return productInfoService.deleProduct(request);
	}
	
	@PostMapping("revise_product")
	public ProductInfoResponse reviseProduct(@RequestBody ProductInfoRequest request) {
		return productInfoService.reviseProduct(request);
	}
	
	@PostMapping("change_state")
	public ProductInfoResponse changeState(@RequestBody ProductInfoRequest request) {
		return productInfoService.changeState(request);
	}
	
	@PostMapping("find_mem_sell")
	public ProductInfoResponse findMemSell(@RequestBody ProductInfoRequest request) {
		return productInfoService.findMemSell(request);
	}
	
	@PostMapping("name_to_id")
	public ProductInfoResponse nameToId(@RequestBody ProductInfoRequest request) {
		return productInfoService.nameToId(request);
	}
	
	@PostMapping("get_pro_info")
	public ProductInfoResponse getProInfo(@RequestBody ProductInfoRequest request) {
		return productInfoService.getProInfo(request);
	}
	
	@PostMapping("sear_pro_sort")
	public ProductInfoResponse searProSort(@RequestBody ProductInfoRequest request) {
		return productInfoService.searProSort(request);
	}
	
	@PostMapping("sear_all_pro")
	public ProductInfoResponse searAllPro(@RequestBody ProductInfoRequest request) {
		return productInfoService.searAllPro(request);
	}
	
	@PostMapping("sear_pro_name_and_sort")
	public ProductInfoResponse searProNameAndSort(@RequestBody ProductInfoRequest request) {
		return productInfoService.searProNameAndSort(request);
	}
}
