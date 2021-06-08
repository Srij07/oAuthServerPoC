package com.oauth.model;

import java.util.List;

public class JwtResponse {

	private String timestamp;
	private String status;
	private String error;
	private String message;
	private String path;
	private String access_token;
	private String refresh_token;
	private String token_type;
	private List<String> role_list;
	private String access_code;
	private String two_fa_flag;
	private String user_name;
	
	
	
	public JwtResponse(String timestamp, String status, String error, String message, String path,
			String jwtAccessToken, String jwtRefreshToken,List<String> collection, String tokenType,
			String username, String accessCode, String twofaFlag) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
		this.access_token = jwtAccessToken;
		this.refresh_token = jwtRefreshToken;
		this.token_type = tokenType;
		this.role_list = collection;
		this.user_name = username;
		this.access_code = accessCode;
		this.two_fa_flag = twofaFlag;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public List<String> getRole_list() {
		return role_list;
	}
	public void setRole_list(List<String> role_list) {
		this.role_list = role_list;
	}
	public String getTwo_fa_flag() {
		return two_fa_flag;
	}
	public void setTwo_fa_flag(String two_fa_flag) {
		this.two_fa_flag = two_fa_flag;
	}
	public String getAccess_code() {
		return access_code;
	}
	public void setAccess_code(String access_code) {
		this.access_code = access_code;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
