package com.thang;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.thang.executor.DBExecutor;
import com.thang.model.Condition;
import com.thang.model.Page;
import com.thang.pojo.Role;
import com.thang.pojo.User;

public class DBETest {

    private DBExecutor dbe=null;	
	
	@Before
	public void setUp() throws Exception {
		dbe=new DBExecutor("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/test","root","su","mysql");
	}

        //@Test
	public void testMysql() {
               Page page=new Page();
               List<Role> roles=dbe.list(new Condition(Role.class,page));
               System.out.println(page.getTotal());
               for(Role u:roles){
                   System.out.println(u.getId());
               }
	}

	@Test
	public void testOracle() {
              dbe=new DBExecutor("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@192.168.30.218:1521/pmsdb","pms","rainsoft","oracle");
               Page page=new Page();
               List<User> users=dbe.list(new Condition(User.class,page));
               System.out.println(page.getTotal());
               for(User u:users){
                   System.out.println(u.getId());
               }
               
            
	}

}
