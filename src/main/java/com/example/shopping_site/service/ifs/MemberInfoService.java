package com.example.shopping_site.service.ifs;

import com.example.shopping_site.request.MemberInfoRequest;
import com.example.shopping_site.response.MemberInfoResponse;

public interface MemberInfoService {
	
	public MemberInfoResponse signUp(MemberInfoRequest request);
	
	public MemberInfoResponse logIn(MemberInfoRequest request);
	
	public MemberInfoResponse changePwd(MemberInfoRequest request);
	
	public MemberInfoResponse editMemInfo(MemberInfoRequest request);
	
	public MemberInfoResponse findMemInfo(MemberInfoRequest request);
	
	public MemberInfoResponse deleMemInfo(MemberInfoRequest request);
}
