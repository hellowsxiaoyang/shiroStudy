package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.Role;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:38
 */
public interface RoleService {

	public Role createRole(Role role);
	public void deleteRole(Long roleId);

	/**
	 * 添加角色-权限关系
	 */
	public void correlationPermissions(Long roleId, Long...permissionIds);

	/**
	 * 移除角色-权限关系
	 * */
	public void uncorrelationPermissions(Long roleId, Long...permissionIds);
}
