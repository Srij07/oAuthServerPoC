package com.oauth.model;

import java.util.Date;

public class AuthToken {
	
	private String auth_code;
	private String oauth_token;
	private String user_name;
	private Date request_time;
	private Date activation_time;
	private Date expiry_time;
	
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getOauth_token() {
		return oauth_token;
	}
	public void setOauth_token(String oauth_token) {
		this.oauth_token = oauth_token;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getRequest_time() {
		return request_time;
	}
	public void setRequest_time(Date request_time) {
		this.request_time = request_time;
	}
	public Date getActivation_time() {
		return activation_time;
	}
	public void setActivation_time(Date activation_time) {
		this.activation_time = activation_time;
	}
	public Date getExpiry_time() {
		return expiry_time;
	}
	public void setExpiry_time(Date expiry_time) {
		this.expiry_time = expiry_time;
	}
	
	

}
