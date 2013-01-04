package org.com.thang.model;

import org.com.thang.model.mate.Table;
import org.com.thang.model.mate.Foreign;
@Table("sys_user_info")
public class User extends Model<User>{

	private String id;
	private String userName;
	private String loginName;
	private String photo;
	private String email;
	private String birth;
	private String sex;
	
	@Foreign(foreignClass=Dept.class,foreignKey="id")
	private Dept deptId;
	private String opt;
	private String createTime;
	private String createPerson;
	private String reference;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Dept getDeptId() {
		return deptId.select();
	}
	public void setDeptId(Dept deptId) {
		this.deptId = deptId;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
}
