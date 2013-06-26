package com.thang.executor;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.thang.model.Condition;
import com.thang.model.Model;
import com.thang.model.sql.SQLGener;
import com.thang.processor.ModelProcessor;
import com.thang.utils.db.ConnectionUtils;
import com.thang.utils.reflect.ModelUtils;

public class DBExecutor {

	private static DataSource dataSource=ConnectionUtils.getDataSource();
	private static QueryRunner queryRunner=new QueryRunner(dataSource);
	
	public DBExecutor(){}

	
	public long num(Class<?> model){
		long total=0;
		try{
		    total=queryRunner.query("select count(*) from "+ModelUtils.getTableName(model),new ScalarHandler<Long>());
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 */
	public void execute(String sql){
		try{
		    queryRunner.update(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询一条记录，返回用Map实例封装。
	 * @param sql
	 * @return
	 */
	public Map<String,Object> queryForMap(String sql){
		try{
		   return queryRunner.query(sql, new MapHandler(ModelProcessor.getInstance()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询多条记录，返回用List<Map>实例封装。
	 * @param sql
	 * @return
	 */
	public List<Map<String,Object>> queryForMaps(String sql){
		try{
		   return queryRunner.query(sql, new MapListHandler(ModelProcessor.getInstance()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 增加一条数据记录，如果ID为空，则根据ID类型自增ID。
	 * @param pojo
	 */
	public void insert(Object pojo){
		try{
		    queryRunner.update(SQLGener.InsertSQL(new Model(pojo)));
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
		    queryRunner.update(SQLGener.InsertSQL(new Model(pojo)));
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
				queryRunner.update(SQLGener.DeleteSQL(new Model(model,id)));
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
				queryRunner.update(SQLGener.DeleteSQL(new Model(model,id)));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public  <T>List<T> list(Class<T> cls){//default is id desc
		 List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectSQL(new Model(cls)),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	public <T>List<T> listDesc(Class<T> cls,String columns){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectDescSQL(new Model(cls),columns),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	public <T>List<T> listAsc(Class<T> cls,String columns){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectAscSQL(new Model(cls),columns),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	/**
	 * 根据condition对象进行分布查询。
	 * @param page
	 * @return
	 */
	public <T>List<T> list(Class<T> cls,Condition condition){
	    List<T> result=null;
	    try{
	    	result=(List<T>)queryRunner.query(SQLGener.SelectConditionSQL(new Model(cls),condition),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
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
			result=queryRunner.query(SQLGener.SimpleSelectSQL(new Model(cls,id)), new BeanHandler<T>(cls,ModelProcessor.getInstance()));
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
		    	queryRunner.update(SQLGener.UpdateSQL(new Model(pojo)));
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
