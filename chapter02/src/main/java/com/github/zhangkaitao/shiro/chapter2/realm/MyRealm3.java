package com.github.zhangkaitao.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-11-20:37
 */
public class MyRealm3 implements Realm {
	public String getName() {
		return "myRealm3";
	}

	public boolean supports(AuthenticationToken authenticationToken) {
		return authenticationToken instanceof UsernamePasswordToken;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String name = (String)authenticationToken.getPrincipal();
		String password = new String((char[])authenticationToken.getCredentials());

		if (!"zhang".equals(name)){
			throw new UnknownAccountException();
		}
		if (!"123".equals(password)){
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(name+"@123",password,getName());
	}
}
