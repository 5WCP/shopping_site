package com.example.shopping_site.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.ProductInfo;
import com.example.shopping_site.entity.ProductSort;
import com.example.shopping_site.repository.ProductInfoDao;
import com.example.shopping_site.request.ProductInfoRequest;
import com.example.shopping_site.response.ProductInfoResponse;
import com.example.shopping_site.service.ifs.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	
	@Autowired
	private ProductInfoDao productInfoDao;

	@Override
	public ProductInfoResponse addProduct(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(!StringUtils.hasText(reqPro.getProductName())) {
			return new ProductInfoResponse("商品名稱未填寫");
		}
		if(reqPro.getProductPicture() == null
			|| reqPro.getProductPicture().length == 0) {
			return new ProductInfoResponse("圖片未上傳");
		}
		if(reqPro.getPrice() < 0 || reqPro.getStock() < 0) {
			return new ProductInfoResponse("價格和數量不得小於0");
		}
		if(request.getSortsName().isEmpty()) {
			return new ProductInfoResponse("請選擇分類");
		}
		List<ProductSort> productSorts = new ArrayList<>();
		for(String sort : request.getSortsName()) {
			productSorts.add(new ProductSort(sort));
		}
		reqPro.setSorts(productSorts);
		productInfoDao.save(reqPro);
		return new ProductInfoResponse("商品新增成功");
	}

	@Override
	public ProductInfoResponse deleteProduct(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(reqPro.getProductId() == 0) {
			return new ProductInfoResponse("商品識別號碼未填寫");
		}
		if(!productInfoDao.existsById(reqPro.getProductId())) {
			return new ProductInfoResponse("商品不存在");
		}
		if(!StringUtils.hasText(reqPro.getProductName())) {
			return new ProductInfoResponse("商品名稱未填寫");
		}
		ProductInfo delePro = productInfoDao.findById(reqPro.getProductId()).get();
		if(!reqPro.getUserId().equals(delePro.getUserId())) {
			return new ProductInfoResponse("您非此商品的擁有者");
		}
		if(delePro.isState() == true) {
			return new ProductInfoResponse("請先下架後再刪除");
		}
		productInfoDao.delete(delePro);
		return new ProductInfoResponse("商品刪除成功");
	}

	@Override
	public ProductInfoResponse reviseProduct(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(reqPro.getProductId() == 0) {
			return new ProductInfoResponse("商品識別號碼未填寫");
		}
		if(!StringUtils.hasText(reqPro.getProductName())) {
			return new ProductInfoResponse("商品名稱未填寫");
		}
		if(reqPro.getProductPicture() == null
			|| reqPro.getProductPicture().length == 0) {
			return new ProductInfoResponse("圖片未上傳");
		}
		if(reqPro.getPrice() < 0 || reqPro.getStock() < 0) {
			return new ProductInfoResponse("價格和數量不得小於0");
		}
		if(request.getSortsName().isEmpty()) {
			return new ProductInfoResponse("請選擇分類");
		}
		if(!productInfoDao.existsById(reqPro.getProductId())) {
			return new ProductInfoResponse("商品不存在");
		}
		ProductInfo revisePro = productInfoDao.findById(reqPro.getProductId()).get();
		if(!reqPro.getUserId().equals(revisePro.getUserId())) {
			return new ProductInfoResponse("您非此商品的擁有者");
		}
		if(revisePro.isState() == true) {
			return new ProductInfoResponse("請先下架後再修改");
		}
		List<ProductSort> productSorts = new ArrayList<>();
		for(String sort : request.getSortsName()) {
			productSorts.add(new ProductSort(sort));
		}
		reqPro.setSorts(productSorts);
		reqPro.setState(false);
		productInfoDao.save(reqPro);
		return new ProductInfoResponse("商品修改成功");
	}

	@Override
	public ProductInfoResponse changeState(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(reqPro.getProductId() == 0) {
			return new ProductInfoResponse("商品識別號碼未填寫");
		}
		if(!productInfoDao.existsById(reqPro.getProductId())) {
			return new ProductInfoResponse("商品不存在");
		}
		ProductInfo changePro = productInfoDao.findById(reqPro.getProductId()).get();
		if(!reqPro.getUserId().equals(changePro.getUserId())) {
			return new ProductInfoResponse("您非此商品的擁有者");
		}
		if(reqPro.isState() == changePro.isState()) {
			return new ProductInfoResponse("狀態未變更");
		}
		changePro.setState(reqPro.isState());
		if(reqPro.isState() == true) {
			return new ProductInfoResponse("商品已上架");
		} else {
			return new ProductInfoResponse("商品已下架");
		}
	}

}
