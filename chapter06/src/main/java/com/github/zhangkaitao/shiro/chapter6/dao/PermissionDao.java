package com.github.zhangkaitao.shiro.chapter6.dao;

import com.github.zhangkaitao.shiro.chapter6.entity.Permission;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:05
 */
public interface PermissionDao {
	public Permission createPermission(Permission permission);

	public void deletePermission(Long permissionId);
}
