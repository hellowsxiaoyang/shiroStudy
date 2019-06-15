package com.github.zhangkaitao.shiro.chapter6;

import com.github.zhangkaitao.shiro.chapter6.entity.Permission;
import com.github.zhangkaitao.shiro.chapter6.entity.Role;
import com.github.zhangkaitao.shiro.chapter6.entity.User;
import com.github.zhangkaitao.shiro.chapter6.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-22:43
 */
public abstract class BaseTest {

	protected UserService userService = new UserServiceImpl();
	protected RoleService roleService = new RoleServcieImpl();
	protected PermissionServcie permissionServcie = new PermissionServcieImpl();

	protected User user1;
	protected User user2;
	protected User user3;
	protected User user4;

	protected String password = "123";

	protected Role role1;
	protected Role role2;

	protected Permission permission1;
	protected Permission permission2;
	protected Permission permission3;

	@Before
	public void setUp(){
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_permissions");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_users_roles");
		JdbcTemplateUtils.jdbcTemplate().update("delete from sys_roles_permissions");

		//新增权限
		permission1 = new Permission("user:create", "用户新增模块", Boolean.TRUE);
		permission2 = new Permission("user:update", "用户修改模块", Boolean.TRUE);
		permission3 = new Permission("menu:create", "菜单新增模块", Boolean.TRUE);
		permissionServcie.createPermission(permission1);
		permissionServcie.createPermission(permission2);
		permissionServcie.createPermission(permission3);

		//新增角色
		role1 = new Role("admin", "管理员", Boolean.TRUE);
		role2 = new Role("user", "用户管理员",Boolean.TRUE);
		roleService.createRole(role1);
		roleService.createRole(role2);

		//关联 角色-权限
		roleService.correlationPermissions(role1.getId(), permission1.getId(), permission2.getId(), permission3.getId());
		roleService.correlationPermissions(role2.getId(), permission1.getId(), permission2.getId());

		//新增用户
		user1 = new User("zhang", password);
		user2 = new User("li", password);
		user3 = new User("wang", password);
		user4 = new User("wu", password);
		userService.createUser(user1);
		userService.createUser(user2);
		userService.createUser(user3);
		userService.createUser(user4);
		//关联 用户-角色
		userService.correlationRoles(user1.getId(), role1.getId());
	}




	public void tearDown() throws Exception{
		ThreadContext.unbindSubject();
	}

	public void login(String configFile, String username, String password){
		Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = (SecurityManager)securityManagerFactory.getInstance();

		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		subject.login(token);
	}

	public Subject subject(){
		return SecurityUtils.getSubject();
	}
}
