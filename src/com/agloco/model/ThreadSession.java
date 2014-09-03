package com.agloco.model;

import java.io.Serializable;

public class ThreadSession implements Serializable {

	public String userId;
	public String ip;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
