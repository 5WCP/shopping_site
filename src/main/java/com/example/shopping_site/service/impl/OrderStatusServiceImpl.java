package com.example.shopping_site.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.OrderStatus;
import com.example.shopping_site.entity.ProductInfo;
import com.example.shopping_site.entity.UserBuyProduct;
import com.example.shopping_site.repository.MemberInfoDao;
import com.example.shopping_site.repository.OrderStatusDao;
import com.example.shopping_site.repository.ProductInfoDao;
import com.example.shopping_site.request.OrderStatusRequest;
import com.example.shopping_site.response.OrderStatusResponse;
import com.example.shopping_site.response.RespProInfo;
import com.example.shopping_site.service.ifs.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
	
	@Autowired
	private ProductInfoDao productInfoDao;
	
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	@Autowired
	private OrderStatusDao orderStatusDao;

	@Override
	public OrderStatusResponse addInCart(OrderStatusRequest request) {
		OrderStatus reqOrd = request.getOrderStatus();
		LocalDateTime updateTime = LocalDateTime.now(); // 更新時間
		List<OrderStatus> userCartList = new ArrayList<>(); // 使用者的購物車資料
		if(!StringUtils.hasText(reqOrd.getUserId())) {
			return new OrderStatusResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqOrd.getUserId())){
			return new OrderStatusResponse("登入的用戶名非本站的用戶名(異常)");
		}
		userCartList = orderStatusDao.findByUserId(reqOrd.getUserId());
		for(OrderStatus userCart : userCartList) {
			if(userCart.getProductId().equals(reqOrd.getProductId())) {
				userCart.setAmount(userCart.getAmount() + reqOrd.getAmount());
				orderStatusDao.save(userCart);
				return new OrderStatusResponse("已新增進購物車");
			}
		}
		OrderStatus addIC = new OrderStatus(reqOrd.getUserId(), reqOrd.getProductId(), 
				updateTime, reqOrd.getAmount(), "考慮中");
		orderStatusDao.save(addIC);
		return new OrderStatusResponse("已新增進購物車");
	}

	@Override
	public OrderStatusResponse userCartInfo(OrderStatusRequest request) {
		String reqUser = request.getUserId();
		List<OrderStatus> userCartList = new ArrayList<>(); // 使用者的購物車資料
		List<RespProInfo> cartInfoList = new ArrayList<>(); // 要傳回前端的購物車資訊列表
		if(!StringUtils.hasText(reqUser)) {
			return new OrderStatusResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqUser)) {
			return new OrderStatusResponse("登入的用戶名非本站的用戶名(異常)");
		}
		userCartList = orderStatusDao.findByUserId(reqUser);
		for(OrderStatus userCart : userCartList) {
			ProductInfo cartPro = productInfoDao.findById(userCart.getProductId()).get();
			cartInfoList.add(new RespProInfo(cartPro.getProductId(), cartPro.getProductName(), 
				cartPro.getPrice(), cartPro.getProductPicture(), userCart.getAmount()));
		}
		return new OrderStatusResponse(cartInfoList);
	}

	@Override
	public OrderStatusResponse removeProFromCart(OrderStatusRequest request) {
		OrderStatus reqOrd = request.getOrderStatus();
		UserBuyProduct deleCartInfoI = new UserBuyProduct(reqOrd.getUserId(), reqOrd.getProductId(), 
				reqOrd.getUpdateTime());
		OrderStatus deleCartInfo = orderStatusDao.findById(deleCartInfoI).get();
		orderStatusDao.delete(deleCartInfo);
		return new OrderStatusResponse("已移出購物車");
	}

	@Override
	public OrderStatusResponse checkout(OrderStatusRequest request) {
		String reqUser = request.getUserId();
		List<OrderStatus> cartProList = new ArrayList<>();
		Integer totalPrice = 0;
		cartProList = orderStatusDao.findByUserId(reqUser);
		if(cartProList.isEmpty()) {
			return new OrderStatusResponse("購物車目前沒有商品");
		}
		for(OrderStatus cartPro : cartProList) {
			ProductInfo pro = productInfoDao.findById(reqUser).get();
			cartPro.setState("準備中");
			totalPrice += cartPro.getAmount() * pro.getPrice();
		}
		return new OrderStatusResponse("已下單 總價格為 : $" + totalPrice);
	}

}
