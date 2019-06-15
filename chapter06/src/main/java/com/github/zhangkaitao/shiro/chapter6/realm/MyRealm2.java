package com.github.zhangkaitao.shiro.chapter6.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-21:52
 */
public class MyRealm2 implements Realm {
	public String getName() {
		return "b"; //realm name 为 “b”
	}

	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		return new SimpleAuthenticationInfo(
				"zhang", //身份 字符串类型
				"123",   //凭据
				getName() //Realm Name
		);
	}
}
