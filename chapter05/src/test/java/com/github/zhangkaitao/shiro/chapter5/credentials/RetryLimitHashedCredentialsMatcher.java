package com.github.zhangkaitao.shiro.chapter5.credentials;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-9:26
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private Ehcache passwordRetryEhcache;

	public RetryLimitHashedCredentialsMatcher(){
		CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
		passwordRetryEhcache = cacheManager.getCache("passwordRetryEhcache");
	}
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		//1.通过令牌获取用户名
		String username = (String)token.getPrincipal();
		//2.判断缓存中是否包含含有此用户名的Element
		Element element = passwordRetryEhcache.get(username);
		//3.如果没有 ,就创建包含该用户名的Element并添加到缓存
		if (element == null){
			element = new Element(username,new AtomicInteger(0));
			passwordRetryEhcache.put(element);
		}
		//4.如果有 就通过该Element获取AutomicInteger
		AtomicInteger atomicInteger = (AtomicInteger)element.getObjectValue();
		//5.如果AutomicInteger的incrementAndGet大于5 就抛异常
		if (atomicInteger.incrementAndGet() > 5){
			throw new ExcessiveAttemptsException();
		}
		//6.如果小于5 就调用父类的doCredentialsMatch方法进行密码验证
		boolean matchs = super.doCredentialsMatch(token, info);
		//7.如果验证通过，就将该username从缓存中清除
		if (matchs){
			passwordRetryEhcache.remove(username);
		}
		return matchs;
	}
}
