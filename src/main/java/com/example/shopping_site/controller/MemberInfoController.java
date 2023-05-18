package com.example.shopping_site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping_site.request.MemberInfoRequest;
import com.example.shopping_site.response.MemberInfoResponse;
import com.example.shopping_site.service.ifs.MemberInfoService;

@CrossOrigin
@RestController
public class MemberInfoController {
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@PostMapping("sign_up")
	public MemberInfoResponse signUp(@RequestBody MemberInfoRequest request) {
		return memberInfoService.signUp(request);
	}
	
	@PostMapping("log_in")
	public MemberInfoResponse logIn(@RequestBody MemberInfoRequest request) {
		return memberInfoService.logIn(request);
	}
	
	@PostMapping("change_pwd")
	public MemberInfoResponse changePwd(@RequestBody MemberInfoRequest request) {
		return memberInfoService.changePwd(request);
	}
	
	@PostMapping("edit_mem_info")
	public MemberInfoResponse reviseMemInfo(@RequestBody MemberInfoRequest request) {
		return memberInfoService.editMemInfo(request);
	}
	
	@PostMapping("find_mem_info")
	public MemberInfoResponse findMemInfo(@RequestBody MemberInfoRequest request) {
		return memberInfoService.findMemInfo(request);
	}
	
	@PostMapping("dele_mem_info")
	public MemberInfoResponse deleMemInfo(@RequestBody MemberInfoRequest request) {
		return memberInfoService.deleMemInfo(request);
	}
}
