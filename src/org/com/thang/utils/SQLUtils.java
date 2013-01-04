package org.com.thang.utils;


import org.com.thang.db.sql.SelectSQL;
import org.com.thang.model.ActionValues;
import org.com.thang.model.Page;
import org.com.thang.processor.SQLProcessor;

public class SQLUtils implements SQLProcessor{

	public String process(Class<?> model, StringBuffer sql, ActionValues values) {
		
		return null;
	}
	
	public static void pageProcessOracle(SelectSQL sql,Page page){
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
	
    public static void pageProcessMySQL(SelectSQL sql,Page page){
		
	}
	
}
