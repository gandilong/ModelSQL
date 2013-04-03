package com.thang.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

public class ConnectionUtils {

	private static Properties pros=null;
	private static Connection conn=null;
	static{
		try{
		    pros=new Properties();
		    pros.load(ConnectionUtils.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
		    DbUtils.loadDriver(pros.getProperty("driverClassName"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static Connection getConn(){
		try{
		    if(null==conn||!conn.isClosed()){
			    conn=DriverManager.getConnection(pros.getProperty("url"),pros.getProperty("username"), pros.getProperty("password"));
		    }
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return conn;
	}
	
	public static void closeConnection(){
		DbUtils.closeQuietly(conn);
	}
	
}
