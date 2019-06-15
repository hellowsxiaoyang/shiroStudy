package com.github.zhangkaitao.shiro.chapter5.test;

import com.sun.javafx.css.converters.EnumConverter;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.junit.Test;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-13-15:57
 */
public class PasswordTest extends BaseTest{

	//测试PasswordServcie及PasswordMatcher
	@Test
	public void testPasswordServiceWithMyRealm(){
		login("classpath:shiro-passwordservice.ini","zhang","123");
	}

	@Test
	public void testPasswordServcieWithJdbcRealm(){
		login("classpath:shiro-jdbc-passwordservice.ini", "wu", "123");
	}

	//测试HashCredentialsMatcher
	@Test
	public void testHashCredentialsMatcherWithMyRealm2(){
		login("classpath:shiro-hashedCredentialsMatcher.ini", "liu", "123");
	}

	@Test
	public void testHashedCredentialsMatcherWithJdbcRealm() {

		//此处还要注意Shiro默认使用了apache commons BeanUtils，默认是不进行Enum类型转型的，此时需要自己注册一个Enum转换器
		BeanUtilsBean.getInstance().getConvertUtils().register(new EnumConverter(), JdbcRealm.SaltStyle.class);

		//使用testGeneratePassword生成的散列密码
		login("classpath:shiro-jdbc-hashedCredentialsMatcher.ini", "liu", "123");
	}

	private class EnumConverter extends AbstractConverter {
		@Override
		protected String convertToString(final Object value) throws Throwable {
			return ((Enum) value).name();
		}
		@Override
		protected Object convertToType(final Class type, final Object value) throws Throwable {
			return Enum.valueOf(type, value.toString());
		}

		@Override
		protected Class getDefaultType() {
			return null;
		}

	}

	//利用HashedCredentialsMatcher 防止密码被暴力破解  密码验证失败5次 就禁用
	@Test(expected = ExcessiveAttemptsException.class)
	public void testRetryLimitHashCredentialsMatcherWithMyRealm(){
		//模拟5次验证失败
		for (int i = 1; i <= 5; i++){
			try {
				// 模拟密码错误 所以每次都是IncorrectCredentialsException
				login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini","liu","234");
			} catch (AuthenticationException e) {
				e.printStackTrace();
			}
		}
		//失败5次后再次验证 即使密码正确 也会报错ExcessiveAttemptsException
		login("classpath:shiro-retryLimitHashedCredentialsMatcher.ini","liu","123");
	}
}
