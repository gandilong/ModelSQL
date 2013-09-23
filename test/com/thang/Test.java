package com.thang;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.thang.utils.db.ConnectionUtils;


public class Test {

	
	public static void main(String[] args) throws Exception {
		DataSource dataSource=ConnectionUtils.getDataSource();
		QueryRunner queryRunner=new QueryRunner(dataSource);
		
		List<String> user=queryRunner.query("select user_name from sys_user_info", new ColumnListHandler<String>(1));
		
		for(String u:user){
			System.out.println(u.toString());
		}
	}
}
