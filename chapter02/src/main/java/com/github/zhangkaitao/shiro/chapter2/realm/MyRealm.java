package com.github.zhangkaitao.shiro.chapter2.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author 杨郑耀
 * @description 自定义realm
 * @create 2019-06-11-15:27
 */
public class MyRealm implements Realm {

	//返回realm名称
	public String getName() {
		return "myRealm"; //必填 不然会报错 （可以不和配置文件中的自定义realm名字一致）
	}

	public boolean supports(AuthenticationToken authenticationToken) {
		//只支持UsernamePasswordToken类型的Token
		return authenticationToken instanceof UsernamePasswordToken;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String name = (String) authenticationToken.getPrincipal(); //通过令牌获取用户名
		//通过令牌获取密码 先将authenticationToken.getCredentials()强转为char类型数组 在通过char类型数组获取String
		String password = new String((char[])authenticationToken.getCredentials());

		if (!"zhang".equals(name)){
			throw new UnknownAccountException(); //用户名验证失败
		}

		if (!"123".equals(password)){
			throw new IncorrectCredentialsException(); //密码验证失败
		}
		//如果验证成功,返回一个AuthenticationInfo
		return new SimpleAuthenticationInfo(name,password,getName());
	}
}
