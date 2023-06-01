package com.example.shopping_site.service.ifs;

import com.example.shopping_site.request.ProductInfoRequest;
import com.example.shopping_site.response.ProductInfoResponse;

public interface ProductInfoService {
	
	public ProductInfoResponse addProduct(ProductInfoRequest request);
	
	public ProductInfoResponse deleteProduct(ProductInfoRequest request); // 從資料庫刪除(暫不使用)
	
	public ProductInfoResponse deleProduct(ProductInfoRequest request); // 沒有從資料庫刪除
	
	public ProductInfoResponse reviseProduct(ProductInfoRequest request);
	
	public ProductInfoResponse changeState(ProductInfoRequest request);
	
	public ProductInfoResponse findMemSell(ProductInfoRequest request); // 找出賣家販賣的商品名稱
	
	public ProductInfoResponse nameToId(ProductInfoRequest request); // 商品名稱帶出商品代碼
	
	public ProductInfoResponse getProInfo(ProductInfoRequest request);
	
	public ProductInfoResponse searProSort(ProductInfoRequest request);
	
	public ProductInfoResponse searAllPro(ProductInfoRequest request);
	
	public ProductInfoResponse searProNameAndSort(ProductInfoRequest request);
}
