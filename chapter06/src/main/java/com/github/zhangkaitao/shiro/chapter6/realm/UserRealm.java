package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.entity.User;
import com.github.zhangkaitao.shiro.chapter6.service.UserService;
import com.github.zhangkaitao.shiro.chapter6.service.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-17:46
 */
public class UserRealm extends AuthorizingRealm {

	private UserService userService = new UserServiceImpl();

	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String)principalCollection.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.findRoles(username));
		authorizationInfo.setStringPermissions(userService.findPermissions(username));

		return authorizationInfo;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();

		User user = userService.findByUsername(username);
		if (user == null){
			throw new UnknownAccountException();//没有找到账号
		}
		if (Boolean.TRUE.equals(user.getLocked())){
			throw new LockedAccountException();//账号锁定
		}

		//将AuthenticationInfo交给AuthenticationRealm使用CredentialsMathcer进行密码匹配
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				user.getUsername(),
				user.getPassword(),
				ByteSource.Util.bytes(user.getCredentialsSalt()),
				getName()
		);
		return simpleAuthenticationInfo;
	}
}
