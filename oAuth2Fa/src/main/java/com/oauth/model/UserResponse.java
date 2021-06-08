package com.oauth.model;

public class UserResponse {

	private String error;
	private String status;
	private int id;
	private String message;
	private String path;
	private String timestamp;
	private String email;
	private String username;
	
	public UserResponse() {};
	
	public UserResponse(String error, String status, int id, String message, String path, String timestamp,
			String email, String username) {
		super();
		this.error = error;
		this.status = status;
		this.id = id;
		this.message = message;
		this.path = path;
		this.timestamp = timestamp;
		this.email = email;
		this.username = username;
	}
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
