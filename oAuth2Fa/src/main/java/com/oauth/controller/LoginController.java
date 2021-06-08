package com.oauth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.oauth.model.AuthCodeResponse;
import com.oauth.model.AuthToken;
import com.oauth.model.JwtRequest;
import com.oauth.model.JwtResponse;
import com.oauth.model.TokenRequest;
import com.oauth.user.CustomUser;
import com.oauth.user.UserDAO;
import com.oauth.util.CustomPasswordEncoder;
import com.oauth.util.JwtProperties;
import com.oauth.util.JwtTokenUtil;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class LoginController {
	
	Date date;
    SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
    
    @Autowired
	UserDAO userDAO;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationCodeOrToken(@RequestBody String input){
		date = new Date();
		 JwtRequest authenticationRequest =  new Gson().fromJson(input, JwtRequest.class);
		String status = authenticate(authenticationRequest.getUser_name(), authenticationRequest.getPassword());
		
		if(status.equalsIgnoreCase("Y")) {
			CustomUser userDetails = (CustomUser) jwtInMemoryUserDetailsService
					.loadUserByUsername(authenticationRequest.getUser_name());
			
			//Extracting Role
			Collection<GrantedAuthority> authoritiesList = (Collection<GrantedAuthority>) userDetails.getAuthorities();
			List<String> roleList = new ArrayList<String>();
			if(authoritiesList!=null && authoritiesList.size()>0) {
			Iterator<GrantedAuthority> iterator = authoritiesList.iterator();
			while (iterator.hasNext()) {
				GrantedAuthority grantedAuthority = iterator.next();
				roleList.add(grantedAuthority.getAuthority());
			}}
			
			if(userDetails.isTwofaenabled()) {

				final String code = jwtTokenUtil.generateCode(userDetails);

				AuthToken token = new AuthToken();
				token.setAuth_code(code);
				token.setUser_name(userDetails.getUsername());

				boolean saved = userDAO.saveToken(token);


				//final String accessToken = jwtTokenUtil.generateToken(userDetails,"access");
				//final String refreshToken = jwtTokenUtil.generateToken(userDetails,"refresh");
				if(saved) {
					return ResponseEntity.ok(new JwtResponse(ft.format(date),JwtProperties.SUCCESS_STATUS,"",JwtProperties.SUCCESS_MESSAGE,"/api/user/authenticate",null,null,null,null,authenticationRequest.getUser_name(),code,"Y"));
				}
				else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JwtResponse(ft.format(date),"500","Internal Server Error","Internal Server Error","/api/user/authenticate",null,null,null,null,authenticationRequest.getUser_name(),code,"Y"));
				}
			}
			else {
				final String accessToken = jwtTokenUtil.generateToken(userDetails,"access");
				final String refreshToken = jwtTokenUtil.generateToken(userDetails,"refresh");
				return ResponseEntity.ok(new JwtResponse(ft.format(date),JwtProperties.SUCCESS_STATUS,"",JwtProperties.SUCCESS_MESSAGE,"/api/user/token",accessToken,refreshToken,roleList,JwtProperties.TOKEN_TYPE,userDetails.getUsername(),null,"N"));
			}
			
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(ft.format(date),JwtProperties.UNAUTHORIZED_STATUS,JwtProperties.UNAUTHORIZED_MESSAGE,status,"/api/user/authenticate",null,null,null,JwtProperties.TOKEN_TYPE,null,null,"N"));
		}

		
	}
	
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody String input){
		date = new Date();
		TokenRequest tokenRequest =  new Gson().fromJson(input, TokenRequest.class);

		AuthToken token = userDAO.getToken(tokenRequest.getAccess_code());

		if(null!= token && null!= token.getUser_name()) {
			CustomUser userDetails = (CustomUser) jwtInMemoryUserDetailsService
					.loadUserByUsername(token.getUser_name());

			//Extracting Role
			Collection<GrantedAuthority> authoritiesList = (Collection<GrantedAuthority>) userDetails.getAuthorities();
			List<String> roleList = new ArrayList<String>();
			if(authoritiesList!=null && authoritiesList.size()>0) {
				Iterator<GrantedAuthority> iterator = authoritiesList.iterator();
				while (iterator.hasNext()) {
					GrantedAuthority grantedAuthority = iterator.next();
					roleList.add(grantedAuthority.getAuthority());
				}}

			CustomPasswordEncoder encoder = new CustomPasswordEncoder();
			boolean isPinMatched = encoder.matches(tokenRequest.getPin(), userDetails.getPin());
			if(isPinMatched) {
				final String accessToken = jwtTokenUtil.generateToken(userDetails,"access");
				final String refreshToken = jwtTokenUtil.generateToken(userDetails,"refresh");

				return ResponseEntity.ok(new JwtResponse(ft.format(date),JwtProperties.SUCCESS_STATUS,"",JwtProperties.SUCCESS_MESSAGE,"/api/user/token",accessToken,refreshToken,roleList,JwtProperties.TOKEN_TYPE,userDetails.getUsername(),null,null));
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(ft.format(date),JwtProperties.UNAUTHORIZED_STATUS,JwtProperties.PIN_NOT_MATCHED_MESSAGE,JwtProperties.PIN_NOT_MATCHED_MESSAGE,"/api/user/token",null,null,null,null,null,null,null));
			}
		}
		else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(ft.format(date),JwtProperties.UNAUTHORIZED_STATUS,JwtProperties.UNAUTHORIZED_MESSAGE,"401","/api/user/token",null,null,null,null,null,null,null));
		}


	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationTokenFromRefreshToken(@RequestBody String input)
			throws Exception {

		date = new Date();
		JwtRequest authenticationRequest =  new Gson().fromJson(input, JwtRequest.class);
		Boolean isAccess = jwtTokenUtil.isAccessToken(authenticationRequest.getRefresh_token());

		if(!isAccess) {
			if(!jwtTokenUtil.isTokenExpired(authenticationRequest.getRefresh_token())) {

				String username = jwtTokenUtil.getUsernameFromToken(authenticationRequest.getRefresh_token());
				final UserDetails userDetails = jwtInMemoryUserDetailsService
						.loadUserByUsername(username);
				
				//Extracting Role
				Collection<GrantedAuthority> authoritiesList = (Collection<GrantedAuthority>) userDetails.getAuthorities();
				List<String> roleList = new ArrayList<String>();
				if(authoritiesList!=null && authoritiesList.size()>0) {
				Iterator<GrantedAuthority> iterator = authoritiesList.iterator();
				while (iterator.hasNext()) {
					GrantedAuthority grantedAuthority = iterator.next();
					roleList.add(grantedAuthority.getAuthority());
				}}

				final String accessToken = jwtTokenUtil.generateToken(userDetails,"access");
				final String refreshToken = jwtTokenUtil.generateToken(userDetails,"refresh");

				return ResponseEntity.ok(new JwtResponse(ft.format(date),JwtProperties.SUCCESS_STATUS,"",JwtProperties.SUCCESS_MESSAGE,"/api/userrefresh",accessToken,refreshToken,roleList,JwtProperties.TOKEN_TYPE,username,null,null));
			}
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(ft.format(date),JwtProperties.UNAUTHORIZED_STATUS,JwtProperties.UNAUTHORIZED_MESSAGE,JwtProperties.REFRESH_TOKEN_EXPIRED,"/api/user/refresh","","",null,JwtProperties.TOKEN_TYPE,null,null,null));
			}

		}
		else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtResponse(ft.format(date),JwtProperties.UNAUTHORIZED_STATUS,JwtProperties.UNAUTHORIZED_MESSAGE,JwtProperties.ACCESS_TOKEN_ERROR,"/api/user/refresh","","",null,JwtProperties.TOKEN_TYPE,null,null,null));
		}


	}

//	private void authenticate(String username, String password) throws Exception {
	private String authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return "Y";
		} catch (DisabledException e) {
			return "USER DISABLED";
//			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			return "INVALID CREDENTIALS";
//			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@GetMapping("/test")
	public String test() {
		return "Hi Testing";
	}
	
	/*@PostMapping("/save")
	public  ResponseEntity<UserResponse> saveDesign(@RequestBody String input) {

		UserModel request = new UserModel();
		UserResponse response  = new UserResponse();
		date = new Date();
		try {
			request = new Gson().fromJson(input, UserModel.class);
			UserModel checkUser = new UserModel();
			checkUser = userDAO.getUserByUserName(request.getUsername());
			if(null!=checkUser && null!= checkUser.getUsername() && !"".equalsIgnoreCase(checkUser.getUsername())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse("User Name Already Present","400",0,"Failed","/api/user/save",ft.format(date),null,null));
			}
			else {
				response = userDAO.createUser(request);	
			}
		}
		catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(e.getMessage(),"400",0,"Failed","/api/user/save",ft.format(date),null,null));

		}

		return ResponseEntity.status(HttpStatus.OK).body(new UserResponse(response.getError(),response.getStatus(),response.getId(),response.getMessage(),"/api/user/save",ft.format(date),response.getEmail(),response.getUsername()));
	}*/

}
