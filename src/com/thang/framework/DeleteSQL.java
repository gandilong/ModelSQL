package com.thang.framework;

import com.thang.model.MField;
import com.thang.model.SQLModel;

public class DeleteSQL {

	private static StringBuffer sber=new StringBuffer();
	
	public static String genDeleteSQL(Class<?> cls,String id){
		String result=null;
		SQLModel model=new SQLModel(cls,id);
		
		sber.append("DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" WHERE ID='");
		sber.append(id);
		sber.append("'");
		
		result=sber.toString();
		clear();
		return result;
	}
	
    public static String genDeleteSQL(Class<?> cls,long id){
    	String result=null;
		SQLModel model=new SQLModel(cls,id);
		
		sber.append("DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" WHERE ID=");
		sber.append(id);
		
		return result;
	}
    
    public static String genDeleteSQL(SQLModel model){
    	clear();
    	sber.append("DELETE FROM ");
		sber.append(model.getTableName());
		sber.append(" where ");
		sber.append(model.getCondition());
		System.out.println(sber);
    	return sber.toString();
    }
    
    public static String genDeleteSQL(Object obj){
    	clear();
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
		return sber.toString();
	}
	
	private static void clear() {
		if(sber.length()>0){
            sber.delete(0, sber.length());
		}
	}
	
}
