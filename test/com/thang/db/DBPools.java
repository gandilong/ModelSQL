package com.thang.db;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DBPools {

	//private BasicDataSourceFactory basicDataSourceFactory=new BasicDataSourceFactory();
	private static Properties pros=new Properties();
	public static DataSource getDataSource(){
		try{
			pros.clear();
		    pros.load(DBPools.class.getClassLoader().getResourceAsStream("dbconfig.properties"));
		    return BasicDataSourceFactory.createDataSource(pros);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
