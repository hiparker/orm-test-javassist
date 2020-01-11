package org.ormtest.step003.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ormtest.step003.annotation.Column;
import org.ormtest.step003.entity.User;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;

/**
 * Created Date by 2020/1/11 0011.
 *
 * UserEntity 帮助类
 *
 * 实体助手类, 这个更通用
 *
 * @author Parker
 */
@Slf4j
public final class UserEntityHelper {

    static private final String GET = "get";
    static private final String SET = "set";


    /**
     * 私有化构造函数
     */
    private UserEntityHelper(){

    }


    /**
     * 将数据集装换为实体对象
     * 反射来实现
     **/
    static public User create(ResultSet rs) throws Exception{
        // 非法判断
        if(null == rs){
            return null;
        }

        User currUser = new User();

        Class<?> clazz = currUser.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            // 非法
            if(null == field){
                continue;
            }

            // 获取列名称
            Column annotation = field.getAnnotation(Column.class);
            String columnName = annotation.name();

            // 非法
            if(StringUtils.isEmpty(columnName)){
                continue;
            }

            // 获得 set
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            //获得写方法
            Method writeMethod = pd.getWriteMethod();


            if(field.getType().getName().equals(Double.class.getName())){
                double aDouble = rs.getDouble(columnName);
                // 执行
                writeMethod.invoke(currUser, aDouble);
            }else if(field.getType().getName().equals(Float.class.getName())){
                float aFloat = rs.getFloat(columnName);
                // 执行
                writeMethod.invoke(currUser, aFloat);
            }else if(field.getType().getName().equals(Byte.class.getName())){
                byte aByte = rs.getByte(columnName);
                // 执行
                writeMethod.invoke(currUser, aByte);
            }else if(field.getType().getName().equals(Short.class.getName())){
                short aShort = rs.getShort(columnName);
                // 执行
                writeMethod.invoke(currUser, aShort);
            }if(field.getType().getName().equals(Integer.class.getName())){
                int anInt = rs.getInt(columnName);
                // 执行
                writeMethod.invoke(currUser, anInt);
            }else if(field.getType().getName().equals(Long.class.getName())){
                long aLong = rs.getLong(columnName);
                // 执行
                writeMethod.invoke(currUser, aLong);
            }else if(field.getType().getName().equals(BigDecimal.class.getName())){
                BigDecimal bd = rs.getBigDecimal(columnName);
                // 执行
                writeMethod.invoke(currUser, bd);
            }else if(field.getType().getName().equals(String.class.getName())){
                String str = rs.getString(columnName);
                // 执行
                writeMethod.invoke(currUser, str);
            }else{
                log.error("无法识别的类型，type={}",field.getType());
            }

        }

        return currUser;
    }


}
