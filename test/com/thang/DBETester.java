package com.thang;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thang.executor.DBExecutor;
import com.thang.model.SQLModel;
import com.thang.pojo.Role;
import com.thang.pojo.User;
import com.thang.utils.reflect.ModelUtils;

public class DBETester {

	private DBExecutor dbe=null;	
	
	@Before
	public void setUp() throws Exception {
		dbe=new DBExecutor("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test","root","su","mysql");
	}

	public void testCountClassOfQ() {
		System.out.println("count(User.class)="+dbe.count(User.class));
	}

	public void testCountString() {
		System.out.println("count(sql)="+dbe.count("select count(0) from "+ModelUtils.getTableName(User.class)));
	}

	public void testExecute() {
		dbe.update("update test set name='good' where id=2");
	}

	public void testColumnClassOfQStringLong() {
		System.out.println("column(User.class,'columnName',id)="+dbe.column(User.class, "loginName", 1));
	}

	public void testColumnClassOfQStringString() {
		System.out.println("column(User.class,'columnName',id)="+dbe.column(User.class, "user_name", "8"));
	}

	public void testColumnsClassOfQString() {
		System.out.println("columns(sql)="+dbe.columns("select user_name from sys_user_info"));
	}

	public void testColumnsStringCondition() {
		System.out.println(""+dbe.columns(User.class, "loginName"));
	}


	public void testInsert() {
		User u=new User();
		u.setBirth("2010-03-01");
		u.setLogin_pass("dddd");
		u.setUser_name("myName");
		u.setLoginName("gooddear");
		dbe.insert(u);
		System.out.println("insert a user");
	}

	public void testInsertWidthID() {
		Role r=new Role();
		r.setName("good");
		dbe.insertWidthID(r);
		
		dbe.insertWidthID(new User());
	}

	public void testInsertOrUpdate() {
		dbe.delete(User.class, 1);
	}

	@Test
	public void testDeleteClassOfQLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteClassOfQString() {
		fail("Not yet implemented");
	}

	@Test
	public void testListClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testListDesc() {
		fail("Not yet implemented");
	}

	@Test
	public void testListAsc() {
	}

	@Test
	public void testListCondition() {
		SQLModel model=new SQLModel(User.class);
		model.getCondition().eq("loginName", "admin").orderBy("loginName").order("asc");
		
		List<User> user=dbe.list(model);
		for(User u:user){
			System.out.println(u.getUser_name());
		}
	}

	@Test
	public void testQueryStringClassOfT() {
		
		User user=dbe.get(User.class,1);
			System.out.println(user.getUser_name());
	}

	@Test
	public void testGetClassOfTLong() {
		SQLModel model=new SQLModel(User.class);
		model.setPageNow(2);
	    dbe.list(model);
	    System.out.println(model.getTotal());
	}

	@Test
	public void testGetClassOfTString() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		dbe.update("update sys_user_info set sex='0' where id>11");
	}

}
