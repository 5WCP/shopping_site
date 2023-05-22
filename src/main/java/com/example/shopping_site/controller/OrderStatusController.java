package com.example.shopping_site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping_site.request.OrderStatusRequest;
import com.example.shopping_site.response.OrderStatusResponse;
import com.example.shopping_site.service.ifs.OrderStatusService;

@CrossOrigin
@RestController
public class OrderStatusController {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@PostMapping("add_in_cart")
	public OrderStatusResponse addInCart(@RequestBody OrderStatusRequest request) {
		return orderStatusService.addInCart(request);
	}
	
	@PostMapping("user_cart_info")
	public OrderStatusResponse userCartInfo(@RequestBody OrderStatusRequest request) {
		return orderStatusService.userCartInfo(request);
	}
	
	@PostMapping("remove_pro_from_cart")
	public OrderStatusResponse removeProFromCart(@RequestBody OrderStatusRequest request) {
		return orderStatusService.removeProFromCart(request);
	}
	
	@PostMapping("check_out")
	public OrderStatusResponse checkout(@RequestBody OrderStatusRequest request) {
		return orderStatusService.checkout(request);
	}
	
}
