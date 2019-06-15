package com.github.zhangkaitao.shiro.chapter6.realm;

import com.github.zhangkaitao.shiro.chapter6.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-14-23:42
 */
public class UserRealmTest extends BaseTest {

	@Test
	public void testLoginSuccess(){
		login("classpath:shiro.ini",user1.getUsername(), password);
 	}
}
