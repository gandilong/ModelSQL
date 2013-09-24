package com.thang.framework;

import com.thang.exception.NullFieldException;
import com.thang.model.MField;
import com.thang.model.SQLModel;
import com.thang.utils.lang.StringUtils;

public class UpdateSQL {

	private static StringBuffer sber=new StringBuffer();
	
	public static String genUpdateSQL(SQLModel model){
		String result=null;
		String[] columnNames=model.getColumnNames();
		if(null!=model){
			sber.append("UPDATE ");
			sber.append(model.getTableName());
			sber.append(" SET ");
			MField field=null;
			for(String fieldName:columnNames){
				if("id".equalsIgnoreCase(fieldName)){
					continue;
				}
				field=model.getMField(fieldName);
				if(null==field){
					throw new NullFieldException("得到的字段为空！ ");
				}
				
				if(0==field.getFieldType()&&StringUtils.isNotEmpty(field.getFieldValue())&&!"null".equalsIgnoreCase(field.getFieldValue())){
					sber.append(field.getColumnName());
					sber.append("=");
					sber.append("'");
					sber.append(field.getFieldValue());
					sber.append("'");
					sber.append(",");
				}else if(1==field.getFieldType()&&StringUtils.isNotEmpty(field.getFieldValue())&&!"null".equalsIgnoreCase(field.getFieldValue())){
					sber.append(field.getColumnName());
					sber.append("=");
					sber.append(field.getFieldValue());
					sber.append(",");
				}else{}
			}
			if(null!=sber&&sber.length()>0&&sber.toString().endsWith(",")){
				sber=sber.delete(sber.length()-1,sber.length());
			}
			
			sber.append(" WHERE ");
			sber.append(model.getPrimaryFieldName());
			sber.append("=");
			MField ID=model.getMField(model.getPrimaryFieldName());
			if((null==ID||StringUtils.isEmpty(ID.getFieldValue()))&&(null==model.getPrimaryFieldValue()||"".equals(model.getPrimaryFieldValue()))){
				throw new NullFieldException("ID字段值为空！");
			}
			if(0==ID.getFieldType()){
				sber.append("'");
				sber.append(ID.getFieldValue()==null?model.getPrimaryFieldValue():ID.getFieldValue());
				sber.append("'");
			}else if(1==ID.getFieldType()){
				sber.append(ID.getFieldValue()==null?model.getPrimaryFieldValue():ID.getFieldValue());
			}
		}
		result=sber.toString();
		clear();
		return result;
	}
	
	private static void clear() {
        sber.delete(0, sber.length());		
	}
}
