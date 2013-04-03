package com.thang.processor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;

public class ModelListHandler<T> implements ResultSetHandler<List<T>> {

	private static final ModelListHandler<?> modelListHandler=new ModelListHandler();
	private final RowProcessor convert=ModelProcessor.getInstance();
	@Override
	public List<T> handle(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> handle(ResultSet rs, Class<?> type) throws SQLException {
		// TODO Auto-generated method stub
		return (List<T>)convert.toBeanList(rs, type);
	}
	
	public static ModelListHandler getInstance(){
		return modelListHandler;
	}

	

}
