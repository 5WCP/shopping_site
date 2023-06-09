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
	
	@PostMapping("sear_buy_pro")
	public OrderStatusResponse searBuyPro(@RequestBody OrderStatusRequest request) {
		return orderStatusService.searBuyPro(request);
	}
	
	@PostMapping("buy_cancel_ord")
	public OrderStatusResponse buyCancelOrd(@RequestBody OrderStatusRequest request) {
		return orderStatusService.buyCancelOrd(request);
	}
	
	@PostMapping("sear_sell_pro")
	public OrderStatusResponse searSellPro(@RequestBody OrderStatusRequest request) {
		return orderStatusService.searSellPro(request);
	}
	
	@PostMapping("sell_cancel_ord")
	public OrderStatusResponse sellCancelOrd(@RequestBody OrderStatusRequest request) {
		return orderStatusService.sellCancelOrd(request);
	}
	
	@PostMapping("change_ord_state")
	public OrderStatusResponse changeOrdState(@RequestBody OrderStatusRequest request) {
		return orderStatusService.changeOrdState(request);
	}
	
	@PostMapping("change_cart_amount")
	public OrderStatusResponse changeCartAmount(@RequestBody OrderStatusRequest request) {
		return orderStatusService.changeCartAmount(request);
	}
}
