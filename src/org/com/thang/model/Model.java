package org.com.thang.model;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.com.thang.db.sql.SQL;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SQLGener;
import org.com.thang.processor.common.FCProcessor;
import org.com.thang.utils.ModelUtils;

public class Model<T> {

	private SQL sql=null;
	private Class<?> modelClass=null;
	
	@SuppressWarnings("unchecked")
	public T select(long id){
		T bean=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(id);
		try{
			bean=new QueryRunner().query(DBConfig.getConnection(),sql.toString(), new BeanHandler<T>((Class<T>)modelClass));
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	public T select(String id){
		T bean=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(id);
		try{
			bean=new QueryRunner().query(DBConfig.getConnection(),sql.toString(), new BeanHandler<T>((Class<T>)modelClass,FCProcessor.getInstance()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	public List<T> select(){
		return null;
	}
	
	public List<T> select(String ...selector){
		return null;
	}
	
	public boolean delete(){
		modelClass=this.getClass();
		try{
		    Field field=ModelUtils.getField(this.getClass(),"id");
		    int type=ModelUtils.getFiledType(this.getClass(), "id");
		    switch(type){
		        case 0: SQLGener.getDeleteSQL(modelClass).delete(String.valueOf(BeanUtils.getProperty(this, "id")));break;
		        case 1: SQLGener.getDeleteSQL(modelClass).delete(Long.valueOf(BeanUtils.getProperty(this, "id")));break;
		        default :break;
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return false;
	}
	
	public boolean insert(){
	    return false;	
	}
	
	public boolean update(){
		return false;
	}
	
	
}
