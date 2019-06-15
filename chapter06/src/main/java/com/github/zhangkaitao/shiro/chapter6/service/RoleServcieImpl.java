package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.dao.RoleDao;
import com.github.zhangkaitao.shiro.chapter6.dao.RoleDaoImpl;
import com.github.zhangkaitao.shiro.chapter6.entity.Role;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:40
 */
public class RoleServcieImpl implements RoleService{

	private RoleDao roleDao = new RoleDaoImpl();

	public Role createRole(Role role) {
		return roleDao.createRole(role);
	}

	public void deleteRole(Long roleId) {
		roleDao.deleteRole(roleId);
	}

	public void correlationPermissions(Long roleId, Long... permissionIds) {
		roleDao.correlationPermissions(roleId, permissionIds);
	}

	public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
		roleDao.uncorrelationPermissions(roleId,  permissionIds);
	}
}
