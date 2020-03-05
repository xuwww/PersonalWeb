package com.personalweb.demo.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private  String name;
    private String accountId;
    private String token;
    private Long gmtCreate;//创建时间
    private Long gmtModified;//修改时间
    private String avatarUrl;
    private String password;

}
