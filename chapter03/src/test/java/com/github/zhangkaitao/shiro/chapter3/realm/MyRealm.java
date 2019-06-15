package com.github.zhangkaitao.shiro.chapter3.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-12-10:51
 */
public class MyRealm extends AuthorizingRealm {

	//授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		//添加角色
		authorizationInfo.addRole("role1");
		authorizationInfo.addRole("role2");

		//添加权限
		authorizationInfo.addStringPermission("user2:*");
		authorizationInfo.addStringPermission("user:create");
		return authorizationInfo;
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String name = (String) authenticationToken.getPrincipal();
		String password = new String((char[])authenticationToken.getCredentials());

		if (!"zhang".equals(name)){
			throw new UnknownAccountException();
		}
		if (!"123".equals(password)){
			throw new IncorrectCredentialsException();
		}

		return new SimpleAuthenticationInfo(name,password,getName());
	}
}
