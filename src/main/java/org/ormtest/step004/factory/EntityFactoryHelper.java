package org.ormtest.step004.factory;

import java.sql.ResultSet;

/**
 * Created Date by 2020/1/11 0011.
 *
 * 助手类工厂
 *
 * @author Parker
 */
public final class EntityFactoryHelper {

    /**
     * 私有化构造函数
     */
    private EntityFactoryHelper(){}

    /**
     *
     * @param entityClazz
     * @return
     */
    static public AbstarctEntityHelper getEntityHelper(Class<?> entityClazz){
        // 这里需要全新设计,
        // 接下来就该请出 javassist 了!
        return null;
    }
}
