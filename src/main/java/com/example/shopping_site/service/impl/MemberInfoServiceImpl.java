package com.example.shopping_site.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.shopping_site.entity.MemberInfo;
import com.example.shopping_site.repository.MemberInfoDao;
import com.example.shopping_site.request.MemberInfoRequest;
import com.example.shopping_site.response.MemberInfoResponse;
import com.example.shopping_site.service.ifs.MemberInfoService;

@Service
public class MemberInfoServiceImpl implements MemberInfoService {
	
	@Autowired
	private MemberInfoDao memberInfoDao;

	@Override
	public MemberInfoResponse signUp(MemberInfoRequest request) {
		String userIdFormat = "[a-zA-Z0-9]{2,8}";
		String pwdFormat = "^(?=.*\\d{3,})(?=.*[A-Z]+)(?=.*[a-z]+).{8,15}$";	
		MemberInfo reqMem = request.getMemberInfo();
		if(!StringUtils.hasText(reqMem.getUserId())
			||!StringUtils.hasText(reqMem.getPassword())
			||!StringUtils.hasText(request.getPwdCheck())
			||!StringUtils.hasText(reqMem.getName())
			||!StringUtils.hasText(reqMem.getMail())
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
			return new MemberInfoResponse("用戶名不存在");
		}
		MemberInfo mem = memberInfoDao.findById(reqMem.getUserId()).get();
		if(!reqMem.getPassword().equals(mem.getPassword())) {
			return new MemberInfoResponse("密碼錯誤");
		}
		return new MemberInfoResponse("用戶名(" + reqMem.getUserId() + ")登入成功");
	}

	@Override
	public MemberInfoResponse changePwd(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		String pwdFormat = "^(?=.*\\d{3,})(?=.*[A-Z]+)(?=.*[a-z]+).{8,15}$";
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("用戶名不存在");
		}
		if(!StringUtils.hasText(reqMem.getPassword())
			|| !StringUtils.hasText(request.getNewPwd())
			|| !StringUtils.hasText(request.getNewPwdCheck())) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		if(!request.getNewPwd().matches(pwdFormat)) {
			return new MemberInfoResponse("新密碼格式錯誤 密碼需符合大小寫英文字母至少各一個，數字至少"
					+ "三個，長度最少八個、最多十五個");
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
	public MemberInfoResponse reviseMemInfo(MemberInfoRequest request) {
		MemberInfo reqMem = request.getMemberInfo();
		if(!memberInfoDao.existsById(reqMem.getUserId())) {
			return new MemberInfoResponse("用戶名不存在");
		}
		if(!StringUtils.hasText(reqMem.getMail())
			||!StringUtils.hasText(reqMem.getName())
			||!Optional.ofNullable(reqMem.getBirthDate()).isPresent()) {
			return new MemberInfoResponse("需填寫欄位請確實填寫");
		}
		List<MemberInfo> checkMail = memberInfoDao.findByMail(reqMem.getMail());
		if(!checkMail.isEmpty()) {
			return new MemberInfoResponse("預修改的信箱已有人使用");
		}
		MemberInfo mem = memberInfoDao.findById(reqMem.getUserId()).get();
		mem.setBirthDate(reqMem.getBirthDate());
		mem.setMail(reqMem.getMail());
		mem.setName(reqMem.getName());
		memberInfoDao.save(mem);
		return new MemberInfoResponse("資料修改成功");
	}
}
