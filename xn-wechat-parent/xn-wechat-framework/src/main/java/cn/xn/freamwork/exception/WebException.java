/*
 * Copyright (c) 2014 xn, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.exception;

/**
 *
 * @author lcl 2014/08/21
 * @version 1.0.0
 */
public class WebException extends Exception {
    private static final long serialVersionUID = 4010986564785322000L;

    public static final String CODE_DEFAULT = "xn.service.exception.code.default";
    public static final String MSG_DEFAULT = "服务层运行异常!";

    public static final String WEB_CODE_DEFAULT = "xn.web.exception.code.default";
    public static final String WEB_MSG_DEFAULT = "WEB层运行异常!";

    private String code;
    private String msg;

    public WebException(Throwable cause) {
        super(null,cause);
    }

    public WebException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public WebException() {
        this(CODE_DEFAULT, MSG_DEFAULT);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
