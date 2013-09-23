package com.thang.executor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.thang.model.Condition;
import com.thang.model.SQLModel;
import com.thang.model.sql.SQLGener;
import com.thang.pojo.User;
import com.thang.processor.ModelProcessor;
import com.thang.utils.reflect.ModelUtils;

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
        
	    /**
	     * 自己配置数据源
	     * @param driverClassName
	     * @param url
	     * @param username
	     * @param password
	     * @param database
	     */
         public DBExecutor(String driverClassName,String url,String username,String password,String database){
            try{
                DbUtils.loadDriver(driverClassName);
                Properties pros=new Properties();
                pros.setProperty("driverClassName",driverClassName);
                pros.setProperty("url",url);
                pros.setProperty("username",username);
                pros.setProperty("password",password);
            
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
		    total=queryRunner.query("SELECT COUNT(*) FROM "+ModelUtils.getTableName(model),new ScalarHandler<Object>());
                    if(total.getClass()==BigDecimal.class){
                        return ((BigDecimal)total).longValue();
                    }else{
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
        public Long count(String sql){
		       Long total=null;
		       try{
		           Object obj=queryRunner.query(sql,new ScalarHandler<Object>());
                    if(obj.getClass()==BigDecimal.class){
                        return ((BigDecimal)obj).longValue();
                    }else{
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
	public void update(String sql){
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
	public String column(Class<?> cls,String fieldName,long id){
    	return column(cls,fieldName,String.valueOf(id));
    }
	
	/**
	 * 得到一个列对象
	 * @param cls
	 * @param fieldName
	 * @param id
	 * @return
	 */
	public String column(Class<?> cls,String fieldName,String id){
	    	try{
	    		SQLModel model=new SQLModel(cls);
	    		String column=model.getMField(fieldName).getColumnName();
	    		List<String> result=queryRunner.query("SELECT "+column+" FROM "+model.getTableName()+" WHERE "+model.getPrimaryFieldName()+"='"+id+"'", new ColumnListHandler<String>(column));
	    		if(null!=result&&result.size()>0){
	    			return result.get(0);
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	 }
	
	/**
	 * 得到列对象集合
	 */
	public List<String> columns(Class<?> cls,String fieldName){
	    	try{
	    		SQLModel model=new SQLModel(cls);
	    		String column=model.getMField(fieldName).getColumnName();
	    		return queryRunner.query("SELECT "+column+" FROM "+model.getTableName(), new ColumnListHandler<String>(column));
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	 }
	
    /**
	 * 得到列对象集合
	 */
	public List<String> columns(String sql){
	    	try{
	    	    return queryRunner.query(sql, new ColumnListHandler<String>());
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
	
	public <T>List<T> list(Class<T> cls){//default is id desc
		 List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectSQL(new SQLModel(cls)),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	public <T>List<T> listDesc(Class<T> cls,String fieldNames){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectDescSQL(new SQLModel(cls),fieldNames),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
		 }catch(Exception e){
		 	e.printStackTrace();
		 }
		 return result;
	}
	
	public <T>List<T> listAsc(Class<T> cls,String fieldNames){
		List<T> result=null;
		 try{
		   	result=(List<T>)queryRunner.query(SQLGener.SelectAscSQL(new SQLModel(cls),fieldNames),new BeanListHandler<T>(cls,ModelProcessor.getInstance()));
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
	public <T>List<T> list(Condition condition){
	    List<T> result=null;
	    try{
	    	String sql=SQLGener.SelectConditionSQL(condition);
                 if(null!=condition&&null!=condition.getPage()&&0==condition.getPage().getTotal()){
                        condition.getPage().setTotal(count("SELECT COUNT(*) FROM ("+sql+") c"));
                    }
	    	result=(List<T>)queryRunner.query(sql,new BeanListHandler<T>((Class<T>)condition.getModelCls(),ModelProcessor.getInstance()));
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
			result=queryRunner.query(SQLGener.SimpleSelectSQL(new SQLModel(cls,id)), new BeanHandler<T>(cls,ModelProcessor.getInstance()));
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
		    	queryRunner.update(SQLGener.UpdateSQL(new SQLModel(pojo)));
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
        Condition.setDatabase(database);
    }
        
       
	
}
