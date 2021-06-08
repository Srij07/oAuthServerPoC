package com.oauth.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.oauth.model.AuthToken;
import com.oauth.sql.MyBatisSqlSessionFactoryService;

@Repository
public class UserDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);


	public UserModel getUserDetails(String username) throws IOException {

		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		
		UserModel responseModel = getUserByUserName(username);

		if (responseModel != null && responseModel.getUsername() != null) {
			
			grantedAuthoritiesList = getUserRole(responseModel);
			responseModel.setGrantedAuthoritiesList(grantedAuthoritiesList);
			return responseModel;
		}

		return null;
	}

	private Collection<GrantedAuthority> getUserRole(UserModel responseModel) {
		
		List<Integer> roleList = new ArrayList<Integer>();
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		try {
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			roleList = session.selectList("ROLE_PERMISSION.getRole",responseModel);
			session.commit();
			session.close();

			if(null!=roleList && roleList.size()>0) {
				for(int i=0;i<roleList.size();i++) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(String.valueOf(roleList.get(i)));
					grantedAuthoritiesList.add(grantedAuthority);
				}
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return grantedAuthoritiesList;
	}

	//public UserResponse createUser(UserModel request) throws IOException {
		//UserResponse response  = new UserResponse();
		/*
		  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		  
		  String encriptedPswd = passwordEncoder.encode(request.getPassword());
		  request.setPassword(encriptedPswd);
		 */
		
		/*MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
		SqlSession session = sessionService.getSqlSession();
		session.insert("USERS.insert", request);
		session.commit();
		UserRoleMapping userRole = new UserRoleMapping();
		userRole.setRoleId(2);
		userRole.setUserName(request.getUsername());
		session.insert("ROLE.insert", userRole);
		session.commit();
		session.close();
		
		UserModel responseModel = getUserByUserName(request.getUsername());

		response.setEmail(responseModel.getEmail());
		response.setId(responseModel.getId());
		response.setUsername(responseModel.getUsername());;
		response.setStatus("200");
		response.setError("Success");
		response.setMessage(JwtProperties.USER_INSERTED);
		
		return response;
	}*/
	
	public UserModel getUserByUserName(String username) {
		
		UserModel result = null;
		try {
			logger.info("Selecting user by user name");
			UserModel request = new UserModel();
			request.setUsername(username);
			
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			result = session.selectOne("USERS.getByUserName",request);
			session.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return result;
		
	}

	public boolean saveToken(AuthToken token) {
		
		try {
			logger.info("Inserting token");
			
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			session.insert("TOKEN_DETAILS.isertToken",token);
			session.commit();
			session.close();
			return true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		
		
	}

	public AuthToken getToken(String access_code) {
		
		AuthToken result = null;
		try {
			logger.info("Selecting token");
			
			AuthToken request = new AuthToken();
			request.setAuth_code(access_code);
			
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			result = session.selectOne("TOKEN_DETAILS.getTokenByCode",request);
			session.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return result;
	}

}
