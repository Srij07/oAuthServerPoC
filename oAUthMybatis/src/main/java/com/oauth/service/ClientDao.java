package com.oauth.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.oauth.model.ClientModel;

@Repository
public class ClientDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ClientDao.class);

	public ClientModel getClientDetails(String clientId) {

		ClientModel client = getClientByClientId(clientId);
		return client;
	}

	private ClientModel getClientByClientId(String clientId) {
		ClientModel result = null;
		try {
			logger.info("Selecting client by client id");
			ClientModel request = new ClientModel();
			request.setClientId(clientId);
			
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			result = session.selectOne("CLIENT.getByClientId",request);
			session.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
	}

}
