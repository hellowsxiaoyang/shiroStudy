package com.github.zhangkaitao.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-11-16:30
 */
public class MyRealm2 implements Realm {
	public String getName() {
		return "myRealm2";
	}

	public boolean supports(AuthenticationToken authenticationToken) {
		return (authenticationToken instanceof UsernamePasswordToken);
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		//获取用户名
		String name = (String) authenticationToken.getPrincipal();
		//获取密码
		String password = new String((char[])authenticationToken.getCredentials());
		if (!"wang".equals(name)){
			throw new UnknownAccountException();
		}
		if (!"123".equals(password)){
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(name,password,getName());
	}
}
