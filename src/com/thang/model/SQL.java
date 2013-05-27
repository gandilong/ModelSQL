package com.thang.model.sql;

import org.mvel2.MVEL;

import com.thang.model.Model;
import com.thang.utils.ActionValues;
import com.thang.utils.lang.StringUtils;

public enum SQL {

  InsertSQL("with(model){" +
			        "sber.delete(0,model.sber.length());" +
			        "sber.append(' INSERT INTO ');" +
			        "sber.append(tableName);" +
			        "sber.append('(');" +
			        "for(int i=0;i<size;i++){" +
			            "if(i!=0)sber.append(',');" +
			            "sber.append(get(i).columnName);" +
			        "}" +
			        "sber.append(') VALUES(');" +
			        "for(int k=0;k<size;k++){" +
			            "if(k!=0)sber.append(',');" +
			            "if(1==get(k).fieldType){" +
			                "sber.append((get(k).fieldValue==null?0:get(k).fieldValue));" +
			            "}else{" +
			                "sber.append(\"'\"+(get(k).fieldValue==null?'':get(k).fieldValue)+\"'\");" +
			            "}" +
			        "}" +
			        "sber.append(')');}" +
			        "return model.sber.toString();"),
	DeleteSQL("import com.thang.model.MField;" +
			      "MField _id=model.getMField('id'); " +
			      "with(model){" +
			          "sber.delete(0,model.sber.length());" +
			          "sber.append(' DELETE FROM ');" +
			          "sber.append(tableName);" +
			          "if(1==_id.fieldType){" +
			              "sber.append(' WHERE ID=');" +
			              "sber.append(_id.fieldValue==null?0:_id.fieldValue);" +
			          "}else{" +
			              "sber.append(\" WHERE ID='\");" +
			              "sber.append((_id.fieldValue==null?'':_id.fieldValue)+\"'\");"+
			          "}"+
			      "}" +
			      "return model.sber.toString();" ),
	SelectSQL("import com.thang.model.MField;" +
			  "MField _id=model.getMField('id'); " +
			  "with(model){" +
			      "sber.delete(0,model.sber.length());" +
			      "sber.append('SELECT ');" +
			      "for(int i=0;i<size;i++){" +
		            "if(i!=0)sber.append(',');" +
		            "sber.append(get(i).columnName);" +
		          "}" +
		          "sber.append(' FROM ');" +
		          "sber.append(tableName);" +
			  "}" +
			  "if(null==condition){" +
			         "if(null!=_id.fieldValue&&-1!=_id.fieldValue){" +
                         "if(1==_id.fieldType){" +
                             "sber.append(' WHERE ID=');" +
                             "sber.append(_id.fieldValue==null?0:_id.fieldValue);" +
                         "}else{" +
                             "sber.append(\" WHERE ID='\");" +
                             "sber.append((_id.fieldValue==null?'':_id.fieldValue)+\"'\");"+
                         "}" +
                     "}" +
			   "}else{" +
			       //"with(condition){" +
			           "if(null!=condition.cdtion&&''!=condition.cdtion&&condition.cdtion.length()>0){" +
			               "model.sber.append(' WHERE ');" +
			               "model.sber.append(condition.cdtion);" +
			           "}" +
			           "model.sber.append(' ORDER BY ');" +
			           "model.sber.append(condition.page.orderBy);"+
			           "model.sber.append(' ');"+
			           "model.sber.append(condition.page.order);"+
			           "if(condition.page.page){" +
			               "model.sber.append(' LIMIT ');" +
			               "if(1==condition.page.pageNow){" +
			                   "model.sber.append(0);" +
			               "}else{" +
			                   "model.sber.append(condition.page.pageNow*condition.page.pageSize-1);" +
			               "}" +
			               "model.sber.append(',');" +
			               "model.sber.append(condition.page.pageNow*condition.page.pageSize);"+
			           "}" +
			      // "}" +
			       "" +
			   "}" +
			  "" +
			  "return model.sber.toString();"),
	UpdateSQL("import com.thang.model.MField;" +
			  "MField _id=model.getMField('id'); " +
			  "with(model){" +
			      "sber.delete(0,model.sber.length());" +
			      "sber.append('UPDATE ');" +
			      "sber.append(tableName);" +
			      "sber.append(' SET ');" +
			      "for(int i=0;i<size;i++){" +
			          "MField field=get(i);" +
			          "if(null!=field.fieldValue&&'-1'!=field.fieldValue&&''!=field.fieldValue&&'id'!=field.fieldName){" +
			              "if(1==field.fieldType){" +
			                   "sber.append(field.columnName);" +
			                   "sber.append('=');" +
			                   "sber.append(field.fieldValue);" +
			              "}else{" +
			                  "sber.append(field.columnName);" +
			                  "sber.append(\"='\");" +
			                  "sber.append(field.fieldValue+\"'\");" +
			              "}" +
			              "sber.append(',')" +
			          "}" +
			      "}" +
			      "sber.delete(sber.length()-1,sber.length());" +
			   "}" +
			      "if(1==_id.fieldType){" +
                      "model.sber.append(' WHERE ID=');" +
                      "model.sber.append(_id.fieldValue==null?0:_id.fieldValue);" +
                  "}else{" +
                      "model.sber.append(\" WHERE ID='\");" +
                      "model.sber.append((_id.fieldValue==null?'':_id.fieldValue)+\"'\");"+
                  "}" +
			  "return model.sber.toString();");
	
	private String sql=null;
	private static final ActionValues values=new ActionValues();
	
	private SQL(String sql){
		this.sql=sql;
	}
	 
	@Override
	public String toString() {
		return this.sql;
	}
	
	/**
	 * 生成SQL语句。
	 * @param values
	 * @return
	 */
	public String sql(Model model){
		values.clear();
		values.put("model", model);
		return StringUtils.println(String.valueOf(MVEL.executeExpression(MVEL.compileExpression(sql),values)));
	}
	
	public String sql(Model model,Object value){
		values.clear();
		values.put("model", model);
		values.put("condition", value);
		return StringUtils.println(String.valueOf(MVEL.executeExpression(MVEL.compileExpression(sql),values)));
	}
	
}
