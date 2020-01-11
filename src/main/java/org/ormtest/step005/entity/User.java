package org.ormtest.step005.entity;

import lombok.Data;
import org.ormtest.step005.annotation.Column;

/**
 * Created Date by 2020/1/11 0051.
 *
 * @author Parker
 */
@Data
public class User {

    /** 用户Id */
    @Column(name = "user_id")
    private Integer userId;
    /** 用户名称 */
    @Column(name = "user_name")
    private String userName;
    /** 密码 */
    @Column(name = "password")
    private String password;

}
