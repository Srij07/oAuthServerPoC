package com.oauth.sql;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBatisSqlSessionFactoryService {

	private static MyBatisSqlSessionFactoryService instance = null;
	private SqlSessionFactory sqlSessionFactory;
	Reader reader;
	private static final Logger log = LoggerFactory.getLogger(MyBatisSqlSessionFactoryService.class);

	private MyBatisSqlSessionFactoryService() {
		Properties properties = new Properties();
		try {
			log.info("Inside MyBatisSqlSessionFactoryService Constructor");
			properties = getProperty();
			reader = Resources.getResourceAsReader("SqlMapConfig.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,properties);
			//			SqlSession session = sqlSessionFactory.openSession(); 
		}
		catch(Exception e) {
			log.error(e.getMessage());
		}
	}

	public static MyBatisSqlSessionFactoryService getInstance() {
		if(instance == null) {
			instance = new MyBatisSqlSessionFactoryService();
		}
		return instance;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}

	public Properties getProperty() throws IOException {
		log.info("Inside getProperty method");
		Properties properties = new Properties();
		URL url1 = ClassLoader.getSystemResource("database.properties");
		properties.load(url1.openStream());
		//properties.load(new FileInputStream("database.properties"));
		String encodedUsername = properties.getProperty("db.username");
		String encodedPassword = properties.getProperty("db.password");
		String encodedUrl = properties.getProperty("db.url");
		String encodedDriver = properties.getProperty("db.driver");

		byte[] usernameBytes = Base64.getDecoder().decode(encodedUsername);
		String username = new String(usernameBytes);
		byte[] passwordBytes = Base64.getDecoder().decode(encodedPassword);
		String password = new String(passwordBytes);
		byte[] urlBytes = Base64.getDecoder().decode(encodedUrl);
		String url = new String(urlBytes);
		byte[] driverBytes = Base64.getDecoder().decode(encodedDriver);
		String driver = new String(driverBytes);

		properties.setProperty("username", username);
		properties.setProperty("password", password);
		properties.setProperty("driver", driver);
		properties.setProperty("url", url);

		return properties;

	}
}
