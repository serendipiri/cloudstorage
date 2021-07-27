package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("insert into NOTES (notetitle, notedescription, userid) " +
            " values (#{noteTitle}, #{noteDescription}, #{userId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int createNote(Note note);

    @Select("select * from NOTES where userid = #{userId} order by noteid desc")
    List<Note> getNoteList(Integer userId);

    @Select("select * from notes where userid = #{userId} and noteid = #{noteId}")
    Note getNote(Integer userId, Integer noteId);

    @Delete("delete from notes where userid = #{userId} and noteid = #{noteId}")
    int deleteNote(Integer userId, Integer noteId);

    @Update("UPDATE NOTES set notetitle= #{noteTitle}, notedescription = #{noteDescription} " +
            " WHERE userid = #{userId} and noteid = #{noteId}")
    void updateNote(Note note);

    @Select("select noteid from NOTES " +
            " where userid = #{userId} and notetitle = #{noteTitle} " +
            "   and notedescription = #{noteDescription} ")
    Integer getNoteIdByTitleAndDescp(Note note);

}
