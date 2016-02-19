/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.exception;

/**
 * 账户变更全局异常类
 *
 * @author lcl 2014/08/21
 * @version 1.0.0
 */
public class AccountServiceException extends XnRunTimeException
{

    private static final long serialVersionUID = -2907779630670587161L;

    public static final String CODE_DEFAULT = "xn.account.service.exception";
    public static final String MSG_DEFAULT = "资金账户变更异常!";

    public AccountServiceException() {
        super(CODE_DEFAULT, MSG_DEFAULT);
    }

    public AccountServiceException(String msg) {
        super(CODE_DEFAULT, msg);
    }

    public AccountServiceException(Throwable cause, String msg) {
        super(cause, XnException.CODE_DEFAULT, msg);
    }
}
