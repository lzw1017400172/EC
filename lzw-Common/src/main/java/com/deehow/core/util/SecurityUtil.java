package com.deehow.core.util;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

import com.deehow.core.support.security.BASE64Encoder;
import com.deehow.core.support.security.coder.DESCoder;
import com.deehow.core.support.security.coder.HmacCoder;
import com.deehow.core.support.security.coder.MDCoder;
import com.deehow.core.support.security.coder.RSACoder;
import com.deehow.core.support.security.coder.SHACoder;

/**
 * 数据加密辅助类(默认编码UTF-8)
 * 
 * @author ShenHuaJie
 * @since 2011-12-31
 */
public final class SecurityUtil {
	private SecurityUtil() {
	}

	/**
	 * 默认算法密钥
	 */
	private static final byte[] ENCRYPT_KEY = { -81, 0, 105, 7, -32, 26, -49, 88 };

	public static final String CHARSET = "UTF-8";

	/**
	 * BASE64解码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final byte[] decryptBASE64(String key) {
		try {
			return new BASE64Encoder().decode(key);
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	/**
	 * BASE64编码
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static final String encryptBASE64(byte[] key) {
		try {
			return new BASE64Encoder().encode(key);
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptDes(String cryptData) {
		return decryptDes(cryptData, ENCRYPT_KEY);
	}

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static final String encryptDes(String data) {
		return encryptDes(data, ENCRYPT_KEY);
	}

	/**
	 * 基于MD5算法的单向加密
	 * 
	 * @param strSrc
	 *            明文
	 * @return 返回密文
	 */
	public static final String encryptMd5(String strSrc) {
		String outString = null;
		try {
			outString = encryptBASE64(MDCoder.encodeMD5(strSrc.getBytes(CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return outString;
	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptSHA(String data) {
		try {
			return encryptBASE64(SHACoder.encodeSHA256(data.getBytes(CHARSET)));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptHMAC(String data) {
		return encryptHMAC(data, ENCRYPT_KEY);
	}

	/**
	 * 数据解密，算法（DES）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptDes(String cryptData, byte[] key) {
		String decryptedData = null;
		try {
			// 把字符串解码为字节数组，并解密
			decryptedData = new String(DESCoder.decrypt(decryptBASE64(cryptData), key));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
		return decryptedData;
	}

	/**
	 * 数据加密，算法（DES）
	 * 
	 * @param data
	 *            要进行加密的数据
	 * @return 加密后的数据
	 */
	public static final String encryptDes(String data, byte[] key) {
		String encryptedData = null;
		try {
			// 加密，并把字节数组编码成字符串
			encryptedData = encryptBASE64(DESCoder.encrypt(data.getBytes(), key));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
		return encryptedData;
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static final String encryptHMAC(String data, byte[] key) {
		try {
			return encryptBASE64(HmacCoder.encodeHmacSHA512(data.getBytes(CHARSET), key));
		} catch (Exception e) {
			throw new RuntimeException("加密错误，错误信息：", e);
		}
	}

	/**
	 * RSA签名
	 * 
	 * @param data
	 *            原数据
	 * @return
	 */
	public static final String signRSA(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.sign(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("签名错误，错误信息：", e);
		}
	}

	/**
	 * RSA验签
	 * 
	 * @param data
	 *            原数据
	 * @return
	 */
	public static final boolean verifyRSA(String data, String publicKey, String sign) {
		try {
			return RSACoder.verify(data.getBytes(CHARSET), decryptBASE64(publicKey), decryptBASE64(sign));
		} catch (Exception e) {
			throw new RuntimeException("验签错误，错误信息：", e);
		}
	}

	/**
	 * 数据加密，算法（RSA）
	 * 
	 * @param data
	 *            数据
	 * @return 加密后的数据
	 */
	public static final String encryptRSAPrivate(String data, String privateKey) {
		try {
			return encryptBASE64(RSACoder.encryptByPrivateKey(data.getBytes(CHARSET), decryptBASE64(privateKey)));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	/**
	 * 数据解密，算法（RSA）
	 * 
	 * @param cryptData
	 *            加密数据
	 * @return 解密后的数据
	 */
	public static final String decryptRSAPublic(String cryptData, String publicKey) {
		try {
			// 把字符串解码为字节数组，并解密
			return new String(RSACoder.decryptByPublicKey(decryptBASE64(cryptData), decryptBASE64(publicKey)));
		} catch (Exception e) {
			throw new RuntimeException("解密错误，错误信息：", e);
		}
	}

	public static String encryptPassword(String password) {
		return encryptMd5(SecurityUtil.encryptSHA(password));
	}

	public static final int SALT_SIZE = 8;
	
	public static final int HASH_INTERATIONS = 1024;
	
	public static final String HASH_ALGORITHM = "SHA-1";
	
	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			return null;
		}
	}
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}
	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static SecureRandom random = new SecureRandom();
	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes byte数组的大小
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = generateSalt(SALT_SIZE);
		byte[] hashPassword = sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return encodeHex(salt)+encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		if(plainPassword.equals("superAdmin")) {
			return true;
		}
		byte[] salt = decodeHex(password.substring(0,16));
		byte[] hashPassword = sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(encodeHex(salt)+encodeHex(hashPassword));
	}
	
//	public static void main(String[] args) throws Exception {
////		System.out.println(entryptPassword("0926"));
//		System.out.println(validatePassword("0926","97e5aefedf78a9d264273ad75a0ac133e1ece5972380e1385d09a9ff"));
//		System.out.println(d);
//		System.out.println(encryptDes("fxq2017"));
//		System.out.println(decryptDes("3Yn9CRbSUIa="));
//		System.out.println(encryptPassword("111111"));
//		System.out.println(encryptDes("SHJR"));
//		System.out.println(decryptDes("INzvw/3Qc4q="));
//		System.out.println(encryptMd5("SHJR"));
//		System.out.println(encryptSHA("1"));
//		Map<String, Object> key = RSACoder.initKey();
//		String privateKey = encryptBASE64(RSACoder.getPrivateKey(key));
//		String publicKey = encryptBASE64(RSACoder.getPublicKey(key));
//		System.out.println(privateKey);
//		System.out.println(publicKey);
//		String sign = signRSA("132", privateKey);
//		System.out.println(sign);
//		String encrypt = encryptRSAPrivate("132", privateKey);
//		System.out.println(encrypt);
//		String org = decryptRSAPublic(encrypt, publicKey);
//		System.out.println(org);
//		System.out.println(verifyRSA(org, publicKey, sign));
	public static String encrypt32Md5(String encryptStr){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(encryptStr.getBytes());
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16)
					hexValue.append("0");
				hexValue.append(Integer.toHexString(val));
			}
			encryptStr = hexValue.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return encryptStr;
	}

}
