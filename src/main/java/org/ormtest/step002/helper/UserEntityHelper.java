package org.ormtest.step002.helper;

import org.ormtest.step002.entity.User;

import java.sql.ResultSet;

/**
 * Created Date by 2020/1/11 0011.
 *
 * UserEntity 帮助类
 * @author Parker
 */
public final class UserEntityHelper {

    /**
     * 私有化构造函数
     */
    private UserEntityHelper(){

    }

    static public User create(ResultSet rs) throws Exception{
        // 非法判断
        if(null == rs){
            return null;
        }

        // 创建User 对象
        User currUser = new User();
        currUser.setUserId(rs.getInt("user_id"));
        currUser.setUserName(rs.getString("user_name"));
        currUser.setPassword(rs.getString("password"));


        //
        // 都是硬编码会不会很累?
        // 而且要是 UserEntity 和 t_user 加字段,
        // 还得改代码...
        // 为何不尝试一下反射?
        // 跳到 step020 看看吧!
        //
        return currUser;
    }


}
