package com.github.zhangkaitao.shiro.chapter6.credentials;

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
 * @create 2019-06-14-22:09
 */
public class RetryLimitHashCredentialsMatcher extends HashedCredentialsMatcher {

	private Ehcache passwordEhcache;

	public RetryLimitHashCredentialsMatcher() {
		CacheManager cacheManager= CacheManager.newInstance(CacheManager.class.getClassLoader().getResource("ehcache.xml"));
		passwordEhcache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();

		Element element = passwordEhcache.get(username);
		if (element == null){
			element = new Element(username,new AtomicInteger(0));
			passwordEhcache.put(element);
		}

		AtomicInteger atomicInteger= (AtomicInteger)element.getObjectValue();
		if (atomicInteger.incrementAndGet() > 5){
			throw new ExcessiveAttemptsException();
		}

		boolean match = super.doCredentialsMatch(token, info);
		if (match){
			passwordEhcache.remove(username);
		}

		return match;
	}
}
