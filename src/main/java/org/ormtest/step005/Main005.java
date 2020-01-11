package org.ormtest.step005;

import org.ormtest.step005.entity.User;
import org.ormtest.step005.factory.AbstarctEntityHelper;
import org.ormtest.step005.factory.EntityFactoryHelper;
import org.ormtest.step005.readxml.ReadXML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created Date by 2020/1/11 0051.
 *
 * 从 数据库 抽取 20万数据 查看硬编码速度
 *
 * 使用javassist 来优化 java 反射
 *
 * @author Parker
 */
public class Main005 {


    /**
     * 应用程序主函数
     *
     * @param args 参数数组
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        // 执行
        Main005.start();
    }

    static private void start() throws Exception{

        // 加载 Mysql 驱动
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        // 数据库连接地址
        String dbConnStr = "jdbc:mysql://localhost:3306/demo?user=root&password=123456&characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=Asia/Shanghai";
        // 创建数据库连接
        Connection conn = DriverManager.getConnection(dbConnStr);
        // 简历陈述对象
        Statement stmt = conn.createStatement();

        List<Map<String, String>> xmls = ReadXML.getXml("src/main/resources/Sql.xml");
        for (Map<String, String> xml : xmls) {

            // 模仿myBatis 获取
            String entityName = xml.get("entity");
            String sql = xml.get("sql");

            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> aClass = classLoader.loadClass(entityName);

            // 创建 SQL 查询
            // ormtest 数据库中有个 t_user 数据表,
            // t_user 数据表包括三个字段: user_id、user_name、password,
            // t_user 数据表有 20 万条数据
            // String sql = "select * from t_user limit 200000";

            // 执行查询
            ResultSet rs = stmt.executeQuery(sql);

            // 获取开始时间
            long t0 = System.currentTimeMillis();

            AbstarctEntityHelper helper = EntityFactoryHelper.getEntityHelper(aClass);

            List<User> list = new ArrayList<>();
            while (rs.next()){

                User currUser = (User) helper.create(rs);
                if(null == currUser){
                    return;
                }

                list.add(currUser);
            }

            // 获取结束时间
            long t1 = System.currentTimeMillis();

            // 关闭数据库连接
            stmt.close();
            conn.close();

            System.out.println(list.size() +" ---- "+ list.get(0).toString());
            // 打印实例化花费时间
            System.out.println("实例化花费时间 = " + (t1 - t0) + "ms");
        }
    }

}
