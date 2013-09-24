package com.thang.framework;

import java.lang.reflect.Field;

import com.thang.model.SQLModel;
import com.thang.utils.lang.StringUtils;
import com.thang.utils.reflect.ModelUtils;

public class SelectSQL {

	private static StringBuffer sber=new StringBuffer();
	
	/**
	 * 生成简单的查询语句
	 * @param cls
	 * @return
	 */
	public static String genSelectSQL(Class<?> cls){
		clear();
		return genSelectSQL(cls,"id","desc");
	}
	
	/**
	 * 生成简单的查询语句
	 * @param cls
	 * @return
	 */
	public static String genSelectSQL(Class<?> cls,String orderBy,String order){
		clear();
		Field[] fields=cls.getDeclaredFields();
			
		if(null!=fields){
		    sber.append(" SELECT ");
				for(Field field:fields){
				    sber.append(StringUtils.addUnderline(field.getName()));
				    sber.append(",");
				}
			sber.delete(sber.length()-1,sber.length());
			sber.append(" FROM ");
			sber.append(ModelUtils.getTableName(cls));
			sber.append(" ORDER BY ");
			sber.append(StringUtils.addUnderline(orderBy));
			sber.append(" ");
			sber.append(order);
		}
		System.out.println(sber.toString());
		return sber.toString();
	}
	
	public static String genSelectSQL(SQLModel model){
		clear();
		sber.append(" SELECT ");
		sber.append(StringUtils.join(model.getColumnNames(),","));
	    sber.append(" FROM ");
	    sber.append(model.getTableName());
	    sber.append(" where ");
	    sber.append(model.getCondition());
	    System.out.println(sber);
		return sber.toString();
	}
	
	
	private static void clear() {
		if(sber.length()>0){
			sber.delete(0, sber.length());	
		}
	}
	
}
