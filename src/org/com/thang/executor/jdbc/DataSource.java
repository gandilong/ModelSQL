package org.com.thang.executor.jdbc;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;

public class DataSource implements javax.sql.DataSource {

	private Properties properties=null;
	
	public DataSource(Properties pro){
		this.properties=pro;
		DbUtils.loadDriver(properties.getProperty("driverName"));
	}
	
	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
        
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
	}

	@Override
	public Connection getConnection(String username, String password)throws SQLException {
		return DriverManager.getConnection(properties.getProperty("url"), username, password);
	}

}
