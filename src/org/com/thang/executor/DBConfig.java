package org.com.thang.executor;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.com.thang.executor.jdbc.DataSource;


public class DBConfig {

	private static Properties properties=null;
	private static DataSource dataSource=null;
	
	
	private DBConfig(){};
	
	static{
		init();
	}
	
	private static void init(){
		try{
		    properties=new Properties();
		    properties.load(DBConfig.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
		    dataSource=new DataSource(properties);
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		if(null==dataSource){
			init();
		}
		return dataSource.getConnection();
	}
	
	public static DataSource getDataSource(){
		if(null==dataSource){
			init();
		}
		return dataSource;
	}
	
	
}
