package org.com.thang.utils;


import org.com.thang.db.sql.SQL;
import org.com.thang.model.ActionValues;
import org.com.thang.processor.SQLProcessor;
import org.mvel2.templates.TemplateRuntime;

public class SQLUtils implements SQLProcessor{

	public static boolean endWith(SQL sql){
		if(sql.toString().endsWith("and")||sql.toString().endsWith("AND")||sql.toString().endsWith("or")||sql.toString().endsWith("OR")||sql.toString().endsWith("where")||sql.toString().endsWith("WHERE")){
			return true;
		}
		return false;
	}

	public String process(Class<?> model, StringBuffer sql, ActionValues values) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
