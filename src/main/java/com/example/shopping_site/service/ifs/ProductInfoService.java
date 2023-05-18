package com.example.shopping_site.service.ifs;

import com.example.shopping_site.request.ProductInfoRequest;
import com.example.shopping_site.response.ProductInfoResponse;

public interface ProductInfoService {
	
	public ProductInfoResponse addProduct(ProductInfoRequest request);
	
	public ProductInfoResponse deleteProduct(ProductInfoRequest request);
	
	public ProductInfoResponse reviseProduct(ProductInfoRequest request);
	
	public ProductInfoResponse changeState(ProductInfoRequest request);
	
	public ProductInfoResponse findMemSell(ProductInfoRequest request);
	
	public ProductInfoResponse getProInfo(ProductInfoRequest request);
}
