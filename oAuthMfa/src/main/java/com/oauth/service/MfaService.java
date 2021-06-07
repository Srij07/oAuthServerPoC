package com.oauth.service;

import org.springframework.stereotype.Service;

@Service
public class MfaService {
	
	 public boolean isEnabled(String username) {
	        return true;
	    }

	    public boolean verifyCode(String username, int code) {
	        return true;
	    }

}
