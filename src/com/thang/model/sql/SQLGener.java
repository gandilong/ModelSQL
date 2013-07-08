package com.thang.model.sql;

import com.thang.exception.NullFieldException;
import com.thang.model.Condition;
import com.thang.model.MField;
import com.thang.model.Model;
import com.thang.utils.db.ConnectionUtils;
import com.thang.utils.lang.StringUtils;

public class SQLGener {
	
	public static String InsertSQL(Model model){
		return StringUtils.println(GenerInsertSQL(model));
	}
	
	public static String DeleteSQL(Model model){
		return StringUtils.println(GenerDeleteSQL(model));
	}
	
	public static String SelectSQL(Model model){
		return StringUtils.println(GenerSelectSQL(model));
	}
	
	public static String SelectDescSQL(Model model,String fieldNames){
		return StringUtils.println(GenerSelectDescSQL(model,fieldNames));
	}
	
	public static String SelectAscSQL(Model model,String fieldNames){
		return StringUtils.println(GenerSelectAscSQL(model,fieldNames));
	}
	
	public static String SelectConditionSQL(Model model,Condition condition){
		return StringUtils.println(GenerSelectConditioncSQL(model,condition));
	}
	
	public static String SelectSQL(Model model,Condition condition){
		return StringUtils.println(GenerSelectSQL(model,condition));
	}
	
	public static String SimpleSelectSQL(Model model){
		return StringUtils.println(GenerSimpleSelectSQL(model));
	}
	
	public static String UpdateSQL(Model model){
		return StringUtils.println(GenerUpdateSQL(model));
	}
	
	/**
	 * 专门生成insert语句
	 * @param model
	 * @return
	 */
	public static String GenerInsertSQL(Model model){
		StringBuilder sber=new StringBuilder();
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
	
	/**
	 * 生成删除语句
	 * @param model
	 * @return
	 */
	public static String GenerDeleteSQL(Model model){
		StringBuilder sber=new StringBuilder();
		if(null!=model){
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
		}
		return sber.toString();
	}
	
	/**
	 * 生成更新语句
	 * @param model
	 * @return
	 */
	public static String GenerUpdateSQL(Model model){
		StringBuilder sber=new StringBuilder();
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
		return sber.toString();
	}
	
	public static String GenerSimpleSelectSQL(Model model){
		StringBuilder sber=new StringBuilder();
		String[] columnNames=model.getColumnNames();
		
		if(null!=model){
			sber.append(" SELECT ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" FROM ");
			sber.append(model.getTableName());
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
		return sber.toString();
	}
	
	public static String GenerSelectSQL(Model model){
		StringBuilder sber=new StringBuilder();
        String[] columnNames=model.getColumnNames();
		
		if(null!=model){
			sber.append(" SELECT ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" FROM ");
			sber.append(model.getTableName());
			sber.append(" ORDER BY ID DESC ");
		}
		return sber.toString();
	}
	
	public static String GenerSelectDescSQL(Model model,String fieldNames){//多个字段之间要加逗号
		StringBuilder sber=new StringBuilder();
        String[] columnNames=model.getColumnNames();
        StringBuilder orderby=new StringBuilder();
		if(fieldNames.contains(",")){
			String[] source=fieldNames.split(",");
			for(String fieldName:source){
				orderby.append(model.getMField(fieldName).getColumnName());
				orderby.append(",");
			}
			orderby.delete(orderby.length()-1, orderby.length());
		}else{
			orderby.append(model.getMField(fieldNames).getColumnName());
		}
		if(null!=model){
			sber.append(" SELECT ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" FROM ");
			sber.append(model.getTableName());
			sber.append(" ORDER BY "+orderby.toString()+" DESC ");
		}
		return sber.toString();
	}
	
	public static String GenerSelectAscSQL(Model model,String fieldNames){//多个字段之间要加逗号
		StringBuilder sber=new StringBuilder();
        String[] columnNames=model.getColumnNames();
        StringBuilder orderby=new StringBuilder();
		if(fieldNames.contains(",")){
			String[] source=fieldNames.split(",");
			for(String fieldName:source){
				orderby.append(model.getMField(fieldName).getColumnName());
				orderby.append(",");
			}
			orderby.delete(orderby.length()-1, orderby.length());
		}else{
			orderby.append(model.getMField(fieldNames).getColumnName());
		}
		if(null!=model){
			sber.append(" SELECT ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" FROM ");
			sber.append(model.getTableName());
			
			sber.append(" ORDER BY "+orderby.toString()+" ASC ");
		}
		return sber.toString();
	}
	
	public static String GenerSelectConditioncSQL(Model model,Condition cd){//多个字段之间要加逗号
		StringBuilder sber=new StringBuilder();
        String[] columnNames=model.getColumnNames();
		if(null!=model&&"oracle".equalsIgnoreCase(ConnectionUtils.getDatabase())){
			sber.append("SELECT \n ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" \nFROM \n");
			sber.append(" \nSELECT t.*,ROWNUM RN");
			sber.append(" \nFROM ( ");
		}
		if(null!=model){
			sber.append("SELECT \n ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append(" \nFROM \n ");
			sber.append(model.getTableName());
			sber.append(" t ");
			sber.append(" \nWHERE \n");
			sber.append(cd.getCdtion().toString());
		}
		if(null!=model&&"oracle".equalsIgnoreCase(ConnectionUtils.getDatabase())){
			sber.append(") WHERE ROWNUM<=");
			sber.append((cd.getPage().getPageNow()+1)*cd.getPage().getPageSize());
			sber.append(" \n) RN >=");
			sber.append(cd.getPage().getPageNow()*cd.getPage().getPageSize());
		}
		return sber.toString();
	}
	
	
	public static String GenerSelectSQL(Model model,Condition condition){
		StringBuilder sber=new StringBuilder();
		
		return sber.toString();
	}
	
}
