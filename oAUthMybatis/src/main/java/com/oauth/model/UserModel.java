package com.oauth.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;


public class UserModel implements Serializable {
    public UserModel() {
    }

    public UserModel(UserModel user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
        this.accountnonexpired = user.isAccountnonexpired();
        this.credentialsnonexpired = user.isCredentialsnonexpired();
        this.accountnonlocked = user.isAccountnonlocked();
        this.roles = user.getRoles();
    }

    private Integer id;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Role_Permission> getRoles() {
		return roles;
	}

	public void setRoles(List<Role_Permission> roles) {
		this.roles = roles;
	}

    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private boolean accountnonexpired;
    private Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
    
    public Collection<GrantedAuthority> getGrantedAuthoritiesList() {
		return grantedAuthoritiesList;
	}

	public void setGrantedAuthoritiesList(Collection<GrantedAuthority> grantedAuthoritiesList) {
		this.grantedAuthoritiesList = grantedAuthoritiesList;
	}
    public boolean isAccountnonexpired() {
		return accountnonexpired;
	}

	public void setAccountnonexpired(boolean accountnonexpired) {
		this.accountnonexpired = accountnonexpired;
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

    private boolean credentialsnonexpired;
    private boolean accountnonlocked;
    private List<Role_Permission> roles;
}
