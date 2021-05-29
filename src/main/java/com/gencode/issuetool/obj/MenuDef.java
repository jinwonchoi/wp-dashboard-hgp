package com.gencode.issuetool.obj;

public class MenuDef extends Pojo {

	protected long id;
	protected String name;
	protected String menuPath;
	protected String rolesAccess;
	protected String rolesRead;
	protected String rolesWrite;

	public MenuDef() {
		// TODO Auto-generated constructor stub
	}

	public MenuDef(long id, String name, String menuPath, String rolesAccess, String rolesRead,
			String rolesWrite) {
		super();
		this.id = id;
		this.name = name;
		this.menuPath = menuPath;
		this.rolesAccess = rolesAccess;
		this.rolesRead = rolesRead;
		this.rolesWrite = rolesWrite;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getRolesAccess() {
		return rolesAccess;
	}

	public void setRolesAccess(String rolesAccess) {
		this.rolesAccess = rolesAccess;
	}

	public String getRolesRead() {
		return rolesRead;
	}

	public void setRolesRead(String rolesRead) {
		this.rolesRead = rolesRead;
	}

	public String getRolesWrite() {
		return rolesWrite;
	}

	public void setRolesWrite(String rolesWrite) {
		this.rolesWrite = rolesWrite;
	}

	
}
