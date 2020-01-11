package org.ormtest.step004.factory;

import java.sql.ResultSet;

/**
 * Created Date by 2020/1/11 0011.
 *
 * 抽象 实体助手
 * @author Parker
 */
public abstract class AbstarctEntityHelper {

    /**
     * 抽象 创建实体
     * @param rs
     * @return
     * @throws Exception
     */
    abstract public Object create(ResultSet rs) throws Exception;

}
