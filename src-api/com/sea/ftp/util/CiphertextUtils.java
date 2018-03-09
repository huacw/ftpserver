package com.sea.ftp.util;

import org.apache.commons.codec.digest.DigestUtils;

import com.sea.ftp.enumeration.EncryptedStrategyType;
import com.sea.ftp.exception.FTPServerRuntimeException;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.i18n.LocalizedMessageResource;

/**
 * 
 * 加密工具类
 *
 * @author sea
 */
public class CiphertextUtils {
    private static LocalizedMessageResource lmr = LocalizedMessageResource.newInstance();

    private CiphertextUtils() {}

    /**
     * 加密字符串
     * 
     * @param sourceStr 需要加密目标字符串
     * @param algorithms 算法(如:MD2,MD5,SHA1,SHA256,SHA384,SHA512)
     * @return
     */
    public static String passAlgorithmsCiphering(String sourceStr, EncryptedStrategyType algorithms) {
        switch (algorithms) {
            case MD2:
                return DigestUtils.md2Hex(sourceStr);
            case MD5:
                return DigestUtils.md5Hex(sourceStr);
            case SHA1:
                return DigestUtils.sha1Hex(sourceStr);
            case SHA256:
                return DigestUtils.sha256Hex(sourceStr);
            case SHA384:
                return DigestUtils.sha384Hex(sourceStr);
            case SHA512:
                return DigestUtils.sha512Hex(sourceStr);
            case none:
                return sourceStr;
            default:
                MessageCode code = MessageCode.newMessageCode(MessageType.Error);
                code.setMsgKey("illegal.encryption.error");
                throw new FTPServerRuntimeException(lmr.getMessage(code, algorithms.name()));
        }
    }
}
