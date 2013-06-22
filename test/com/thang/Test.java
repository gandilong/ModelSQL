package com.thang;

import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.thang.executor.DBExecutor;
import com.thang.model.Condition;
import com.thang.pojo.User;

public class Test {

	
	public static void main(String[] args) throws Exception {
		User u=new User();
		//DbUtils.loadDriver("com.mysql.jdbc.Driver");
		u.setId(1);
		u.setUname("gandilong");
		
		
		/*
		Method[] methods=User.class.getDeclaredMethods();
		System.out.println("method lenght="+methods.length);
		for(Method m:methods){ 
			System.out.println(m.getName());
			if(m.getName().startsWith("get")){
				Object obj=m.invoke(u, null);
				if(obj instanceof String){
					System.out.println("string="+obj);	
				}
				if(obj instanceof Class<?>){
					System.out.println("class="+((Class<?>)obj));
				}
				
			}
		}*/
		//System.out.println(MVEL.executeExpression(MVEL.compileExpression(SQL.InsertSQL.toString()),new Model(u)));
		//SQL.InsertSQL.sql(new Model(User.class));
		
		DBExecutor dbe=new DBExecutor();
 //User uu=dbe.get(User.class, "aaaa");
		//u.setId("00adfsddfdffswer");
		//dbe.insert(u);
		//dbe.delete(User.class,"good");
		long start=System.currentTimeMillis();
		//u.setUname("hahaha");
		//dbe.update(u);
		//dbe.delete(User.class,"good");
		//User uu=dbe.get(User.class, null);
		Condition con=new Condition(User.class,true);
		//con.eq("id", "adfsdfswer").eq("uname", "hahaha");
		//con.and();
		List<User> us=dbe.list(User.class, con);
		//uu.setType(10002);
		//dbe.update(uu);
		System.out.println(us.size());
		System.out.println(System.currentTimeMillis()-start);
		//new Thread() {public void run() { 
			// JUnitCore.runClasses(new Class[] { TestExample.class }); 
			// new JUnitCore().run(Request.method(TestExample.class, "testMethod"));
			//}}.start();

		//dbe.delete(User.class, "good");
		
	}
}
