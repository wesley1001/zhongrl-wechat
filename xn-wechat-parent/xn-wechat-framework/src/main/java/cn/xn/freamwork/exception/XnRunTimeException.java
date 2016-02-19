/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
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
public class XnRunTimeException extends RuntimeException
{
    private static final long serialVersionUID = -3952126728658060998L;

    private String code;
    private String msg;

    public XnRunTimeException(Throwable cause) {
        super(cause);
    }

    public XnRunTimeException() {
        this(XnException.CODE_DEFAULT, XnException.MSG_DEFAULT);
    }

    public XnRunTimeException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public XnRunTimeException(String message, String code, String msg) {
        super(message);
        this.code = code;
        this.msg = msg;
    }

    public XnRunTimeException(String message, Throwable cause, String code, String msg) {
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }

    public XnRunTimeException(Throwable cause, String code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }

    public XnRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.msg = msg;
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
