package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note) {
        return noteMapper.createNote(note);
    }

    public List<Note> getNotes(Integer userId) {
        return noteMapper.getNoteList(userId);
    }

    public Note getNote(Integer userId, Integer noteId) {
        return noteMapper.getNote(userId, noteId);
    }

    public Integer deleteNote(Integer userId, Integer noteId) {
        return noteMapper.deleteNote(userId, noteId);
    }

}
