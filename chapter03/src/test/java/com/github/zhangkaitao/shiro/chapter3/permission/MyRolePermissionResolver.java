package com.github.zhangkaitao.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;
import sun.reflect.generics.tree.Wildcard;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-12-14:32
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if ("role2".equals(roleString)){
			return Arrays.asList((Permission) new WildcardPermission("menu:*"));
		}
//		if ("role2".equals(roleString)){
//			return Arrays.asList((Permission)new WildcardPermission("user2:*"));
//		}
		return null;
	}
}
