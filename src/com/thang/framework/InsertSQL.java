package com.thang.framework;

import com.thang.exception.NullFieldException;
import com.thang.framework.core.SQL;
import com.thang.model.MField;
import com.thang.model.SQLModel;
import com.thang.utils.lang.StringUtils;

public class InsertSQL implements SQL {

	private static StringBuffer sber=new StringBuffer();
	
	public static String genInsertSQL(SQLModel model){
		clear();
		String[] columnNames=model.getColumnNames();
		if(null!=model){
			sber.append("INSERT INTO ");
			sber.append(model.getTableName());
			sber.append("(");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(") VALUES(");
			MField field=null;
			int i=0;
			for(String fieldName:columnNames){
				if(0<i){
					sber.append(",");
				}
				field=model.getMField(fieldName);
				if(null==field){
					throw new NullFieldException("得到的字段为空！ ");
				}
				if(0==field.getFieldType()){
					sber.append("'");
					sber.append(field.getFieldValue());
					sber.append("'");
				}else if(1==field.getFieldType()){
					sber.append(field.getFieldValue());
				}else{}
				i++;
			}
			sber.append(")");
		}
		
		return sber.toString();
	}


	private static void clear() {
        sber.delete(0, sber.length());		
	}
	
}
