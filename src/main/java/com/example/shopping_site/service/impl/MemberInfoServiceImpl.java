package com.example.shopping_site.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.MemberInfo;
import com.example.shopping_site.entity.OrderStatus;
import com.example.shopping_site.entity.ProductInfo;
import com.example.shopping_site.repository.MemberInfoDao;
import com.example.shopping_site.repository.OrderStatusDao;
import com.example.shopping_site.repository.ProductInfoDao;
import com.example.shopping_site.request.MemberInfoRequest;
import com.example.shopping_site.response.MemberInfoResponse;
import com.example.shopping_site.service.ifs.MemberInfoService;

@Service
public class MemberInfoServiceImpl implements MemberInfoService {
	
	@Autowired
	private MemberInfoDao memberInfoDao;
	
	@Autowired
	private ProductInfoDao productInfoDao;
	
	@Autowired
	private OrderStatusDao orderStatusDao;

	@Override
	public MemberInfoResponse signUp(MemberInfoRequest request) {
		String userIdFormat = "[a-zA-Z0-9]{2,8}";
		String pwdFormat = "^(?=(?:.*\\d){3})(?=.*[A-Z]+)(?=.*[a-z]+)[a-zA-Z0-9]{8,15}$";
		String phoneFormat = "^09\\d{8}$";
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())
			||!StringUtils.hasText(reqMem.getPassword())
			||!StringUtils.hasText(request.getPwdCheck())
			||!StringUtils.hasText(reqMem.getName())
			||!StringUtils.hasText(reqMem.getMail())
			||!StringUtils.hasText(reqMem.getPhone())
			||!Optional.ofNullable(reqMem.getBirthDate()).isPresent()) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		if(memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("該用戶名已存在");
		}
		List<MemberInfo> checkPwd = memberInfoDao.findByPassword(reqMem.getPassword());
		if(!checkPwd.isEmpty()) {
			return new MemberInfoResponse("該密碼已有人使用");
		}
		List<MemberInfo> checkMail = memberInfoDao.findByMail(reqMem.getMail());
		if(!checkMail.isEmpty()) {
			return new MemberInfoResponse("該信箱已註冊過");
		}
		List<MemberInfo> checkPhone = memberInfoDao.findByPhone(reqMem.getPhone());
		if(!checkPhone.isEmpty()) {
			return new MemberInfoResponse("該手機已註冊過");
		}
		if(!reqMem.getPassword().equals(request.getPwdCheck())) {
			return new MemberInfoResponse("密碼欄位與密碼再輸入欄位不相同");
		}
		if(!reqMem.getUserId().matches(userIdFormat)) {
			return new MemberInfoResponse("用戶名格式錯誤，用戶名須符合二到八位的英文或數字");
		}
		if(!reqMem.getPassword().matches(pwdFormat)) {
			return new MemberInfoResponse("密碼格式錯誤，密碼需符合大小寫英文字母至少各一個，數字至少"
					+ "三個，長度最少八個、最多十五個");
		}
		if(!reqMem.getPhone().matches(phoneFormat)) {
			return new MemberInfoResponse("手機格式錯誤");
		}
		reqMem.setActive(true);
		LocalDateTime signUpTime = LocalDateTime.now();
		reqMem.setSignUpTime(signUpTime);
		memberInfoDao.save(reqMem);
		return new MemberInfoResponse("用戶名(" + reqMem.getUserId() + ")註冊成功");
	}
	
	@Override
	public MemberInfoResponse logIn(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())
			||!StringUtils.hasText(reqMem.getPassword())) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("用戶名不存在或密碼錯誤");
		}
		MemberInfo mem = memberInfoDao.findById(reqMem.getUserId()).get();
		if(!reqMem.getPassword().equals(mem.getPassword())) {
			return new MemberInfoResponse("用戶名不存在或密碼錯誤");
		}
		if(mem.isActive() == false) {
			return new MemberInfoResponse("該用戶品處於停用狀態");
		}
		return new MemberInfoResponse("用戶名(" + reqMem.getUserId() + ")登入成功");
	}

	@Override
	public MemberInfoResponse changePwd(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		String pwdFormat = "^(?=(?:.*\\d){3})(?=.*[A-Z]+)(?=.*[a-z]+)[a-zA-Z0-9]{8,15}$";
		if(!StringUtils.hasText(reqMem.getUserId())) {
			return new MemberInfoResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("該用戶品非本站用戶(異常)");
		}
		if(!StringUtils.hasText(reqMem.getPassword())
			|| !StringUtils.hasText(request.getNewPwd())
			|| !StringUtils.hasText(request.getNewPwdCheck())) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		if(!request.getNewPwd().matches(pwdFormat)) {
			return new MemberInfoResponse("新密碼格式錯誤 密碼需符合大小寫英文字母至少各一個，數字至少"
					+ "三個，長度最少八個、最多十五個的英文字母和數字");
		}
		MemberInfo mem = memberInfoDao.findById(reqMem.getUserId()).get();
		if(!reqMem.getPassword().equals(mem.getPassword())) {
			return new MemberInfoResponse("密碼錯誤");
		}
		if(reqMem.getPassword().equals(request.getNewPwd())) {
			return new MemberInfoResponse("預修改的新密碼與舊密碼相同，不需修改");
		}
		if(!request.getNewPwd().equals(request.getNewPwdCheck())) {
			return new MemberInfoResponse("兩次輸入的新密碼不相同");
		}
		List<MemberInfo> checkNewPwd = memberInfoDao.findByPassword(request.getNewPwd());
		if(!checkNewPwd.isEmpty()) {
			return new MemberInfoResponse("新密碼已有人使用");
		}
		mem.setPassword(request.getNewPwd());
		memberInfoDao.save(mem);
		return new MemberInfoResponse("密碼修改成功");
	}

	@Override
	public MemberInfoResponse editMemInfo(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		String phoneFormat = "^09\\d{8}$";
		if(!StringUtils.hasText(reqMem.getUserId())) {
			return new MemberInfoResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("該用戶品非本站用戶(異常)");
		}
		if(!StringUtils.hasText(reqMem.getMail())
			||!StringUtils.hasText(reqMem.getName())
			||!StringUtils.hasText(reqMem.getPhone())
			||!Optional.ofNullable(reqMem.getBirthDate()).isPresent()) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		MemberInfo mem = memberInfoDao.findById(reqMem.getUserId()).get();
		if(reqMem.getMail().equals(mem.getMail())
			&& reqMem.getName().equals(mem.getName())
			&& reqMem.getBirthDate().equals(mem.getBirthDate())
			&& reqMem.getPhone().equals(mem.getPhone())) {
			return new MemberInfoResponse(0);
		}
		if(!reqMem.getBirthDate().equals(mem.getBirthDate())) {
			mem.setBirthDate(reqMem.getBirthDate());
		}
		if(!reqMem.getName().equals(mem.getName())) {
			mem.setName(reqMem.getName());
		}
		List<MemberInfo> checkMail = memberInfoDao.findByMail(reqMem.getMail());
		if(!checkMail.isEmpty() && !reqMem.getMail().equals(mem.getMail())) {
			return new MemberInfoResponse("預修改的信箱已有人使用");
		}
		if(!reqMem.getMail().equals(mem.getMail())) {
			mem.setMail(reqMem.getMail());
		}
		List<MemberInfo> checkPhone = memberInfoDao.findByPhone(reqMem.getPhone());
		if(!checkPhone.isEmpty() && !reqMem.getPhone().equals(mem.getPhone())) {
			return new MemberInfoResponse("預修改的電話已有人使用");
		}
		if(!reqMem.getPhone().matches(phoneFormat)) {
			return new MemberInfoResponse("手機格式錯誤");
		}
		if(!reqMem.getPhone().equals(mem.getPhone())) {
			mem.setPhone(reqMem.getPhone());
		}
		memberInfoDao.save(mem);
		return new MemberInfoResponse("資料修改成功");
	}

	@Override
	public MemberInfoResponse findMemInfo(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())) {
			return new MemberInfoResponse("帳號未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("登入帳號非本站會員(異常)");
		}
		MemberInfo userInfo = memberInfoDao.findById(reqMem.getUserId()).get();
		return new MemberInfoResponse(userInfo);
	}

	@Override
	public MemberInfoResponse deleMemInfo(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())) {
			return new MemberInfoResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("該用戶品非本站用戶(異常)");
		}
		List<ProductInfo> sellProList = new ArrayList<>(); // 找出會員的販賣商品
		sellProList = productInfoDao.findByUserId(reqMem.getUserId());
		if(!sellProList.isEmpty()) {
			return new MemberInfoResponse("尚有販賣商品 請先刪除");
		}
		List<OrderStatus> memOrdList = new ArrayList<>(); // 找出會員的下單資料
		memOrdList = orderStatusDao.findByUserId(reqMem.getUserId());
		if(!memOrdList.isEmpty()) {
			for(OrderStatus memOrd : memOrdList) {
				if(memOrd.getState().equals("考慮中")) {
					return new MemberInfoResponse("請先刪除購物車的商品");
				}
				if(memOrd.getState().equals("準備中") || memOrd.getState().equals("運送中")
						|| memOrd.getState().equals("待收貨")) {
					return new MemberInfoResponse("尚有已下單的商品 無法刪除");
				}
			}
		}
		MemberInfo deleMem = memberInfoDao.findById(reqMem.getUserId()).get();
		memberInfoDao.delete(deleMem);
		return new MemberInfoResponse("會員刪除成功");
	}

	@Override
	public MemberInfoResponse disableMemInfo(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())) {
			return new MemberInfoResponse("尚未登入(異常)");
		}
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("該用戶品非本站用戶(異常)");
		}
		List<ProductInfo> sellProList = new ArrayList<>(); // 找出會員的販賣商品
		sellProList = productInfoDao.findByUserId(reqMem.getUserId());
		if(!sellProList.isEmpty()) {
			return new MemberInfoResponse("尚有販賣商品 請先刪除");
		}
		List<OrderStatus> memOrdList = new ArrayList<>(); // 找出會員的下單資料
		memOrdList = orderStatusDao.findByUserId(reqMem.getUserId());
		if(!memOrdList.isEmpty()) {
			for(OrderStatus memOrd : memOrdList) {
				if(memOrd.getState().equals("考慮中")) {
					return new MemberInfoResponse("請先刪除購物車的商品");
				}
				if(memOrd.getState().equals("準備中") || memOrd.getState().equals("運送中")
						|| memOrd.getState().equals("待收貨")) {
					return new MemberInfoResponse("尚有已下單的商品 無法停用");
				}
			}
		}
		MemberInfo disableMem = memberInfoDao.findById(reqMem.getUserId()).get();
		disableMem.setActive(false);
		memberInfoDao.save(disableMem);
		return new MemberInfoResponse("會員已停用");
	}
}
