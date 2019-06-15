package com.github.zhangkaitao.shiro.chapter6.entity;

import java.io.Serializable;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-18:26
 */
public class RolePermission implements Serializable {
	private Long roleId;
	private Long permissionId;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		RolePermission that = (RolePermission) o;

		//对permissionId进行非空判断
		if (permissionId != null ? !permissionId.equals(that.permissionId) : that.permissionId != null) return false;
		//对roleId进行非空判断
		if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		//根据roleId和permissionId的hashCode来生成最终的hashCode
		int result = roleId != null ? roleId.hashCode() : 0;
		result = 31 * result + (permissionId != null ? permissionId.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "RolePermssion{" +
				"roleId=" + roleId +
				", permissionId=" + permissionId +
				'}';
	}
}
