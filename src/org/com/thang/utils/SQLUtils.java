package org.com.thang.utils;


import java.lang.reflect.Field;

import org.com.thang.db.sql.SelectSQL;
import org.com.thang.model.ActionValues;
import org.com.thang.model.Page;
import org.com.thang.model.mate.Order;
import org.com.thang.processor.SQLProcessor;

public class SQLUtils implements SQLProcessor{

	public String process(Class<?> model, StringBuffer sql, ActionValues values) {
		
		return null;
	}
	
	public static void pageProcessOracle(SelectSQL sql,Page page){
		
	}
	
    public static void pageProcessMySQL(SelectSQL sql,Page page){
    	sql.append(" limit ");
		sql.append((page.getPageNow()*page.getPageSize()+1));
		sql.append(",");
		sql.append(page.getPageSize());
		sql.append(" ");
		sql.append(" order by ");
		sql.append(page.getOrderBy());
		sql.append(" ");
		sql.append(page.getOrder());
	}
    
    public static void orderProcess(SelectSQL sql,Class<?> modelClass){
    	Field[] fields=modelClass.getDeclaredFields();
    	for(Field field:fields){
    		if(field.isAnnotationPresent(org.com.thang.model.mate.Order.class)){
    			Order order=field.getAnnotation(org.com.thang.model.mate.Order.class);
    			sql.append(" order by ");
    			sql.append(field.getName());
    			sql.append(" ");
    			sql.append(order.order());
    		}
    	}
    }
    
    public static String formatSQL(StringBuffer sql){
    	sql.insert(sql.indexOf("select")+7, "\n\t");
    	sql.insert(sql.indexOf("from")-1, "\n");
    	if(-1!=sql.indexOf("where")){
    	    sql.insert(sql.indexOf("where")-1, "\n");
    	}
    	return sql.toString().replace(",", ",\n\t");
    }
	
}
