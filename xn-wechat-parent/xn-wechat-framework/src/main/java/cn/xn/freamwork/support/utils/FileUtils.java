/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.support.utils;

import java.io.File;

/**
 * 文件操作工具类
 *
 * @author lcl 2015/1/4.
 * @version 1.0.0
 */
public class FileUtils {

    /**
     * 创建不存在的目录
     * @param dirs
     */
    public static void createDir(File[] dirs) {
        //不存在创建目录
        for (File dir:dirs) {
            if (!dir.exists())
                dir.mkdir();
        }
    }

    public static void createDir(File dir) {
        //不存在创建目录
        if (!dir.exists())
            dir.mkdir();

    }

}
