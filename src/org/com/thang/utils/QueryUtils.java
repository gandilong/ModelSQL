package org.com.thang.utils;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SQLGener;
import org.com.thang.processor.common.FCProcessor;

public class QueryUtils {

	private static QueryRunner queryRunner=new QueryRunner();
	
	public static List<?> query(Class<?> modelC){
		List<?> data=null;
		System.out.println(QueryUtils.class.getClasses());
		try {
			data=queryRunner.query(DBConfig.getConnection(),SQLGener.getSelectSQL(modelC).select(false).toString(), new BeanListHandler(modelC,FCProcessor.getInstance()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
