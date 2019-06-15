package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.dao.PermissionDao;
import com.github.zhangkaitao.shiro.chapter6.dao.PermissionDaoImpl;
import com.github.zhangkaitao.shiro.chapter6.entity.Permission;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:48
 */
public class PermissionServcieImpl implements PermissionServcie{

	private PermissionDao permissionDao = new PermissionDaoImpl();

	public Permission createPermission(Permission permission) {
		return permissionDao.createPermission(permission);
	}

	public void deletePermission(Long permissionId) {
		permissionDao.deletePermission(permissionId);
	}
}
