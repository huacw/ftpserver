package com.sea.ftp.exception.illegal;

import java.lang.IllegalArgumentException;

/**
 * 无效端口异常
 *
 * @author sea
 * @Date 2018/8/17 16:08
 * @Version 1.0
 */
public class IllegalPortException extends IllegalArgumentException {
    private static final long serialVersionUID = -7771719692741419931L;

    public IllegalPortException() {
        super();
    }

    public IllegalPortException(String s) {
        super(s);
    }
}
