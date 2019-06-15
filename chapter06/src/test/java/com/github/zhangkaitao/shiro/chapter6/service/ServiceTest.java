package com.github.zhangkaitao.shiro.chapter6.service;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-23:06
 */
public class ServiceTest extends BaseTest {

	@Test
	public void testUserRolePermissionRelation(){
		Set<String> roles = userService.findRoles(user1.getUsername());
		Assert.assertEquals(1, roles.size());
		Assert.assertTrue(roles.contains(role1.getRole()));

		Set<String> permissions = userService.findPermissions(user1.getUsername());
		Assert.assertEquals(3, permissions.size());
		Assert.assertTrue(permissions.contains(permission3.getPermission()));

		//li
		roles = userService.findRoles(user2.getUsername());
		Assert.assertEquals(0, roles.size());
		permissions = userService.findPermissions(user2.getUsername());
		Assert.assertEquals(0, permissions.size());

		//解除admin-menu:create 关联
		roleService.uncorrelationPermissions(role1.getId(), permission3.getId());
		permissions = userService.findPermissions(user1.getUsername());
		Assert.assertEquals(2,permissions.size());
		Assert.assertFalse(permissions.contains(permission3.getPermission()));

		//删除一个permission
		permissionServcie.deletePermission(permission2.getId());
		permissions = userService.findPermissions(user1.getUsername());
		Assert.assertTrue(permissions.size() == 1);

		//解除zhang-admin 关联
		userService.uncorrelationRoles(user1.getId(), role1.getId());
		permissions = userService.findPermissions(user1.getUsername());
		Assert.assertTrue(permissions.size() == 0);
	}
}
