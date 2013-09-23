package com.thang.model.sql;

import com.thang.exception.NullFieldException;
import com.thang.model.Condition;
import com.thang.model.MField;
import com.thang.model.SQLModel;
import com.thang.utils.lang.StringUtils;

/**
 * 核心sql生成方法
 * @author gandilong
 *
 */
public class SQLGener {
	
	
	
	/**
	 * 新增语句
	 * @param model
	 * @return
	 */
	public static String InsertSQL(SQLModel model){
		return StringUtils.println(GenerInsertSQL(model));
	}
	
	/**
	 * 删除语句
	 * @param model
	 * @return
	 */
	public static String DeleteSQL(SQLModel model){
		return StringUtils.println(GenerDeleteSQL(model));
	}
	
	/**
	 * 更新语句
	 * @param model
	 * @return
	 */
	public static String UpdateSQL(SQLModel model){
		return StringUtils.println(GenerUpdateSQL(model));
	}
	
	public static String SelectSQL(SQLModel model){
		return StringUtils.println(GenerSelectSQL(model));
	}
	
	public static String SelectDescSQL(SQLModel model,String fieldNames){
		return StringUtils.println(GenerSelectDescSQL(model,fieldNames));
	}
	
	public static String SelectAscSQL(SQLModel model,String fieldNames){
		return StringUtils.println(GenerSelectAscSQL(model,fieldNames));
	}
	
	public static String SelectConditionSQL(Condition condition){
		return StringUtils.println(GenerSelectConditioncSQL(condition.getModel(),condition));
	}
	
	public static String CountSQL(SQLModel model,Condition condition){
		return StringUtils.println(countSQL(model,condition));
	}
	
	public static String SimpleSelectSQL(SQLModel model){
		return StringUtils.println(GenerSimpleSelectSQL(model));
	}
	
	
	
	/**
	 * 专门生成insert语句
	 * @param model
	 * @return
	 */
	public static String GenerInsertSQL(SQLModel model){
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
	public static String GenerDeleteSQL(SQLModel model){
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
	public static String GenerUpdateSQL(SQLModel model){
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
	
	public static String GenerSimpleSelectSQL(SQLModel model){
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
	
	public static String GenerSelectSQL(SQLModel model){
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
	
	public static String GenerSelectDescSQL(SQLModel model,String fieldNames){//多个字段之间要加逗号
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
	
	public static String GenerSelectAscSQL(SQLModel model,String fieldNames){//多个字段之间要加逗号
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
	
	public static String GenerSelectConditioncSQL(SQLModel model,Condition cd){//多个字段之间要加逗号
		StringBuilder sber=new StringBuilder();
                String[] columnNames=model.getColumnNames();
                
		if(null!=model&&"oracle".equalsIgnoreCase(cd.getDatabase())&&null!=cd.getPage()){
			sber.append("SELECT \n ");
			sber.append(StringUtils.join(columnNames,","));
                        sber.append(",RN ");
			sber.append(" \nFROM (");
			sber.append(" \n    SELECT t.*,ROWNUM RN  FROM (");
		}
		if(null!=model){
			sber.append("\n           SELECT \n                 ");
			sber.append(StringUtils.join(columnNames,","));
			sber.append("\n           FROM  ");
			sber.append(model.getTableName());
			sber.append(" \n           WHERE ");
			sber.append(cd.getCdtion().toString());
		}
		if(null!=model&&"oracle".equalsIgnoreCase(cd.getDatabase())&&null!=cd.getPage()){
			sber.append("\n    ) t WHERE ROWNUM<=");
			sber.append(cd.getPage().getPageNow()*cd.getPage().getPageSize());
			sber.append(" \n) WHERE RN >=");
                        sber.append(cd.getPage().getPageNow()==1?0:(cd.getPage().getPageNow()-1)*cd.getPage().getPageSize());
		}
		return sber.toString();
	}
	
	public static String countSQL(SQLModel model,Condition condition){
		StringBuilder sber=new StringBuilder();
                String[] columnNames=model.getColumnNames();
                sber.append("SELECT COUNT(*) FROM (");
		sber.append("\n    SELECT  ");
		sber.append(StringUtils.join(columnNames,","));
		sber.append(" \n    FROM  ");
		sber.append(model.getTableName());
		sber.append(" \n    WHERE  ");
		sber.append(condition.toString());
                sber.append("\n) c ");
		return sber.toString();
	}
	
}
