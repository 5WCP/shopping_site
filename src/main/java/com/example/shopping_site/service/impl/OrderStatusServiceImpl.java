package com.example.shopping_site.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.MemberInfo;
import com.example.shopping_site.entity.OrderStatus;
import com.example.shopping_site.entity.ProductInfo;
import com.example.shopping_site.entity.UserBuyProduct;
import com.example.shopping_site.repository.MemberInfoDao;
import com.example.shopping_site.repository.OrderStatusDao;
import com.example.shopping_site.repository.ProductInfoDao;
import com.example.shopping_site.request.OrderStatusRequest;
import com.example.shopping_site.response.OrdNumInfo;
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
		userCartList = orderStatusDao.findByUserIdAndProductIdAndState(reqOrd.getUserId(), reqOrd.getProductId() , "考慮中");
		ProductInfo pro = productInfoDao.findById(reqOrd.getProductId()).get();
		if(userCartList.isEmpty()) {
			if(reqOrd.getAmount() == 0) {
				return new OrderStatusResponse();
			}
			if(reqOrd.getAmount() > pro.getStock()) {
				reqOrd.setAmount(pro.getStock());
			}
			OrderStatus addIC = new OrderStatus(reqOrd.getUserId(), reqOrd.getProductId(), 
					updateTime, reqOrd.getAmount(), "考慮中");
			orderStatusDao.save(addIC);
			return new OrderStatusResponse("(" + reqOrd.getProductId() + ")已新增進購物車");
		}
		for(OrderStatus userCart : userCartList) {
			if(userCart.getProductId().equals(reqOrd.getProductId())) {
				if(userCart.getAmount() == pro.getStock()) {
					return new OrderStatusResponse("購物車商品數量已達商品庫存數量");
				}
				Integer totalA= userCart.getAmount() + reqOrd.getAmount();
				if(totalA > pro.getStock()) {
					totalA = pro.getStock();
				}
				OrderStatus addIC = new OrderStatus(reqOrd.getUserId(), reqOrd.getProductId(), 
						updateTime, totalA, "考慮中"); // 三主鍵更新時間與訂單量
				orderStatusDao.delete(userCart); // 刪除舊的資料庫資料
				orderStatusDao.save(addIC); // 存取新的資料庫資料
			}
		}
		return new OrderStatusResponse("(" + reqOrd.getProductId() + ")已新增進購物車");
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
		userCartList = orderStatusDao.findByUserIdAndState(reqUser, "考慮中");
		for(OrderStatus userCart : userCartList) {
			ProductInfo cartPro = productInfoDao.findById(userCart.getProductId()).get();
			if(userCart.getAmount() > cartPro.getStock()) {
				userCart.setAmount(cartPro.getStock());
			}
			cartInfoList.add(new RespProInfo(cartPro.getProductId(), cartPro.getProductName(), 
				cartPro.getPrice(), cartPro.getProductPicture(), userCart.getAmount(), 
				userCart.getUpdateTime(), cartPro.getStock()));
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
		return new OrderStatusResponse("移出購物車");
	}

	@Override
	public OrderStatusResponse checkout(OrderStatusRequest request) {
		String reqUser = request.getUserId();
		String reqAdd = request.getAddress();
		Set<String> diffSell = new HashSet<>(); // 購物車裡面不同買家 需分成多張訂單
		List<OrderStatus> cartProList = new ArrayList<>();
		Integer totalPrice = 0;
		LocalDateTime updateTime = LocalDateTime.now();
		cartProList = orderStatusDao.findByUserIdAndState(reqUser, "考慮中");
		if(!StringUtils.hasText(reqAdd)) {
			return new OrderStatusResponse("請輸入寄件地址");
		}
		if(cartProList.isEmpty()) {
			return new OrderStatusResponse("購物車目前沒有商品");
		}
		for(OrderStatus cartPro : cartProList) {
			ProductInfo pro = productInfoDao.findById(cartPro.getProductId()).get();
			diffSell.add(pro.getUserId()); // 找出所有購物車的買家 存進set
		}
		for(String sellId : diffSell) {
			for(OrderStatus cartPro : cartProList) {
				ProductInfo pro = productInfoDao.findById(cartPro.getProductId()).get();
				if(pro.getUserId().equals(sellId)) {
					String english = "abcdefghijklmnopqrstuvxyz"; // 生成新訂單編號
					int number = 100000000;
					Random random = new Random();
					char randomEng = english.charAt(random.nextInt(english.length()));
					int randomNum = random.nextInt(number);
					StringBuilder bulidOrderN = new StringBuilder();
					bulidOrderN.append(randomEng);
					bulidOrderN.append(String.format("%08d", randomNum));
					String newOrN = bulidOrderN.toString();
					while(!orderStatusDao.findByOrderNumber(newOrN).isEmpty()) { // 訂單編號已存在
						randomEng = english.charAt(random.nextInt(english.length()));
						randomNum = random.nextInt(number);
						StringBuilder bulidOrderNR = new StringBuilder();
						bulidOrderNR.append(randomEng);
						bulidOrderNR.append(String.format("%08d", randomNum));
						newOrN = bulidOrderNR.toString();
					}
					if((pro.getStock() - cartPro.getAmount()) < 0) {
						return new OrderStatusResponse("(" + pro.getProductId() + ")商品庫存不足");
					}
					if((pro.getStock() == cartPro.getAmount())) {
						pro.setState(false);
					}
					pro.setStock((pro.getStock() - cartPro.getAmount()));
					productInfoDao.save(pro);
					OrderStatus newOrder = new OrderStatus(cartPro.getUserId(), cartPro.getProductId(), 
							updateTime, cartPro.getAmount(), "準備中", newOrN ,reqAdd);
					orderStatusDao.delete(cartPro);
					orderStatusDao.save(newOrder);
					totalPrice += cartPro.getAmount() * pro.getPrice();
				}
			}
		}
		return new OrderStatusResponse("已下單 總價格為 : $" + totalPrice);
	}

	@Override
	public OrderStatusResponse searBuyPro(OrderStatusRequest request) {
		String reqUser = request.getUserId();
		List<OrderStatus> resOrStList = new ArrayList<>(); // 購物中(準備中或運送中或已完成)的資料
		List<OrdNumInfo> ordNumList = new ArrayList<>(); // 要查詢的訂單資訊
		Set<String> ordNSet = new HashSet<>(); // 找出訂單號碼
		if(!StringUtils.hasText(reqUser)) {
			return new OrderStatusResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqUser)) {
			return new OrderStatusResponse("登入的用戶名非本站的用戶名(異常)");
		}
		resOrStList = orderStatusDao.findByUserIdAndState(reqUser, request.getState());
		if(resOrStList.isEmpty()) {
			return new OrderStatusResponse("您暫無" + request.getState() + "的商品");
		}
		for(OrderStatus resOrSt : resOrStList) { // 放置訂單編號
			ordNSet.add(resOrSt.getOrderNumber());
		}
		for(String ordN : ordNSet) {
			List<RespProInfo> orderInfoList = new ArrayList<>(); // 要查詢的商品資訊
			List<OrderStatus> proInOrdNL = new ArrayList<>();
			proInOrdNL = orderStatusDao.findByOrderNumber(ordN); // 找出這張訂單號碼的商品
			for(OrderStatus proInOrdN : proInOrdNL) {
				ProductInfo pro = productInfoDao.findById(proInOrdN.getProductId()).get();
				orderInfoList.add(new RespProInfo(proInOrdN.getProductId(), pro.getProductName(), 
						pro.getPrice(), proInOrdN.getAmount()));
			}
			OrderStatus ordNIn = proInOrdNL.get(0); // 資訊一樣的部分只需要抽出陣列其中一筆取得資料
			ProductInfo pro = productInfoDao.findById(ordNIn.getProductId()).get(); // 找出商品資訊
			MemberInfo sellMem = memberInfoDao.findById(pro.getUserId()).get(); // 得到賣家資訊
			ordNumList.add(new OrdNumInfo(ordN, ordNIn.getAddress(), sellMem.getPhone(), 
					ordNIn.getState(), ordNIn.getUpdateTime(), orderInfoList));
		}
		return new OrderStatusResponse(ordNumList, 0);
	}

	@Override
	public OrderStatusResponse buyCancelOrd(OrderStatusRequest request) {
		String cancelOrdN = request.getOrderStatus().getOrderNumber();
		List<OrderStatus> cancelOrdNPL = new ArrayList<>();
		LocalDateTime updateTime = LocalDateTime.now(); // 取消訂單的時間
		cancelOrdNPL = orderStatusDao.findByOrderNumber(cancelOrdN);
		for(OrderStatus cancelOrdNP : cancelOrdNPL) {
			OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(cancelOrdNP.getUserId(), 
					cancelOrdNP.getProductId(), cancelOrdNP.getUpdateTime())).get(); // 拉出原本資料庫的訂單資料
			ProductInfo cancelPro = productInfoDao.findById(cancelOrdNP.getProductId()).get(); // 找出被取消訂單的商品
			cancelPro.setStock(cancelPro.getStock() + oriOrd.getAmount()); // 把取消的數量新增回庫存
			productInfoDao.save(cancelPro);
			OrderStatus cancelOrd = new OrderStatus(oriOrd.getUserId(),oriOrd.getProductId(), 
					updateTime, oriOrd.getAmount(), "已取消", oriOrd.getOrderNumber(), oriOrd.getAddress()); // 要新增的取消訂單資料
			orderStatusDao.delete(oriOrd);
			orderStatusDao.save(cancelOrd);
		}
		return new OrderStatusResponse("已取消訂單");
	}

	@Override
	public OrderStatusResponse searSellPro(OrderStatusRequest request) {
		String reqUser = request.getUserId();
		if(!StringUtils.hasText(reqUser)) {
			return new OrderStatusResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqUser)) {
			return new OrderStatusResponse("登入的用戶名非本站的用戶名(異常)");
		}
		List<ProductInfo> userSellProList = new ArrayList<>(); // 該用戶品販賣的商品
		userSellProList = productInfoDao.findByUserId(reqUser);
		List<OrderStatus> orderSellProList = new ArrayList<>(); // 找出在下單資料庫的商品
		for(ProductInfo userSellPro : userSellProList) {
			List<OrderStatus> orderProList = new ArrayList<>(); // 找出賣家單件商品在下單資料庫的資料
			orderProList = orderStatusDao.findByProductId(userSellPro.getProductId());
			if(!orderProList.isEmpty()) { // 如果單件商品在下單資料庫找得到
				orderSellProList.addAll(orderProList);
			}
		}
		List<RespProInfo> sellStaProList = new ArrayList<>(); // 要搜尋的狀態商品資訊
		List<OrdNumInfo> ordNumList = new ArrayList<>(); // 要查詢的訂單資訊
		Set<String> ordNSet = new HashSet<>(); // 找出訂單號碼
		for(OrderStatus orderSellPro : orderSellProList) {
			ordNSet.add(orderSellPro.getOrderNumber());
		}
		for(String ordN : ordNSet) {
			List<OrderStatus> proInOrdNL = new ArrayList<>();
			proInOrdNL = orderStatusDao.findByOrderNumber(ordN); // 找出這張訂單號碼的商品
			for(OrderStatus proInOrdN : proInOrdNL) {
				if(proInOrdN.getState().equals(request.getState())) {
					ProductInfo pro = productInfoDao.findById(proInOrdN.getProductId()).get();
					sellStaProList.add(new RespProInfo(proInOrdN.getUserId(), proInOrdN.getProductId(), 
							pro.getProductName(), pro.getPrice(), proInOrdN.getAmount()));
				}
			}
			OrderStatus ordNIn = proInOrdNL.get(0); // 資訊一樣的部分只需要抽出陣列其中一筆取得資料
			MemberInfo buyMem = memberInfoDao.findById(ordNIn.getUserId()).get();
			ordNumList.add(new OrdNumInfo(ordN, ordNIn.getAddress(), buyMem.getPhone(), 
					ordNIn.getState(), ordNIn.getUpdateTime(), sellStaProList));
		}
		return new OrderStatusResponse(ordNumList, 0);
	}

	@Override
	public OrderStatusResponse sellCancelOrd(OrderStatusRequest request) {
		String cancelOrdN = request.getOrderStatus().getOrderNumber();
		List<OrderStatus> cancelOrdNPL = new ArrayList<>();
		LocalDateTime updateTime = LocalDateTime.now(); // 取消訂單的時間
		cancelOrdNPL = orderStatusDao.findByOrderNumber(cancelOrdN);
		for(OrderStatus cancelOrdNP : cancelOrdNPL) {
			OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(cancelOrdNP.getUserId(), 
					cancelOrdNP.getProductId(), cancelOrdNP.getUpdateTime())).get(); // 拉出原本資料庫的訂單資料
			ProductInfo cancelPro = productInfoDao.findById(cancelOrdNP.getProductId()).get(); // 找出被取消訂單的商品
			cancelPro.setStock(cancelPro.getStock() + oriOrd.getAmount()); // 把取消的數量新增回庫存
			productInfoDao.save(cancelPro);
			OrderStatus cancelOrd = new OrderStatus(oriOrd.getUserId(),oriOrd.getProductId(), 
					updateTime, oriOrd.getAmount(), "已取消", oriOrd.getOrderNumber(), oriOrd.getAddress()); // 要新增的取消訂單資料
			orderStatusDao.delete(oriOrd);
			orderStatusDao.save(cancelOrd);
		}
		return new OrderStatusResponse("已取消訂單");
	}

	@Override
	public OrderStatusResponse changeOrdState(OrderStatusRequest request) {
		String reqOrdN = request.getOrderStatus().getOrderNumber();
		List<OrderStatus> reqChaStaOrdL = new ArrayList<>();
		reqChaStaOrdL = orderStatusDao.findByOrderNumber(reqOrdN);
		LocalDateTime updateTime = LocalDateTime.now(); // 訂單變換狀態的時間
		for(OrderStatus reqChaStaOrd : reqChaStaOrdL) { // 要轉換狀態的購物車訂單
			if(reqChaStaOrd.getState().equals("準備中")) {
				OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(
						reqChaStaOrd.getUserId(), reqChaStaOrd.getProductId(), 
						reqChaStaOrd.getUpdateTime())).get(); // 拉出原本資料庫的訂單資料
				OrderStatus chaStaOrd = new OrderStatus(oriOrd.getUserId(),oriOrd.getProductId(), 
						updateTime, oriOrd.getAmount(), "運送中", oriOrd.getOrderNumber(), oriOrd.getAddress()); // 要轉換狀態的訂單資料
				orderStatusDao.delete(oriOrd);
				orderStatusDao.save(chaStaOrd);
				return new OrderStatusResponse("訂單狀態變更成功");
			}
			if(reqChaStaOrd.getState().equals("運送中")) {
				OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(
						reqChaStaOrd.getUserId(), reqChaStaOrd.getProductId(), 
						reqChaStaOrd.getUpdateTime())).get(); // 拉出原本資料庫的訂單資料
				OrderStatus chaStaOrd = new OrderStatus(oriOrd.getUserId(),oriOrd.getProductId(), 
						updateTime, oriOrd.getAmount(), "待收貨", oriOrd.getOrderNumber(), oriOrd.getAddress()); // 要轉換狀態的訂單資料
				orderStatusDao.delete(oriOrd);
				orderStatusDao.save(chaStaOrd);
				return new OrderStatusResponse("訂單狀態變更成功");
			}
			OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(
					reqChaStaOrd.getUserId(), reqChaStaOrd.getProductId(), 
					reqChaStaOrd.getUpdateTime())).get(); // 拉出原本資料庫的訂單資料
			OrderStatus chaStaOrd = new OrderStatus(oriOrd.getUserId(),oriOrd.getProductId(), 
					updateTime, oriOrd.getAmount(), "已完成", oriOrd.getOrderNumber(), oriOrd.getAddress()); // 要轉換狀態的訂單資料
			orderStatusDao.delete(oriOrd);
			orderStatusDao.save(chaStaOrd);
			return new OrderStatusResponse("訂單狀態變更成功");
		}
		return new OrderStatusResponse();
	}

	@Override
	public OrderStatusResponse changeCartAmount(OrderStatusRequest request) {
		OrderStatus reqOrd = request.getOrderStatus();
		OrderStatus oriOrd = orderStatusDao.findById(new UserBuyProduct(reqOrd.getUserId(), 
				reqOrd.getProductId(), reqOrd.getUpdateTime())).get();
		ProductInfo pro = productInfoDao.findById(reqOrd.getProductId()).get();
		if(reqOrd.getAmount() == oriOrd.getAmount()) { // 數量一樣不做動作
			return new OrderStatusResponse();
		}
		if(reqOrd.getAmount() == 0) {
			orderStatusDao.delete(oriOrd);
			return new OrderStatusResponse("移出購物車");
		}
		if(reqOrd.getAmount() > pro.getStock()) {
			oriOrd.setAmount(pro.getStock());
			orderStatusDao.save(oriOrd);
			return new OrderStatusResponse("該商品最多可輸入的數量為: " + pro.getStock());
		}
		oriOrd.setAmount(reqOrd.getAmount());
		orderStatusDao.save(oriOrd);
		return new OrderStatusResponse();
	}
}
