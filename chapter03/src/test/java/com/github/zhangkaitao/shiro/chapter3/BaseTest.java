package com.github.zhangkaitao.shiro.chapter3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-12-11:16
 */
public abstract class BaseTest {

	@After
	public void tearDown() throws Exception{
		ThreadContext.unbindSubject();//解除Subject绑定到线程
	}

	protected void login(String configFile, String name, String password) throws AuthenticationException {
		//1.获取SecurityManager工厂,通过Ini配置初始化ecurityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
		//2.获取SecurityManager实例,并绑定SecurityUtils
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		//3.得到Subject及创建用户/密码 身份验证
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(name,password);

		subject.login(token);
	}

	//获取Subject
	public Subject subject(){
		return SecurityUtils.getSubject();
	}
}
