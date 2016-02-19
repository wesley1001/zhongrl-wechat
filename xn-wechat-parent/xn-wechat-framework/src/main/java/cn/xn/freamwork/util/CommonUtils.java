package cn.xn.freamwork.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjianwei on 14-9-14.
 */
public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    public static List copyList(List<? extends Object> poList, Class voClass) throws InvocationTargetException, IllegalAccessException {

        List voList = new ArrayList();

        Object voObj = null;
        for (Object poObj : poList) {
            try {
                voObj = voClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("", e);
            }
            BeanUtils.copyProperties(poObj,voObj);
            voList.add(voObj);
        }
        return voList;

    }

}
