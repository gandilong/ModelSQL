package com.thang.executor;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.thang.db.DBPools;
import com.thang.model.Condition;
import com.thang.model.SQLModel;
import com.thang.model.sql.SQLGener;
import com.thang.utils.reflect.ModelUtils;

public class DBExecutor {

	private static DataSource dataSource=DBPools.getDataSource();
	private static QueryRunner queryRunner=new QueryRunner(dataSource);
	
	public DBExecutor(){}
	
/*	public DBExecutor(DataSource dataSource){
		dbm=new DBModel();
		this.dataSource=dataSource;
		this.queryRunner=new QueryRunner(dataSource);
	}*/
	
	/**
	 * 增加一条数据记录，如果ID为空，则根据ID类型自增ID。
	 * @param pojo
	 */
	public void insert(Object pojo){
		try{
		    if(!ModelUtils.idValid(pojo)){//假如实体ID值有效，则不自增，不然自增一个ID值。
		    	ModelUtils.installID(pojo);
		    }
		    queryRunner.update(SQLGener.InsertSQL(new SQLModel(pojo.getClass(),pojo)));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据整数ID删除数据。
	 * @param id
	 */
	public void delete(Class<?> model,long id){
		if(0!=id){
			try{
				queryRunner.update(SQLGener.DeleteSQL(new SQLModel(model,id)));
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			System.out.println("ID值无效！");
		}
	}
	
	/**
	 * 根据字符串ID删除数据。
	 * @param id
	 */
	public void delete(Class<?> model,String id){
		if(null!=id&&!"".equals(id)){
			try{
				queryRunner.update(SQLGener.DeleteSQL(new SQLModel(model,id)));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据condition对象进行分布查询。
	 * @param page
	 * @return
	 */
	public <T>List<T> list(Class<T> cls,Condition condition){
	    List<T> result=null;
	    try{
	    	result=(List<T>)queryRunner.query(SQLGener.SelectSQL(new SQLModel(cls,condition)),new BeanListHandler<T>(cls));
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据ID得到实体对象。
	 * @param id
	 * @return
	 */
	public <T>T get(Class<T> cls,long id){
		return get(cls,String.valueOf(id));
	}
	
	/**
	 * 根据ID得到实体对象。
	 * @param id
	 * @return
	 */
	public <T>T get(Class<T> cls,String id){
		T result=null;
		try{
			result=queryRunner.query(SQLGener.SimpleSelectSQL(new SQLModel(cls,id)), new BeanHandler<T>(cls));
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 默认只更新不为空的字段值。
	 * @param pojo
	 */
	public void update(Object pojo){
		try{
		    if(ModelUtils.idValid(pojo)){//实体ID值必须有效
		    	queryRunner.update(SQLGener.UpdateSQL(new SQLModel(pojo.getClass(),pojo)));
		    }else{
		    	System.out.println("无效ID！");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static void setDataSource(DataSource dataSource) {
		DBExecutor.dataSource = dataSource;
	}
	
}
