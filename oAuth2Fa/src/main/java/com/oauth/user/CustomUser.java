package com.oauth.user;

import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private boolean twofaenabled;
	private String pin;
	
	public CustomUser(UserModel userModel) {
		super(userModel.getUsername(), userModel.getPassword(),userModel.isEnabled(),
				userModel.isAccountnonexpired(),userModel.isCredentialsnonexpired(),userModel.isAccountnonlocked(),userModel.getGrantedAuthoritiesList());
		this.setTwofaenabled(userModel.isTwofaenabled());
		this.setPin(userModel.getPin());
		
	}

	public boolean isTwofaenabled() {
		return twofaenabled;
	}

	public void setTwofaenabled(boolean twofaenabled) {
		this.twofaenabled = twofaenabled;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
}