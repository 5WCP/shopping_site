package com.example.shopping_site.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrdNumInfo {

	private String orderNumber;
	
	private String address;
	
	private String phone;
	
	private String state;
	
	private LocalDateTime updateTime;
	
	private List<RespProInfo> proInfoList;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public List<RespProInfo> getProInfoList() {
		return proInfoList;
	}

	public void setProInfoList(List<RespProInfo> proInfoList) {
		this.proInfoList = proInfoList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public OrdNumInfo() {
		
	}

	public OrdNumInfo(String orderNumber, String address, String phone, String state, 
			LocalDateTime updateTime, List<RespProInfo> proInfoList) { // 搜尋訂單號碼資訊
		this.orderNumber = orderNumber;
		this.address = address;
		this.phone = phone;
		this.state = state;
		this.updateTime = updateTime;
		this.proInfoList = proInfoList;
	}
		
}
