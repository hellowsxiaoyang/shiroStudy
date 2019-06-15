package com.github.zhangkaitao.shiro.chapter5.test;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Test;

import java.security.Key;
import java.util.Arrays;

/**
 * @author 杨郑耀
 * @description
 * @create 2019-06-12-17:20
 */
public class EncryptionDemos {

	//Base64 编码/解码
	@Test
	public void testBase64() {
		String password = "123456";
		String base64Encode = Base64.encodeToString(password.getBytes()); //加密
		System.out.println("base64Encode: " + base64Encode);
		String baseDecode = Base64.decodeToString(base64Encode.getBytes());
		System.out.println("baseDecode: " + baseDecode);
	}

	//十六进制 加密/解密
	@Test
	public void testHex() {
		String password = "123456";
		String hexEncode = Hex.encodeToString(password.getBytes());
		System.out.println("hexEncode: " + hexEncode);
		String hexDecode = Hex.encodeToString(hexEncode.getBytes());
		System.out.println("hexDecode: " + hexDecode);
	}

	//散列算法 不可逆算法

	//MD5 散列算法
	@Test
	public void testHashAlgorithm() {
		String password = "123456";
		String salt = "123";
		Md5Hash md5Hash = new Md5Hash(password, salt);
		String md5Password = md5Hash.toString();
		//	md5Hash.toBase64();
//		md5Hash.toHex();
		System.out.println("md5Password: " + md5Password);
	}

	//SHA 散列算法
	@Test
	public void testSHAAlgorithm() {
		String password = "123456";
		String salt = "123";
		Sha256Hash sha256Hash = new Sha256Hash(password, salt);
		String shaPassword = sha256Hash.toString();
		System.out.println("shaPassword： " + shaPassword);
	}

	//通用散列支持
	@Test
	public void testSimpleHash() {
		String password = "123456";
		String salt = "123";
		SimpleHash md5 = new SimpleHash("MD5", password, salt);
		String md5Password = md5.toString();
		System.out.println("md5Password: " + md5Password);

		md5 = new SimpleHash("SHA-256", password, salt);
		String shaPassword = md5.toString();
		System.out.println("shaPassword: " + shaPassword);//92650041f7b179e5d0bd54fd82e27414e2a20d9d458ef35d592eff9f1427e1b8

		//这种写法等于 SimpleHash simpleHash = new SimpleHash("SHA-256",password,salt);
		SimpleHash simpleHash = new SimpleHash("SHA-256");
		simpleHash.setBytes(md5.getBytes());
		simpleHash.setSalt(ByteSource.Util.bytes(salt));
		String shaPassword2 = simpleHash.toString();
		System.out.println("shaPassword2: " + shaPassword2);//92650041f7b179e5d0bd54fd82e27414e2a20d9d458ef35d592eff9f1427e1b8
	}

	//HashService及其实现DefaultHashService
	@Test
	public void testDefaultHashService() {
		String password = "123456";

		DefaultHashService defaultHashService = new DefaultHashService();//默认是SHA-512
		defaultHashService.setHashAlgorithmName("SHA-512");
		defaultHashService.setPrivateSalt(new SimpleByteSource("123"));//私盐 默认没有
		defaultHashService.setGeneratePublicSalt(true);//用户没有传入公盐时 是否生成公盐
		defaultHashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐

		defaultHashService.setHashIterations(1);//默认就一次

		HashRequest hashRequest = new HashRequest.Builder().setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(password))
				.setSalt(ByteSource.Util.bytes("123")).setIterations(1).build();

		Hash hash = defaultHashService.computeHash(hashRequest);

		String hashPassword = hash.toHex();
		System.out.println(hashPassword);
	}

	//SecureRandomNumberGenerator用于生成一个随机数：
	@Test
	public void getRandomNumber() {
		SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
		//secureRandomNumberGenerator.setSeed("123".getBytes());
		System.out.println(secureRandomNumberGenerator.nextBytes().toHex());
	}

	//对称加密 shiro提供对对称加密的支持，如AES Blowfish
	@Test
	public void testAes() {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); //设置key的长度  128, 192 , 256

		Key key = aesCipherService.generateNewKey(); //生成key

		String password = "123455";
		//加密
		String aesEncodePassword = aesCipherService.encrypt(password.getBytes(), key.getEncoded()).toHex();
		System.out.println("aesEncodePassword: " + aesEncodePassword);

		/*解密*/
		ByteSource decrypt = aesCipherService.decrypt(Hex.decode(aesEncodePassword), key.getEncoded());
		String aesDecodePassword = new String(decrypt.getBytes());
		System.out.println("aesDecodePassword: " + aesDecodePassword);
	}

	@Test
	public void testBlowFish() {
		BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
		blowfishCipherService.setKeySize(128);

		Key key = blowfishCipherService.generateNewKey();

		String password = "123455";

		//加密
		ByteSource encrypt = blowfishCipherService.encrypt(password.getBytes(), key.getEncoded());
		String blowfishEncodePassword = encrypt.toHex();
		System.out.println("blowfishEncodePassword: " + blowfishEncodePassword);

		//解密
		ByteSource decrypt = blowfishCipherService.decrypt(Hex.decode(blowfishEncodePassword), key.getEncoded());
		String blowfishDecode = new String(decrypt.getBytes());
		System.out.println("blowfishDecode: " + blowfishDecode);
		short s1 = 1;
	}

	//CodecSupport
	@Test
	public void testCodeSupport(){
		String str = "hello";
		byte[] bytes = CodecSupport.toBytes(str);
		System.out.println(Arrays.toString(bytes));
		String s = CodecSupport.toString(bytes);
		System.out.println(s);

		byte[] bytes1 = str.getBytes();
		System.out.println(Arrays.toString(bytes1));
	}
}
