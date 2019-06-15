package com.github.zhangkaitao.shiro.chapter2;

import junit.framework.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-11-14:40
 */
public class LoginLogoutTest {

	public Subject getSubject(String resourcePath){
		//1.获取SecurityManager工厂
		Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory(resourcePath);
		//2.通过SecurityManager工厂获取SecurityManager
		SecurityManager securityManager = securityManagerFactory.getInstance();
		//3.将SecurityManager绑定到SecurityUtils
		SecurityUtils.setSecurityManager(securityManager);
		//4.利用SecurityUtils获取Subject
		 Subject subject = SecurityUtils.getSubject();
		 return subject;
	}

	@Test
	public void testHelloWord(){
		/*//1.获取SecurityManager工厂com/github/zhangkaitao/shiro/chapter2/shiro.ini
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//2.通过SecurityManager工厂获取SecurityManager
		SecurityManager securityManager = factory.getInstance();
		//3.将SecurityManager绑定到SecurityUtil
		SecurityUtils.setSecurityManager(securityManager);
		//4.通过SecurityUtil获取Subject
		Subject subject = SecurityUtils.getSubject();*/
		Subject subject = getSubject("classpath:shiro.ini");
		//5.创建Token
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		//6.利用Subject的login方法验证
		try {
			//登录，即身份验证
			subject.login(token);
			System.out.println("验证通过,登录成功");
		} catch (AuthenticationException e) {
			//身份验证失败
			System.out.println("验证失败");
			System.out.println(e.getClass().getName());
			e.printStackTrace();
			//密码错误报 IncorrectCredentialsException异常
		}
		Assert.assertEquals(true,subject.isAuthenticated());//断言是否登录
		//7.退出
		subject.logout();
	}

	//测试自定义realm
	@Test
	public void testMyRealm(){
		Subject subject = getSubject("classpath:shiro-realm.ini");
		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
		try {
			subject.login(token);
			System.out.println("验证成功,登录");
		} catch (AuthenticationException e) {
			System.out.println("验证失败");
			e.printStackTrace();
		}
		subject.logout();
	}

	//测试多个realm
	@Test
	public void testMultiRealms(){
		Subject subject = getSubject("classpath:shiro-multi-realm.ini");
		UsernamePasswordToken token = new UsernamePasswordToken("wang","123");

		try {
			subject.login(token);
			System.out.println("验证成功,登录");
		} catch (AuthenticationException e) {
			e.printStackTrace();
			System.out.println("验证失败");
		}
		subject.logout();
	}

	//测试JdbcRealm
	@Test
	public void testJdbcRealm(){
		//1.获取SecurityManager工厂
		Factory<SecurityManager> managerFactory = new IniSecurityManagerFactory("classpath:shiro-jdbc.ini");
		//2.通过工厂获取SecurityManager
		SecurityManager instance = managerFactory.getInstance();
		//3.将SecurityManager绑定到SecurityUtils
		SecurityUtils.setSecurityManager(instance);
		//4.通过SecurityUtils获取Subject
		Subject subject = SecurityUtils.getSubject();

		//通过用户名、密码注册Token
		UsernamePasswordToken token = new UsernamePasswordToken("zhang2","123");

		//验证
		try {
			subject.login(token);
			System.out.println("验证成功,登录");
		} catch (AuthenticationException e) {
			System.out.println(e.getClass().getName());
			System.out.println("验证失败");
		}
	}

	//测试AllSuccessfulStrategy认证策略 成功案例
	@Test
	public void testAllSuccessfulStrategy(){
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-all-successful.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

		try {
			subject.login(token);
			System.out.println("验证成功");

			//返回所有的Principal
			PrincipalCollection collection = subject.getPrincipals();
			System.out.println(collection);

			//返回第一个Principal
			String usename = (String)subject.getPrincipal();
			System.out.println(usename);

			//返回PrincipalCollection中第一个Principal
			String primaryUsername = (String)collection.getPrimaryPrincipal();
			System.out.println(primaryUsername);

		} catch (AuthenticationException e) {
			System.out.println("验证失败");
			System.out.println(e.getClass().getName());
		}
	}

	//测试AllSuccessfulStrategy认证策略 失败案例
	@Test
	public void testAllSuccessfulStrategyFailed(){
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-all-failed.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

		try {
			subject.login(token);
			System.out.println("验证成功");
			PrincipalCollection collection = subject.getPrincipals();
			System.out.println(collection);
		} catch (AuthenticationException e) {
			System.out.println("验证失败");
			System.out.println(e.getClass().getName());
		}

	}

	//测试AtLeastOneSuccessfulStrategy验证策略 成功案例
	@Test
	public void testAtLeastOneSuccessfulStrategy(){
		Subject subject = getSubject("classpath:shiro-atLeastOneSuccessful.ini");

		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

		try {
			subject.login(token);
			//返回所有成功的Realm认证信息
			System.out.println("验证成功:"+subject.getPrincipals());
		} catch (AuthenticationException e) {
			System.out.println("验证失败："+e.getClass().getName());
		}

	}

	//测试AtLeastSuccessfulStrategy策略 成功
	@Test
	public void testAtLeastSuccessfulStrategy(){
		Subject subject = getSubject("classpath:shiro-firstSuccessful.ini");

		UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");

		try {
			subject.login(token);
			//有一个Realm认证通过就返回true  返回第一个认证成功Realm的认证信息
			System.out.println("验证成功:"+subject.getPrincipals());
		} catch (AuthenticationException e) {
			System.out.println("验证失败："+e.getClass().getName());
		}
	}
}
