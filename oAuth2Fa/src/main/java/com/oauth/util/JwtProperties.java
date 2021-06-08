package com.oauth.util;

public class JwtProperties {
	public static final String SECRET = "secret";
    public static final int ACCESS_TOKEN_EXPIRATION_TIME = 25*60; // 25 mnts
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 50*60; // 50 mnts
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_TYPE="Bearer";
    public static final String HEADER_STRING = "Authorization";
    public static final String SUCCESS_STATUS= "200";
    public static final String UNAUTHORIZED_STATUS = "401";
    public static final String SUCCESS_MESSAGE= "Success";
    public static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    public static final String PIN_NOT_MATCHED_MESSAGE= "Incorrect Pin";
    public static final String ACCESS_TOKEN_ERROR = "In Space of Refresh Token, Access Token Sent";
    public static final String REFRESH_TOKEN_EXPIRED= "Refresh Token Expired";
    public static final String USER_INSERTED = "User Successfully Created";

}
