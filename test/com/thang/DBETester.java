package com.thang;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thang.executor.DBExecutor;
import com.thang.model.Condition;
import com.thang.pojo.Role;
import com.thang.pojo.User;
import com.thang.utils.reflect.ModelUtils;

public class DBETester {

	private DBExecutor dbe=null;	
	
	@Before
	public void setUp() throws Exception {
		dbe=new DBExecutor("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test","root","su","mysql");
	}

	@Test
	public void testCountClassOfQ() {
		System.out.println("count(User.class)="+dbe.count(User.class));
	}

	@Test
	public void testCountString() {
		System.out.println("count(sql)="+dbe.count("select count(0) from "+ModelUtils.getTableName(User.class)));
	}

	@Test
	public void testExecute() {
		dbe.update("update test set name='good' where id=2");
	}

	@Test
	public void testColumnClassOfQStringLong() {
		System.out.println("column(User.class,'columnName',id)="+dbe.column(User.class, "loginName", 1));
	}

	@Test
	public void testColumnClassOfQStringString() {
		System.out.println("column(User.class,'columnName',id)="+dbe.column(User.class, "user_name", "8"));
	}

	@Test
	public void testColumnsClassOfQString() {
		System.out.println("columns(sql)="+dbe.columns("select user_name from sys_user_info"));
	}

	@Test
	public void testColumnsStringCondition() {
		System.out.println(""+dbe.columns(User.class, "loginName"));
	}


	@Test
	public void testInsert() {
		User u=new User();
		u.setBirth("2010-03-01");
		u.setLogin_pass("dddd");
		u.setUser_name("myName");
		u.setLoginName("gooddear");
		dbe.insert(u);
		System.out.println("insert a user");
	}

	@Test
	public void testInsertWidthID() {
		Role r=new Role();
		r.setName("good");
		dbe.insertWidthID(r);
		
		dbe.insertWidthID(new User());
	}

	@Test
	public void testInsertOrUpdate() {
		
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
		List<User> user=dbe.listAsc(User.class, "loginName");
		for(User u:user){
			//System.out.println(u.getUser_name());
		}
	}

	@Test
	public void testListCondition() {
		Condition c=new Condition(User.class);
		List<User> user=dbe.list(c);
		for(User u:user){
			System.out.println(u.getUser_name());
		}
	}

	@Test
	public void testQueryStringClassOfT() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClassOfTLong() {
		fail("Not yet implemented");
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
