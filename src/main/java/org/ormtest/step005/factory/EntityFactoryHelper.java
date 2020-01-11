package org.ormtest.step005.factory;

import javassist.*;
import lombok.extern.slf4j.Slf4j;
import org.ormtest.step005.annotation.Column;
import org.ormtest.step005.entity.User;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created Date by 2020/1/11 0011.
 *
 * 助手类工厂
 *
 * @author Parker
 */
@Slf4j
public final class EntityFactoryHelper {

    /**
     * entityhelper 字典
     */
    static private final Map<Class<?>,AbstarctEntityHelper> _entityHelperMap = new HashMap<>();

    /**
     * 私有化构造函数
     */
    private EntityFactoryHelper(){}

    /**
     *
     * @param entityClazz
     * @return
     */
    static public AbstarctEntityHelper getEntityHelper(Class<?> entityClazz) throws Exception{
        // 这里需要全新设计,
        // 接下来就该请出 javassist 了!

        if(null == entityClazz){
            return null;
        }

        AbstarctEntityHelper helper = _entityHelperMap.get(entityClazz);

        // 如果字典map中 存在 则直接返回对象 不需要二次创建 避免性能过度损耗
        if(null != helper){
            return helper;
        }

        // 使用 Javassist 动态生成 Java 字节码
        ///////////////////////////////////////////////////////////////////////
        ClassPool clazzPool = ClassPool.getDefault();

        // 添加class 系统路径
        clazzPool.appendSystemPath();

        // 导包
        // import org.ormtest.step005.ResultSet
        // import org.ormtest.step005.entity.User
        clazzPool.importPackage(ResultSet.class.getName());
        clazzPool.importPackage(entityClazz.getName());

        // 抽象助手类
        CtClass abstractEntityHelper = clazzPool.getCtClass(AbstarctEntityHelper.class.getName());
        // 助手类名称
        final String entityHelperName = entityClazz.getName()+"_Helper";
        // 构建助手类
        CtClass helperClazz = clazzPool.makeClass(entityHelperName, abstractEntityHelper);

        // 添加构造函数
        CtConstructor constructor = new CtConstructor(new CtClass[0],helperClazz);
        // 空的方法体
        constructor.setBody("{}");
        helperClazz.addConstructor(constructor);

        // 创建方法体 ---------------
        final StringBuilder sb = new StringBuilder();

        sb.append("public Object create( ").append("java.sql.ResultSet rs").append(")").append(" throws Exception ").append("{\n");

        sb.append("if( null == rs) { return null; }");

        // 创建对象
        sb.append(entityClazz.getName())
                .append(" obj = ")
                .append("new ")
                .append(entityClazz.getName())
                .append("();\n");

        Field[] fields = entityClazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];

            Column annotation = field.getAnnotation(Column.class);

            // 非法判断
            if(null == annotation){
                continue;
            }

            // 数据库字段名称
            String columnName = annotation.name();

            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),entityClazz);

            // 获得写入方法
            Method writeMethod = pd.getWriteMethod();

            if(field.getType().getName().equals(Double.class.getName())){
                sb.append("Double param").append(i).append(" = new Double(rs.getDouble(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(Float.class.getName())){
                sb.append("Float param").append(i).append(" = new Float(rs.getFloat(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(Byte.class.getName())){
                sb.append("Byte param").append(i).append(" = new Byte(rs.getByte(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(Short.class.getName())){
                sb.append("Short param").append(i).append(" = new Short(rs.getShort(\"").append(columnName).append("\"));");
            }if(field.getType().getName().equals(Integer.class.getName())){
                sb.append("Integer param").append(i).append(" = new Integer(rs.getInt(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(Long.class.getName())){
                sb.append("Long param").append(i).append(" = new Long(rs.getLong(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(BigDecimal.class.getName())){
                sb.append("BigDecimal param").append(i).append(" = new BigDecimal(rs.getBigDecimal(\"").append(columnName).append("\"));");
            }else if(field.getType().getName().equals(String.class.getName())){
                sb.append("String param").append(i).append(" = new String(rs.getString(\"").append(columnName).append("\"));");
            }else{
                //log.error("无法识别的类型，type={}",field.getType());
                continue;
            }

            sb.append("obj.").append(writeMethod.getName()).append("(").append("param").append(i).append(");");
        }

        sb.append("return obj;\n");
        sb.append("}\n");

        // 解析方法
        CtMethod makeMethod = CtNewMethod.make(sb.toString(), helperClazz);
        // 添加方法到clazz中
        helperClazz.addMethod(makeMethod);

        // 生成文件 测试查看使用
        // helperClazz.writeFile("E:/personal/practive/workspace/orm-test-javassist/src/main/java/org/ormtest/temp");

        // 获取 JAVA 类
        Class<?> javaClazz = helperClazz.toClass();

        // 创建帮助对象实例
        ///////////////////////////////////////////////////////////////////////
        helper = (AbstarctEntityHelper) javaClazz.newInstance();

        // 将实例对象 添加至 map 字典中
        _entityHelperMap.put(javaClazz,helper);

        return helper;
    }
}
