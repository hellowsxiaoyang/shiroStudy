package com.github.zhangkaitao.shiro.chapter3;

import org.apache.shiro.authc.AuthenticationException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-12-11:16
 */
public class AuthorizerTest extends BaseTest {

	@Test
	public void testAuthorizer(){
		try {
			login("classpath:shiro-authorizer.ini","zhang","123");
			System.out.println(subject().getPrincipals());
			//用户zhang 没有user:update 会报错
			//Assert.assertTrue(subject().isPermitted("user:update"));

			//断言用户zhang是否有user2:create权限  成功
			Assert.assertTrue(subject().isPermitted("user2:create"));
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

	}

	//jdbcRealm测试
	@Test
	public void testJdbcRealm(){
		try {
			login("classpath:shiro-jdbc.ini","zhang","123");
			//System.out.println(subject().getPrincipals());
			//Assert.assertTrue(subject().isPermitted("user1:create"));
			System.out.println(subject().isPermitted("user1:create"));
			System.out.println(subject().isPermitted("user2:*"));
			System.out.println(subject().isPermitted("menu:*"));
		} catch (AuthenticationException e) {
			System.out.println(e.getClass().getName());
		}
	}
}
