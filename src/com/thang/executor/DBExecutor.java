package com.thang.executor;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.thang.model.Condition;
import com.thang.model.Model;
import com.thang.model.sql.SQLGener;
import com.thang.processor.ModelProcessor;
import com.thang.utils.reflect.ModelUtils;
import java.math.BigDecimal;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import java.util.Properties;

public class DBExecutor {

	private  DataSource dataSource=null;//ConnectionUtils.getDataSource();
	private  QueryRunner queryRunner=null;//new QueryRunner(dataSource);
	private  String database=null;//数据库类型 mysql ,oracle,sqlserver
        
        public DBExecutor(){}
        
        /**
         * @param ds 数据源
         * @param database 数据库类型，mysql,oracle,sqlserver
         */
	public DBExecutor(DataSource ds,String database){
             this.dataSource=ds;
             this.queryRunner=new QueryRunner(ds);
             setDatabase(database);
        }
        
        public DBExecutor(String driverClassName,String url,String username,String password,String database){
            try{
                DbUtils.loadDriver(driverClassName);
                Properties pros=new Properties();
                pros.setProperty("driverClassName",driverClassName );
                pros.setProperty("url",url );
                pros.setProperty("username",username);
                pros.setProperty("password",password );
            
                this.dataSource=BasicDataSourceFactory.createDataSource(pros);
                this.queryRunner=new QueryRunner(this.dataSource);
                setDatabase(database);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

	/**
         * 查某张表的数据量
         * @param model
         * @return 
         */
	public long count(Class<?> model){
		Object total=null;
		try{
		    total=queryRunner.query("select count(*) from "+ModelUtils.getTableName(model),new ScalarHandler<Object>());
                    if(total.getClass()==BigDecimal.class){
                        return ((BigDecimal)total).longValue();
                    }else if(total.getClass()==Long.class){
                        return (Long)total;
                    }
		}catch(Exception e){
		    e.printStackTrace();
		}
		return 0;
	}
        
        /**
         * 查某张表的数据量
         * @param model
         * @return 
         */    
        public Long count(Class<?> model,Condition cnd){
		Long total=null;
                cnd.setDatabase(this.database);
		try{
		    Object obj=queryRunner.query(SQLGener.CountSQL(new Model(model),cnd),new ScalarHandler<Object>());
                    if(obj.getClass()==BigDecimal.class){
                        return ((BigDecimal)obj).longValue();
                    }else if(obj.getClass()==Long.class){
                        return (Long)obj;
                    }
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
	 * 得到一个列对象
	 * @param cls
	 * @param fieldName
	 * @param id
	 * @return
	 */
	public Object column(Class<?> cls,String fieldName,long id){
    	try{
    		Model model=new Model(cls);
    		return queryRunner.query("select "+model.getMField(fieldName).getColumnName()+" from "+model.getTableName()+" WHERE "+model.getPrimaryFieldName()+"="+id, new ColumnListHandler<Object>(fieldName));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
	
	/**
	 * 得到一个列对象
	 * @param cls
	 * @param fieldName
	 * @param id
	 * @return
	 */
	public Object column(Class<?> cls,String fieldName,String id){
	    	try{
	    		Model model=new Model(cls);
	    		return queryRunner.query("select "+model.getMField(fieldName).getColumnName()+" from "+model.getTableName()+" WHERE "+model.getPrimaryFieldName()+"='"+id+"'", new ColumnListHandler<Object>(fieldName));
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	 }
	
	/**
	 * 得到列对象集合
	 */
	public List<Object> columns(Class<?> cls,String fieldName){
	    	try{
	    		Model model=new Model(cls);
	    		return queryRunner.query("select "+model.getMField(fieldName).getColumnName()+" from "+model.getTableName(), new ColumnListHandler<Object>(fieldName));
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	 }
        
        /**
	 * 得到列对象集合
	 */
	public List<Object> columns(Class<?> cls,String fieldName,Condition condition){
	    	try{
                    condition.setDatabase(this.database);
                    if(0==condition.getPage().getTotal()){
                        condition.getPage().setTotal(count(cls,condition).longValue());
                    }
	    	    return queryRunner.query(SQLGener.SelectConditionSQL(new Model(cls),condition), new ColumnListHandler<Object>(fieldName));
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	 }
	
	
	public Map<String,Object> map(Class<?> cls,long id){
		try{
			  return queryRunner.query(SQLGener.SimpleSelectSQL(new Model(cls,id)), new MapHandler(ModelProcessor.getInstance()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public Map<String,Object> map(Class<?> cls,String id){
		try{
			  return queryRunner.query(SQLGener.SimpleSelectSQL(new Model(cls,id)), new MapHandler(ModelProcessor.getInstance()));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,Object>> maps(Class<?> cls){//default is id desc
		 try{
		   	return queryRunner.query(SQLGener.SelectSQL(new Model(cls)),new MapListHandler(ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return null;
	}
	
	public List<Map<String,Object>> mapsDesc(Class<?> cls,String fieldNames){
		 try{
		   	return queryRunner.query(SQLGener.SelectDescSQL(new Model(cls),fieldNames),new MapListHandler(ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return null;
	}
	
	public List<Map<String,Object>> mapsAsc(Class<?> cls,String fieldNames){
		 try{
		   	return queryRunner.query(SQLGener.SelectAscSQL(new Model(cls),fieldNames),new MapListHandler(ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return null;
	}
	
	/**
	 * 根据condition对象进行分布查询。
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> maps(Class<?> cls,Condition condition){
	    try{
                condition.setDatabase(this.database);
                 if(0==condition.getPage().getTotal()){
                        condition.getPage().setTotal(count(cls,condition).longValue());
                    }
	    	return queryRunner.query(SQLGener.SelectConditionSQL(new Model(cls),condition),new MapListHandler(ModelProcessor.getInstance()));
	    }catch(Exception e){
			e.printStackTrace();
	    }
		return null;
	}
	 
	/**
	 * 查询一条记录，返回用Map实例封装。
	 * @param sql
	 * @return
	 */
	public Map<String,Object> query(String sql){
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
	public List<Map<String,Object>> maps(String sql){
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
     *判断ID字段是否有值，来进行操作
     */
	public void insertOrUpdate(Object obj){
             if(ModelUtils.idValid(obj)){
        	update(obj);
             }else{
        	insert(obj);
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
	
	public <T>List<T> listDesc(Class<T> cls,String fieldNames){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectDescSQL(new Model(cls),fieldNames),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	public <T>List<T> listAsc(Class<T> cls,String fieldNames){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectAscSQL(new Model(cls),fieldNames),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
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
                condition.setDatabase(this.database);
                 if(null!=condition&&null!=condition.getPage()&&0==condition.getPage().getTotal()){
                        condition.getPage().setTotal(this.count(cls, condition));
                    }
	    	result=(List<T>)queryRunner.query(SQLGener.SelectConditionSQL(new Model(cls),condition),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
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
	public <T>List<T> query(String sql,Class<T> cls){
	    List<T> result=null;
	    try{
	    	result=(List<T>)queryRunner.query(sql,new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
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

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

    public  String getDatabase() {
        return database;
    }

    public  void setDatabase(String database) {
        this.database = database;
    }
        
       
	
}
