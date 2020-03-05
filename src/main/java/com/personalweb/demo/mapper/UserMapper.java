package com.personalweb.demo.mapper;

import com.personalweb.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url,password) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{password})")
    void insert(User user);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select count(1) from user where name = #{name}")
    Integer findByName(@Param("name") String name);
}
