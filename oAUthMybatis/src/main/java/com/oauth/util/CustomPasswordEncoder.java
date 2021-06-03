package com.oauth.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {

		MessageDigest md;
		String hashtext="";
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] messageDigest = md.digest(rawPassword.toString().getBytes());
			BigInteger no = new BigInteger(1,messageDigest);
			hashtext = no.toString(16);
			
			while(hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hashtext;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		MessageDigest md;
		String hashtext="";
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] messageDigest = md.digest(rawPassword.toString().getBytes());
			BigInteger no = new BigInteger(1,messageDigest);
			hashtext = no.toString(16);
			
			while(hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hashtext.equals(encodedPassword);
	}

}
