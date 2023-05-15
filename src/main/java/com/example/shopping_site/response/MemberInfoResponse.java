package com.example.shopping_site.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberInfoResponse {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MemberInfoResponse(String message) {
		this.message = message;
	}

	public MemberInfoResponse() {
		
	}
	
	
}
