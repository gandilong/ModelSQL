package org.com.thang.processor.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.com.thang.model.ActionValues;
import org.com.thang.processor.FieldColumnProcessor;
import org.com.thang.utils.StringUtils;

import com.mysql.jdbc.Blob;
import com.mysql.jdbc.Clob;

/**
 * 从field到column的字段处理。
 * @author Gandilong at 2012-12-29下午01:28:31
 *
 */
public class FCProcessor implements FieldColumnProcessor {

	private static final FieldColumnProcessor instance=new FCProcessor();
	
	public static FieldColumnProcessor getInstance(){
		return FCProcessor.instance;
	}
	
	@Override
	public String filedToColumn(Class<?> model,String field) {
		if(null!=model){
			try{
			    Field f=model.getDeclaredField(field);
			    if(f.isAnnotationPresent(org.com.thang.model.mate.Field.class)){
			    	return f.getAnnotation(org.com.thang.model.mate.Field.class).column();
			    }else{
			    	return StringUtils.addUnderline(field);
			    }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String columnToField(Class<?> model,String column) {
		Field[] fields=model.getDeclaredFields();
		for(Field f:fields){
			if(f.isAnnotationPresent(org.com.thang.model.mate.Field.class)){
		    	if(column.equalsIgnoreCase(f.getAnnotation(org.com.thang.model.mate.Field.class).column())){
		    		return f.getName();
		    	}
		    }
		}
		return StringUtils.dropUnderline(column);
	}
	
	@Override
	public Object[] toArray(ResultSet rs) throws SQLException {
		ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; i++) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
	}

	@Override
	public <T> T toBean(ResultSet rs, Class<T> modelClass) throws SQLException {
		T bean=null;
		try{
			Class<?> type=null;
            bean=modelClass.newInstance();
            Field[] fields=modelClass.getDeclaredFields();
            BufferedReader reader=null;
            StringBuilder strContainer=null;
            
            byte[] b=null;
            BufferedInputStream input=null;
            for(Field field:fields){
            	type=field.getType();
            	if(String.class.equals(type)||char.class.equals(type)){
            		BeanUtils.setProperty(bean, field.getName(), rs.getString(filedToColumn(modelClass,field.getName())));
            	}else if(int.class.equals(field)||Integer.class.equals(field)){
            		BeanUtils.setProperty(bean, field.getName(), rs.getInt(filedToColumn(modelClass,field.getName())));
            	}else if(long.class.equals(field)||Long.class.equals(field)){
            		BeanUtils.setProperty(bean, field.getName(), rs.getLong(filedToColumn(modelClass,field.getName())));
            	}else if(double.class.equals(field)){
            		BeanUtils.setProperty(bean, field.getName(), rs.getDouble(filedToColumn(modelClass,field.getName())));
            	}else if(java.sql.Date.class.equals(field)){
            		BeanUtils.setProperty(bean, field.getName(), rs.getDate(filedToColumn(modelClass,field.getName())));
            	}else if(Clob.class.equals(field)){
            		reader=new BufferedReader(rs.getClob(filedToColumn(modelClass,field.getName())).getCharacterStream());
            		strContainer=new StringBuilder();
            		while(reader.ready()){
            			strContainer.append(reader.readLine());
            		}
            		BeanUtils.setProperty(bean, field.getName(), strContainer.toString());
            	}else if(Blob.class.equals(field)){
            		b=new byte[1024*3];
            		strContainer=new StringBuilder();
            		input=new BufferedInputStream(rs.getBlob(filedToColumn(modelClass,field.getName())).getBinaryStream());
            		while(-1!=input.read(b)){
            			strContainer.append(new String(b,"UTF-8"));
            		}
            		BeanUtils.setProperty(bean, field.getName(), strContainer.toString());
            	}
            	
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return bean;
	}

	@Override
	public <T> List<T> toBeanList(ResultSet rs, Class<T> modelClass)throws SQLException {
		List<T> data=new ArrayList<T>();
		while(rs.next()){
			data.add(toBean(rs,modelClass));
		}
		return data;
	}

	@Override
	public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		ActionValues values=new ActionValues();
		ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; i++) {
            values.put(rsmd.getColumnName(i), rs.getObject(i));
        }

		return values;
	}

}
