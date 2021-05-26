package com.oauth.service;

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

import com.oauth.model.Role_Permission;
import com.oauth.model.UserModel;

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
		
		List<Role_Permission> rolePermissionList = new ArrayList();
		Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
		try {
			MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
			SqlSession session = sessionService.getSqlSession();
			rolePermissionList = session.selectList("ROLE_PERMISSION.getRolePermission",responseModel);
			session.commit();
			session.close();

			if(null!=rolePermissionList && rolePermissionList.size()>0) {
				for(Role_Permission rolePermission : rolePermissionList) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(rolePermission.getPermission());
					grantedAuthoritiesList.add(grantedAuthority);
				}
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return grantedAuthoritiesList;
	}

	
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

	/*public UserResponse createUser(UserModel request) throws IOException {
		UserResponse response  = new UserResponse();
		
		MyBatisSqlSessionFactoryService sessionService = MyBatisSqlSessionFactoryService.getInstance();
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

}
