package com.thang.model;


import com.thang.utils.reflect.ModelUtils;

public class Condition {

	private Page page=null;
	private Class<?> model;
	private StringBuilder sber;
	private StringBuilder cdtion;
	private boolean hasCondition=false;
	
	private String orderBy="ID";
	private String order="DESC";
	
	public Condition(Class<?> cls){
		model=cls;
		cdtion=new StringBuilder();
	}
	
	public Condition(Class<?> cls,boolean toPage){
		if(toPage){
			page=new Page(cls);	
		}
		model=cls;
		cdtion=new StringBuilder();
	}
	
	public void addCondition(String fieldName,Link link,String fieldValue){
		try{
			
			if(!hasCondition){
				hasCondition=true;
			}else{
				if(!cdtion.toString().trim().endsWith("AND")&&!cdtion.toString().trim().endsWith("OR")){
					and();
				}
			}
			
		    cdtion.append(" (");
		    cdtion.append(ModelUtils.getColumnName(model.getDeclaredField(fieldName)));
		    cdtion.append(link.value());
		    if(ModelUtils.isNumber(model.getDeclaredField(fieldName))){
			    cdtion.append(fieldValue);	
		    }else{
			    cdtion.append("'");
			    cdtion.append(fieldValue);
			    cdtion.append("'");
		    }
		    cdtion.append(") ");
	    }catch(Exception e){
		    e.printStackTrace();
	    }
	}
	
	public Condition eq(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.EQUAL,fieldValue);
		return this;
	}

	public Condition ne(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.NOT_EQUAL,fieldValue);
		return this;
	}

	public Condition le(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.LESS_EQUAL,fieldValue);
		return this;
	}

	public Condition ge(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.GRATE_EQUAL,fieldValue);
		return this;
	}

	public Condition gt(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.GRATE_THAN,fieldValue);
		return this;
	}

	public Condition lt(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.LESS_THAN,fieldValue);
		return this;
	}

	public Condition like(String fieldName, String fieldValue) {
		addCondition(fieldName,Link.LIKE,fieldValue);
		return this;
	}

	public Condition in(String fieldName, String[] fieldValues) throws SecurityException, NoSuchFieldException {
		addCondition(fieldName,Link.IN,join(fieldValues,ModelUtils.isNumber(model.getDeclaredField(fieldName))));
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

	public void notIn(String fieldName, String[] fieldValues) throws SecurityException, NoSuchFieldException {
		addCondition(fieldName,Link.NOT_IN,join(fieldValues,ModelUtils.isNumber(model.getDeclaredField(fieldName))));
	}
	
	public String join(String[] values,boolean isNumber){
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
		
		if(cdtion.length()==0){
			cdtion.append(" 1=1 ");
		}
		
		cdtion.append(" \nORDER BY ");
		cdtion.append(getOrderBy());
		cdtion.append(" ");
		cdtion.append(getOrder());
		cdtion.append(" ");
		
		if(null!=page){
			cdtion.append(" LIMIT ");
			cdtion.append(page.getPageNow()==1?0:(page.getPageNow()-1)*page.getPageSize());
			cdtion.append(",");
			cdtion.append(page.getPageNow()*page.getPageSize());
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

	
}
