package com.oauth.model;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class CustomClient extends BaseClientDetails{
	
	public CustomClient(ClientModel clientModel) {
		super(clientModel.getClientId(),clientModel.getResourceIds(),clientModel.getScope(),clientModel.getAuthorizedGrantTypes(),clientModel.getAuthorities());
	}

}
