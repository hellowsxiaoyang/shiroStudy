package com.github.zhangkaitao.shiro.chapter5.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-13-16:11
 */
public class MyRealm extends AuthorizingRealm {
	private PasswordService passwordService;

	//private CredentialsMatcher credentialsMatcher;

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}


	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		return new SimpleAuthenticationInfo("wu",passwordService.encryptPassword("123"),getName());
	}
}
