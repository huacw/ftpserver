package com.sea.ftp.util;

import org.apache.commons.codec.digest.DigestUtils;

import com.sea.ftp.server.impl.config.xml.enumeration.EncryptedStrategyType;

/**
 * 
 * 加密工具类
 *
 * @author sea
 */
public class CiphertextUtils {

	/**
	 * 加密字符串
	 * 
	 * @param sourceStr
	 *            需要加密目标字符串
	 * @param algorithms
	 *            算法(如:MD2,MD5,SHA1,SHA256,SHA384,SHA512)
	 * @return
	 */
	public static String passAlgorithmsCiphering(String sourceStr,
			EncryptedStrategyType algorithms) {
		String password = "";
		switch (algorithms) {
			case MD2:
				password = DigestUtils.md2Hex(sourceStr);
				break;
			case MD5:
				password = DigestUtils.md5Hex(sourceStr);
				break;
			case SHA1:
				password = DigestUtils.sha1Hex(sourceStr);
				break;
			case SHA256:
				password = DigestUtils.sha256Hex(sourceStr);
				break;
			case SHA384:
				password = DigestUtils.sha384Hex(sourceStr);
				break;
			case SHA512:
				password = DigestUtils.sha512Hex(sourceStr);
				break;
			default:
				break;
		}
		return password;
	}
}
