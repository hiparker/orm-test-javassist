package org.ormtest.step005.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created Date by 2020/1/11 0011.
 *
 * 字段注解类 模仿jpa
 * @author Parker
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * 数据字段名称
     * @return
     */
    String name();

}
