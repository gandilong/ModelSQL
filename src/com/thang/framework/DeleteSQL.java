package com.thang.framework;

import com.thang.model.MField;
import com.thang.model.SQLModel;

public class DeleteSQL {

	private static StringBuffer sber=new StringBuffer();
	
	public static String GenDeleteSQL(Class<?> cls,String id){
		String result=null;
		SQLModel model=new SQLModel(cls,id);
		
		sber.append("DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" WHERE ID=");
		sber.append(id);
		
		
		result=sber.toString();
		clear();
		return result;
	}
	
    public static String GenDeleteSQL(Class<?> cls,long id){
    	String result=null;
		SQLModel model=new SQLModel(cls,id);
		
		sber.append("DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" WHERE ID='");
		sber.append(id);
		sber.append("'");
		
		return result;
	}
    
    public static String GenDeleteSQL(Object obj){
    	String result=null;
		SQLModel model=new SQLModel(obj);
		
		sber.append(" DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" WHERE ");
		sber.append(model.getPrimaryFieldName());
		sber.append("=");
		MField ID=model.getMField(model.getPrimaryFieldName());
		if(0==ID.getFieldType()){
			sber.append("'");
			sber.append(ID.getFieldValue()==null?model.getPrimaryFieldValue():ID.getFieldValue());
			sber.append("'");
		}else if(1==ID.getFieldType()){
			sber.append(ID.getFieldValue()==null?model.getPrimaryFieldValue():ID.getFieldValue());
		}
		result=sber.toString();
		clear();
		return result;
	}
	
	private static void clear() {
        sber.delete(0, sber.length());		
	}
	
}
