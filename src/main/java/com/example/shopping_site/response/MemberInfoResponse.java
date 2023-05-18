package com.example.shopping_site.response;

import com.example.shopping_site.entity.MemberInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponse {
	
	private String message;
	
	@JsonProperty("member_info")
	private MemberInfo memberInfo;
	
	private int re;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public int getRe() {
		return re;
	}

	public void setRe(int re) {
		this.re = re;
	}

	public MemberInfoResponse(String message) {
		this.message = message;
	}

	public MemberInfoResponse() {
		
	}

	public MemberInfoResponse(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public MemberInfoResponse(int re) {
		this.re = re;
	}
	
}
