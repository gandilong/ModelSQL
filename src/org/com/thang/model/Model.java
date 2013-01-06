package org.com.thang.model;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.com.thang.db.sql.SQL;
import org.com.thang.executor.DBConfig;
import org.com.thang.gener.sql.SQLGener;
import org.com.thang.processor.common.FCProcessor;
import org.com.thang.utils.ModelUtils;
import org.com.thang.utils.SQLUtils;
@SuppressWarnings("unchecked")
public class Model<T> {

	private SQL sql=null;
	private Class<?> modelClass=null;
	protected static QueryRunner queryRunner=new QueryRunner(DBConfig.getDataSource());
	protected BeanHandler<T> beanHandler=new BeanHandler<T>((Class<T>)(this.getClass()),FCProcessor.getInstance());
	protected BeanListHandler<T> beanListHandler=new BeanListHandler<T>((Class<T>)this.getClass(),FCProcessor.getInstance());
	
	public T select(){
		T bean=null;
		try{
			if(null!=BeanUtils.getProperty(this, "id")){
		        switch(ModelUtils.getFiledType(this.getClass(), "id")){
		            case 0: bean=this.select(String.valueOf(BeanUtils.getProperty(this, "id")));break;
		            case 1: bean=this.select(Long.valueOf(BeanUtils.getProperty(this, "id")));break;
		            case 2: bean=this.select(Long.valueOf(BeanUtils.getProperty(this, "id")));break;
		            default :break;
		        }
			}else{
				sql=SQLGener.getSelectSQL(this.getClass()).select(false);
				sql.values(ActionValues.copyFromObject(this));
				SQLUtils.process(false,sql.getModel(), sql);
				bean=queryRunner.query(sql.toString(), beanHandler);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 根据ID查询一条记录。
	 * @param id
	 * @return
	 */
	public T select(long id){
		T bean=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(id);
		try{
			bean=queryRunner.query(sql.toString(), beanHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 根据ID查询一条记录。
	 * @param id
	 * @return
	 */
	public T select(String id){
		T bean=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(id);
		try{
			bean=queryRunner.query(sql.toString(), beanHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * 查询对象条件查询所有数据。
	 * @return
	 */
	public List<T> list(){
		List<T> data=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(false);
		sql.values(ActionValues.copyFromObject(this));
		try{
			data=queryRunner.query(sql.toString(), beanListHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 指定要查询的列，用属性名字符串形式做参数。
	 * @param selector
	 * @return
	 */
	public List<T> list(String ...selector){
		List<T> data=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(false,selector);
		sql.values(ActionValues.copyFromObject(this));
		try{
			data=queryRunner.query(sql.toString(), beanListHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 以分页的形式询数据。
	 * @param pageNow
	 * @param pageSize
	 * @return
	 */
	public List<T> page(long pageNow,long pageSize){
		List<T> data=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(false);
		sql.values(ActionValues.copyFromObject(this));
		sql.value("page", new Page(pageNow,pageSize));
		try{
			data=queryRunner.query(sql.toString(), beanListHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 以分页的形式询数据。
	 * @param page
	 * @return
	 */
	public List<T> page(Page page){
		List<T> data=null;
		modelClass=this.getClass();
		sql=SQLGener.getSelectSQL(modelClass).select(false);
		sql.values(ActionValues.copyFromObject(this));
		sql.value("page", page);
		try{
			data=queryRunner.query(sql.toString(), beanListHandler);
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
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
	
	@Override
	public String toString() {
		try{
		    if(null!=this){
			    Field[] fields=this.getClass().getDeclaredFields();
			    for(Field field:fields){
				    if(field.isAnnotationPresent(org.com.thang.model.mate.Column.class)){
					    if(field.getAnnotation(org.com.thang.model.mate.Column.class).primaryKey()){
						    return BeanUtils.getProperty(this, field.getName());
					    }
				    }
			    }
			    return BeanUtils.getProperty(this, "id");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
