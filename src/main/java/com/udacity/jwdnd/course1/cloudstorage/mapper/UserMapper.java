package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username} ")
    User getUser(String username);

    @Insert("insert into users (username, salt, password, firstname, lastname) " +
            " values (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int addUser(User user);

    @Select("select * from users")
    List<User> getUserList();

    @Select("select userid from users where username = #{username} ")
    Integer getUserId(String username);
}
