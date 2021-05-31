package com.oauth.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.oauth.model.ClientModel;
import com.oauth.model.CustomClient;

@Service
public class CustomClientService implements ClientDetailsService{

	@Autowired
	ClientDao clientDao;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		ClientModel clientModel;
		//CustomClient customClient;
		
		clientModel = clientDao.getClientDetails(clientId);
		//customClient = new CustomClient(clientModel);
		BaseClientDetails customClient = new BaseClientDetails(clientModel.getClientId(), clientModel.getResourceIds(), clientModel.getScope(), clientModel.getAuthorizedGrantTypes(), clientModel.getAuthorities());
		customClient.setClientSecret(clientModel.getClientSecret());
		customClient.setAccessTokenValiditySeconds(clientModel.getAccessTokenValidity());
		customClient.setRefreshTokenValiditySeconds(clientModel.getRefreshTokenValidity());
		//customClient.setAdditionalInformation(clientModel.getAdditionalInfo();
		customClient.setAutoApproveScopes(Arrays.asList(clientModel.getScope().split(",")));
        return customClient;
	}

}
