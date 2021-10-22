package bkdn.pbl6.main.configs.models;

import org.springframework.security.core.GrantedAuthority;

import bkdn.pbl6.main.enums.Role;

public class RoleModel implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private Role roleId;
	private String role;

	public RoleModel(Role role) throws Exception {
		roleId = role;
		switch (role) {
		case User:
			this.role = new String("USER");
			break;
		case Admin:
			this.role = new String("ADMIN");
			break;
		case Tutor:
			this.role = new String("TUTOR");
			break;
		default:
			throw new Exception("Role " + role + " khong hop le");
		}
	}

	@Override
	public String getAuthority() {
		return this.role;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

}
