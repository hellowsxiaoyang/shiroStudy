package com.github.zhangkaitao.shiro.chapter6.dao;

import com.github.zhangkaitao.shiro.chapter6.entity.Role;
import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-20:45
 */
public interface RoleDao {

	public Role createRole(Role role);
	public void deleteRole(Long roleId);

	public void correlationPermissions(Long roleId, Long...permissionIds);
	public void uncorrelationPermissions(Long roleId, Long...permissionIds);
}
