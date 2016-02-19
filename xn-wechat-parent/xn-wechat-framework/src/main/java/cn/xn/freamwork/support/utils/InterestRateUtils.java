/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package cn.xn.freamwork.support.utils;

import java.math.BigDecimal;

/**
 * 利息计算工具类
 *
 * @author lcl 2014/11/27.
 * @version 1.0.0
 */
public class InterestRateUtils {

    /**
     * 一次性返息计算
     *
     * @param investMoney
     * @param interest
     * @param investDate
     * @return
     */
    public static BigDecimal oncePaybackInterest(BigDecimal investMoney,
                                                 Double interest, Integer investDate) {
        if (null == investDate)
            return new BigDecimal(0);

        BigDecimal cInterest = (BigDecimalUtil.divide(
                BigDecimalUtil.multiply(BigDecimalUtil.multiply(investMoney, (interest / 100)), investDate)
                , 365)
        );
        return cInterest.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 充值手续费 (0.2%)
     * @param inMoney
     * @return
     */
    public static BigDecimal rechargeFactorage(BigDecimal inMoney) {

        return BigDecimalUtil.multiply(inMoney, (0.2f / 100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 居间服务费
     * @param interest
     * @return
     */
    public static BigDecimal serveFee(BigDecimal interest) {

        return BigDecimalUtil.multiply(interest, (10f / 100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 推荐奖励
     * @param interest
     * @return
     */
    public static BigDecimal award(BigDecimal interest) {
        return BigDecimalUtil.multiply(interest, (15f / 100)).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public static void main(String[] args) {

//        System.out.println(oncePaybackInterest(new BigDecimal(1000), 18d, 60));

//        System.out.println(rechargeFactorage(new BigDecimal(1000.00)));

        //System.out.println(serveFee(new BigDecimal(1000)));

        //计算收费
        BigDecimal result = oncePaybackInterest(new BigDecimal(10000), 14d, 1);
        BigDecimal serveFee = serveFee(result);
        System.out.println(result +" - "+ serveFee +" = "+ BigDecimalUtil.subtract(result, serveFee));


    }

}
