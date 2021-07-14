package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from files where userid = #{userId} order by fileId desc")
    List<File> fileList(Integer userId);

    @Select("select * from files where userid = #{userId} and filename = #{fileName}")
    File getFileByName(String fileName, Integer userId);

    @Select("select * from files where userid = #{userId} and fileId = #{fileId}")
    File getFileById(Integer userId, Integer fileId);

    @Insert("insert into files (filename, contenttype, filesize, userid, filedata) " +
            " values (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFile(File file);

    @Delete("delete from files where fileId = #{fileId}")
    int deleteFile(Integer fileId);

}
