package com.oauth.user;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;


public class UserModel {
	private String username;
	private String password;
	private int id;
	private String email;
	private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
	private String name;
	private boolean credentialsnonexpired;
    private boolean accountnonlocked;
    private boolean enabled;
    private boolean accountnonexpired;
    
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountnonexpired() {
		return accountnonexpired;
	}

	public void setAccountnonexpired(boolean accountnonexpired) {
		this.accountnonexpired = accountnonexpired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCredentialsnonexpired() {
		return credentialsnonexpired;
	}

	public void setCredentialsnonexpired(boolean credentialsnonexpired) {
		this.credentialsnonexpired = credentialsnonexpired;
	}

	public boolean isAccountnonlocked() {
		return accountnonlocked;
	}

	public void setAccountnonlocked(boolean accountnonlocked) {
		this.accountnonlocked = accountnonlocked;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
		return grantedAuthoritiesList;
	}

	public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
		this.grantedAuthoritiesList = grantedAuthoritiesList;
	}
}
