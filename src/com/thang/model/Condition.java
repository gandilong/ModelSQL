package com.thang.model;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.thang.utils.reflect.ModelUtils;

public class Condition {

	private Page page=null;
	private Class<?> modelCls;
	private StringBuilder sber;
	private StringBuilder cdtion;
	private boolean hasPages=false;
	private boolean hasCondition=false;
	
	private SQLModel model=null;
	
	private String orderBy="ID";
	private String order="DESC";
        
    private static String database=null;//数据库类型 mysql ,oracle,sqlserver
	
	public Condition(Class<?> cls){
		modelCls=cls;
		model=new SQLModel(cls);
		cdtion=new StringBuilder();
	}
	
	public Condition(Class<?> cls,Page page){
		this.page=page;	
		modelCls=cls;
		model=new SQLModel(cls);
		cdtion=new StringBuilder();
		this.hasPages=true;
	}
	
	public Condition(Object pojo,Page page){
        this.page=page;	
		modelCls=pojo.getClass();
		model=new SQLModel(modelCls);
		cdtion=new StringBuilder();
		this.hasPages=true;
	}
	
	
	/**
	 * 核心添加条件方法
	 * @param fieldName
	 * @param link
	 * @param fieldValue
	 */
	private void addCondition(String fieldName,Link link,String fieldValue){
		try{
			
			if(!hasCondition){
				hasCondition=true;
			}else{
				if(!cdtion.toString().trim().endsWith("AND")&&!cdtion.toString().trim().endsWith("OR")){
					and();
				}
			}
			
		    cdtion.append(" (");
		    cdtion.append(ModelUtils.getColumnName(modelCls.getDeclaredField(fieldName)));
		    cdtion.append(link.value());
                    
                    if("IN".equals(link.value().trim())){
                          cdtion.append("(");
                          cdtion.append(fieldValue);
                          cdtion.append(")");
                    }else if("NOT IN".equals(link.value().trim())){
                          cdtion.append("(");
                          cdtion.append(fieldValue);
                          cdtion.append(")");
                    }else if("LIKE".equals(link.value().trim())){
                          cdtion.append("'%");
                          cdtion.append(fieldValue);
                          cdtion.append("%'");
                    }else{
                    
		        if(ModelUtils.isNumber(modelCls.getDeclaredField(fieldName))){
			       cdtion.append(fieldValue);	
		        }else{
			    cdtion.append("'");
			    cdtion.append(fieldValue);
			    cdtion.append("'");
		        }
                    }
		    cdtion.append(") ");
	    }catch(Exception e){
		    e.printStackTrace();
	    }
	}
	
	
	/**
	 * 添加等于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition eq(String fieldName, Object fieldValue) {
	    if(fieldValue.getClass() == this.modelCls){
              addCondition(fieldName,Link.EQUAL,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	    }else{
		      addCondition(fieldName,Link.EQUAL,String.valueOf(fieldValue));
	    }
		return this;
	}

	/**
	 * 添加不等于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition ne(String fieldName, Object fieldValue) {
          if(fieldValue.getClass() == this.modelCls){
              addCondition(fieldName,Link.NOT_EQUAL,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		     addCondition(fieldName,Link.NOT_EQUAL,String.valueOf(fieldValue));
         }
		return this;
	}

	/**
	 * 添加小于等于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition le(String fieldName, Object fieldValue) {
             if(fieldValue.getClass() == this.modelCls){
                  addCondition(fieldName,Link.LESS_EQUAL,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		addCondition(fieldName,Link.LESS_EQUAL,String.valueOf(fieldValue));
             }
		return this;
	}

	/**
	 * 添加大于等于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition ge(String fieldName, Object fieldValue) {
            if(fieldValue.getClass() == this.modelCls){
                  addCondition(fieldName,Link.GRATE_EQUAL,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		addCondition(fieldName,Link.GRATE_EQUAL,String.valueOf(fieldValue));
            }
		return this;
	}

	/**
	 * 添加大于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition gt(String fieldName, Object fieldValue) {
            if(fieldValue.getClass() == this.modelCls){
                  addCondition(fieldName,Link.GRATE_THAN,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		addCondition(fieldName,Link.GRATE_THAN,String.valueOf(fieldValue));
            }
		return this;
	}

	/**
	 * 添加小于条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition lt(String fieldName, Object fieldValue) {
         if(fieldValue.getClass() == this.modelCls){
             addCondition(fieldName,Link.LESS_THAN,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		     addCondition(fieldName,Link.LESS_THAN,String.valueOf(fieldValue));
          }
		return this;
	}

	/**
	 * 添加Like条件
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public Condition like(String fieldName, Object fieldValue) {
         if(fieldValue.getClass() == this.modelCls){
             addCondition(fieldName,Link.LIKE,String.valueOf(ModelUtils.getProperty(fieldValue,fieldName)));
	     }else{
		     addCondition(fieldName,Link.LIKE,String.valueOf(fieldValue));
         }
		return this;
	}

	/**
	 * 添加In条件
	 * @param fieldName
	 * @param fieldValues
	 * @return
	 */
	public Condition in(String fieldName, Object[] fieldValues) {
            try{
                String[] values=StringUtils.join(fieldValues,",").split(",");
		addCondition(fieldName,Link.IN,join(values,ModelUtils.isNumber(modelCls.getDeclaredField(fieldName))));
            }catch(Exception e){
                e.printStackTrace();
            }
		return this;
	}
	
	/**
	 * 添加In条件
	 * @param fieldName
	 * @param fieldValues
	 * @return
	 */
	public Condition in(String fieldName, List<String> fieldValues) {
        in(fieldName, fieldValues.toArray());
		return this;
	}
	
	public Condition and(){
		cdtion.append(" AND ");
		return this;
	}
	
	public Condition or(){
		cdtion.append(" OR ");
		return this;
	}

	public Condition notIn(String fieldName, Object[] fieldValues) {
            try{
                String[] values=StringUtils.join(fieldValues,",").split(",");
		        addCondition(fieldName,Link.NOT_IN,join(values,ModelUtils.isNumber(modelCls.getDeclaredField(fieldName))));
            }catch(Exception e){
                e.printStackTrace();
            }
            return this;
	}
	
	private String join(String[] values,boolean isNumber){
		int i=0;
		if(null==sber){
			sber=new StringBuilder();			
		}else{
			sber.delete(0, sber.length());
		}
		for(String value:values){
			if(0!=i){
			    sber.append(",");				
			}
			if(isNumber){
				sber.append(value);	
			}else{
				sber.append("'");
				sber.append(value);
				sber.append("'");
			}
			
			i++;
		}
		return sber.toString();
	}

	public StringBuilder getCdtion() {
		
		if(cdtion.toString().trim().length()<=0){
			cdtion.append(" 1=1 ");
		}
		
		cdtion.append(" \n           ORDER BY ");
		cdtion.append(getOrderBy());
		cdtion.append(" ");
		cdtion.append(getOrder());
		cdtion.append(" ");
		
                if(null!=page){
                    if("mysql".equalsIgnoreCase(getDatabase())){
                        cdtion.append(" LIMIT ");
			            cdtion.append(page.getPageNow()<=1?0:(page.getPageNow()-1)*page.getPageSize());
			            cdtion.append(",");
			            cdtion.append(page.getPageNow()<=1?page.getPageSize():page.getPageNow()*page.getPageSize());
                    }else if("oracle".equalsIgnoreCase(getDatabase())){
                        
                    }else if("sqlserver".equalsIgnoreCase(getDatabase())){
                    
                    }
                }
			
		return cdtion;
	}

	private String getOrderBy() {
		return orderBy;
	}
	public Condition setOrderBy(String orderBy) {
		this.orderBy = orderBy;
		return this;
	}
	private String getOrder() {
		return order;
	}
	public Condition setOrder(String order) {
		this.order = order;
		return this;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	public Page getPage() {
		return page;
	}

    public String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        Condition.database = database;
    }
    public boolean isHasPages() {
		return hasPages;
	}

	@Override
    public String toString() {
        if(cdtion.toString().trim().length()<=0){
            return " 1=1 ";
        }
        return cdtion.toString();
    }

	public SQLModel getModel() {
		return model;
	}

	public Class<?> getModelCls() {
		return modelCls;
	}
    
}
