package com.example.shopping_site.request;

import com.example.shopping_site.entity.MemberInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MemberInfoRequest {
	
	@JsonProperty("member_info")
	private MemberInfo memberInfo;
	
	@JsonProperty("pwd_check")
	private String pwdCheck;
	
	@JsonProperty("new_pwd")
	private String newPwd;
	
	@JsonProperty("new_pwd_check")
	private String newPwdCheck;

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getNewPwdCheck() {
		return newPwdCheck;
	}

	public void setNewPwdCheck(String newPwdCheck) {
		this.newPwdCheck = newPwdCheck;
	}

	public MemberInfo getMemberInfo() {
		return memberInfo;
	}

	public void setMemberInfo(MemberInfo memberInfo) {
		this.memberInfo = memberInfo;
	}

	public String getPwdCheck() {
		return pwdCheck;
	}

	public void setPwdCheck(String pwdCheck) {
		this.pwdCheck = pwdCheck;
	}
	
}
