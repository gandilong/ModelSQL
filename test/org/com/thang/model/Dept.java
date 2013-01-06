package org.com.thang.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.com.thang.model.mate.Foreign;
import org.com.thang.model.mate.Table;
import org.com.thang.utils.StrUtils;

@Table("sys_dept_info")
public class Dept extends Model<Dept>{

	private long id;
	private String deptName;//部门名称
	
	@Foreign(foreignClass=User.class,foreignKey="id")
	private User user;//部门负责人
	private String opt;//备注
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public User getUser()  {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	
}
