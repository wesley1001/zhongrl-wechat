/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.model.command;

/**
 * 用户查询类
 *
 * @author lcl 2015/3/18.
 * @version 1.0.0
 */
public class UserSearchCommand extends SearchCommand {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名字
     */
    private String userName;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
