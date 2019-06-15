package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.entity.Permission;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:47
 */
public interface PermissionServcie {
	public Permission createPermission(Permission permission);
	public void deletePermission(Long permissionId);
}
