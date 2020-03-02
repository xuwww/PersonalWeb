package com.personalweb.demo.mapper;

import com.personalweb.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("inset into user {name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name}),@{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl")
    void insert(User user);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer creator);
}
