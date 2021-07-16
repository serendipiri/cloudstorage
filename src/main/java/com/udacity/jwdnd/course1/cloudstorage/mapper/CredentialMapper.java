package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("insert into CREDENTIALS (url, username, key, password, userid) " +
            " values (#{url}, #{username}, #{key}, #{password}, #{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

    @Select("select * from CREDENTIALS where userid = #{userId} order by credentialid desc")
    List<Credential> getCredentialList(Integer userId);

    @Select("select * from CREDENTIALS where userid = #{userId} and credentialid = #{credentialId}")
    Credential getCredential(Integer userId, Integer credentialId);

    @Delete("delete from CREDENTIALS where userid = #{userId} and credentialid = #{credentialId}")
    int deleteCredential(Integer userId, Integer credentialId);

}
