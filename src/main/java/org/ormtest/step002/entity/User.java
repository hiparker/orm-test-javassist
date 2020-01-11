package org.ormtest.step002.entity;

import lombok.Data;

/**
 * Created Date by 2020/1/11 0021.
 *
 * @author Parker
 */
@Data
public class User {

    /** 用户Id */
    private Integer userId;
    /** 用户名称 */
    private String userName;
    /** 密码 */
    private String password;

}
