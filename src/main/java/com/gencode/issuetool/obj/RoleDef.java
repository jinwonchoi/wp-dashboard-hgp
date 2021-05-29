package com.gencode.issuetool.obj;

public class RoleDef extends Pojo {
	protected String roleId;
	protected String roleName;

	public RoleDef() {
		// TODO Auto-generated constructor stub
	}

	public RoleDef(String roleId, String roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}
