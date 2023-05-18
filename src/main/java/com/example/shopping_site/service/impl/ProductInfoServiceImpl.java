package com.example.shopping_site.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.ProductInfo;
import com.example.shopping_site.entity.ProductSort;
import com.example.shopping_site.repository.MemberInfoDao;
import com.example.shopping_site.repository.ProductInfoDao;
import com.example.shopping_site.request.ProductInfoRequest;
import com.example.shopping_site.response.ProductInfoResponse;
import com.example.shopping_site.service.ifs.ProductInfoService;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
	
	@Autowired
	private ProductInfoDao productInfoDao;
	
	@Autowired
	private MemberInfoDao memberInfoDao;

	@Override
	public ProductInfoResponse addProduct(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		String english = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int number = 100000000;
		Random random = new Random();
		char randomEng = english.charAt(random.nextInt(english.length()));
		int randomNum = random.nextInt(number);
		StringBuilder proIdBuilder = new StringBuilder();
		proIdBuilder.append(randomEng);
		proIdBuilder.append(String.format("%08d", randomNum));
		String newProId = proIdBuilder.toString();
		while(productInfoDao.existsById(newProId)) {
			char randomEngA = english.charAt(random.nextInt(english.length()));
			int randomNumA = random.nextInt(number);
			StringBuilder proIdBuilderA = new StringBuilder();
			proIdBuilderA.append(randomEngA);
			proIdBuilderA.append(String.format("%08d", randomNumA));
			newProId = proIdBuilderA.toString();
		}
		reqPro.setProductId(proIdBuilder.toString());
		if(!StringUtils.hasText(reqPro.getUserId())) {
			return new ProductInfoResponse("沒有使用者用戶名資料(異常)");
		}
		if(!StringUtils.hasText(reqPro.getProductName())) {
			return new ProductInfoResponse("商品名稱未填寫");
		}
		if(!StringUtils.hasText(reqPro.getProductPicture())) {
			return new ProductInfoResponse("圖片未上傳");
		}
		if(reqPro.getPrice() <= 0 || reqPro.getStock() <= 0) {
			return new ProductInfoResponse("價格和數量不得小於等於0");
		}
		if(request.getSortsName().isEmpty()) {
			return new ProductInfoResponse("請選擇分類");
		}
		List<ProductSort> productSorts = new ArrayList<>();
		for(String sort : request.getSortsName()) {
			productSorts.add(new ProductSort(sort));
		}
		reqPro.setUserId(reqPro.getUserId());
		reqPro.setSorts(productSorts);
		productInfoDao.save(reqPro);
		return new ProductInfoResponse("商品新增成功");
	}

	@Override
	public ProductInfoResponse deleteProduct(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(!StringUtils.hasText(reqPro.getProductId())) {
			return new ProductInfoResponse("商品代碼未填寫");
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
		if(!StringUtils.hasText(reqPro.getProductId())) {
			return new ProductInfoResponse("商品代號未填寫");
		}
		ProductInfo oriPro = productInfoDao.findById(reqPro.getProductId()).get();
		if(!StringUtils.hasText(reqPro.getProductName())) {
			return new ProductInfoResponse("商品名稱未填寫");
		}
		if(!StringUtils.hasText(reqPro.getProductPicture())) {
			reqPro.setProductPicture(oriPro.getProductPicture());
		}
		if(reqPro.getPrice() <= 0 || reqPro.getStock() <= 0) {
			return new ProductInfoResponse("價格和數量不得小於等於0");
		}
		if(request.getSortsName().isEmpty()) {
			return new ProductInfoResponse("請選擇分類");
		}
		if(!productInfoDao.existsById(reqPro.getProductId())) {
			return new ProductInfoResponse("商品不存在");
		}
		if(!reqPro.getUserId().equals(oriPro.getUserId())) {
			return new ProductInfoResponse("您非此商品的擁有者");
		}
		if(oriPro.isState() == true) {
			return new ProductInfoResponse("請先下架後再修改");
		}
		List<ProductSort> productSorts = new ArrayList<>();
		for(String sort : request.getSortsName()) {
			productSorts.add(new ProductSort(sort));
		}
		reqPro.setSorts(productSorts);
		reqPro.setState(false);
		productInfoDao.delete(oriPro);
		productInfoDao.save(reqPro);
		return new ProductInfoResponse("商品修改成功");
	}

	@Override
	public ProductInfoResponse changeState(ProductInfoRequest request) {
		ProductInfo reqPro = request.getProductInfo();
		if(!StringUtils.hasText(reqPro.getProductId())) {
			return new ProductInfoResponse("商品代碼未填寫");
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
		productInfoDao.save(changePro);
		if(reqPro.isState() == true) {
			return new ProductInfoResponse("商品已上架");
		} else {
			return new ProductInfoResponse("商品已下架");
		}
	}

	@Override
	public ProductInfoResponse findMemSell(ProductInfoRequest request) {
		if(!StringUtils.hasText(request.getUserId())) {
			return new ProductInfoResponse("目前為未登入狀態(異常)");
		}
		if(!memberInfoDao.existsById(request.getUserId())) {
			return new ProductInfoResponse("登入的用戶名非本站的用戶名(異常)");
		}
		List<ProductInfo> sellList = productInfoDao.findByUserId(request.getUserId());
		List<String> sellIdList = new ArrayList<>();
		for(ProductInfo sellPro : sellList) {
			sellIdList.add(sellPro.getProductId());
		}
		return new ProductInfoResponse(sellIdList);
	}

	@Override
	public ProductInfoResponse getProInfo(ProductInfoRequest request) {
		ProductInfo getPro = productInfoDao.findById(request.getProductId()).get();
		List<String> sortList = new ArrayList<>();
		for(ProductSort sort : getPro.getSorts()) {
			sortList.add(sort.getSortName());
		}
		return new ProductInfoResponse(getPro.getProductName(), getPro.getPrice(), getPro.getStock(), 
				getPro.getProductPicture(), sortList, getPro.isState());
	}
}
