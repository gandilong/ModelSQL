package org.com.thang.utils;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.com.thang.db.sql.SQL;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SQLGener;
import org.com.thang.model.ActionValues;
import org.com.thang.model.Page;
import org.com.thang.processor.common.FCProcessor;

public class QueryUtils {

	private static SQL sql=null;
	private static QueryRunner queryRunner=new QueryRunner(DBConfig.getDataSource());
	
	/**
	 * 查询指定类的所有数据。
	 * @param modelC
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<?> query(Class<?> modelC){
		List<?> data=null;
		try {
			data=queryRunner.query(SQLGener.getSelectSQL(modelC).select(false).toString(), new BeanListHandler(modelC,FCProcessor.getInstance()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 查询指定类和条件的所有数据。
	 * @param modelC
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<?> query(Class<?> modelC,ActionValues values){
		List<?> data=null;
		sql=SQLGener.getSelectSQL(modelC).select(false);
		sql.values(values);
		try {
			data=queryRunner.query(sql.toString(), new BeanListHandler(modelC,FCProcessor.getInstance()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 根据条件分页查询。
	 * @param pageNow
	 * @param pageSize
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> page(long pageNow,long pageSize,Class<?> modelC,ActionValues values){
		List<?> data=null;
		sql=SQLGener.getSelectSQL(modelC).select(false);
		sql.values(values);
		sql.value("page", new Page(pageNow,pageSize));
		try {
			data=queryRunner.query(sql.toString(), new BeanListHandler(modelC,FCProcessor.getInstance()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 根据条件分页查询。
	 * @param page
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> page(Class<?> modelC,Page page,ActionValues values){
		List<?> data=null;
		sql=SQLGener.getSelectSQL(modelC).select(false);
		sql.values(values);
		sql.value("page", page);
		try {
			data=queryRunner.query(sql.toString(), new BeanListHandler(modelC,FCProcessor.getInstance()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
