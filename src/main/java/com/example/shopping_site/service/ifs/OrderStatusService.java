package com.example.shopping_site.service.ifs;

import com.example.shopping_site.request.OrderStatusRequest;
import com.example.shopping_site.response.OrderStatusResponse;

public interface OrderStatusService {
	
	public OrderStatusResponse addInCart(OrderStatusRequest request);
	
	public OrderStatusResponse userCartInfo(OrderStatusRequest request);
	
	public OrderStatusResponse removeProFromCart(OrderStatusRequest request);
	
	public OrderStatusResponse checkout(OrderStatusRequest request);
	
	public OrderStatusResponse searBuyPro(OrderStatusRequest request);
	
	public OrderStatusResponse buyCancelOrd(OrderStatusRequest request);
	
	public OrderStatusResponse searSellPro(OrderStatusRequest request);
	
	public OrderStatusResponse sellCancelOrd(OrderStatusRequest request);
	
	public OrderStatusResponse changeOrdState(OrderStatusRequest request);
	
	public OrderStatusResponse changeCartAmount(OrderStatusRequest request);
}
