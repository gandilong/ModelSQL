package com.thang.executor;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.thang.model.SQLModel;
import com.thang.model.sql.SQLGener;
import com.thang.utils.reflect.ModelUtils;

public class SQLExecutor {

	private DataSource dataSource=null;
	private QueryRunner queryRunner=null;
	private String database=null;//数据库类型 mysql ,oracle,sqlserver
	
	
	/**
	 * 增加一条数据记录，如果ID为空，则根据ID类型自增ID。
	 * @param pojo
	 */
	public void insert(Object pojo){
		try{
		    queryRunner.update(SQLGener.InsertSQL(new SQLModel(pojo)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 增加一条数据记录，如果ID为空，则根据ID类型自增ID。
	 * @param pojo
	 */
	public void insertWidthID(Object pojo){
		try{
		    ModelUtils.installID(pojo);
		    queryRunner.update(SQLGener.InsertSQL(new SQLModel(pojo)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void InsertOrUpdate(Object obj){
		if(ModelUtils.idValid(obj)){
        	update(obj);
       }else{
         	insert(obj);
       }
	}
	
	/**
	 * 默认只更新不为空的字段值。
	 * @param pojo
	 */
	public void update(Object pojo){
		try{
		    if(ModelUtils.idValid(pojo)){//实体ID值必须有效
		    	queryRunner.update(SQLGener.UpdateSQL(new SQLModel(pojo)));
		    }else{
		    	System.out.println("无效ID！");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
