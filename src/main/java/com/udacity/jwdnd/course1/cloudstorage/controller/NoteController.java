package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/note")
public class NoteController {

    UserService userService;
    NoteService noteService;

    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @PostMapping
    public String addNote(@ModelAttribute("note") Note note, Authentication authentication,
                          Model model, RedirectAttributes redirectAttributes) {
        try {

            note.setUserId(userService.getUserId(authentication.getName()));

            Integer existingCredId = noteService.getNoteIdByTitleAndDescp(note);

            if (existingCredId != null
                    && (note.getNoteId() == null || !existingCredId.equals(note.getNoteId()) )) {
                throw new CloudStorageException("Same note already exists.");
            }

            //Edit note..
            if (note != null && note.getNoteId() != null) {
                return edit(note, redirectAttributes);
            }

            noteService.addNote(note);

            handleMessage(false, "Note added successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Note could not be added. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }

    private String edit(Note note, RedirectAttributes redirectAttributes) {
        try {
            noteService.editNote(note);
            handleMessage(false, "Note updated successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Note could not be edited. Something went wrong.", redirectAttributes);
        }
        return "redirect:/home";
    }


    @GetMapping("select/{noteId}")
    public ResponseEntity<Note> getEditView (@PathVariable Integer noteId, Authentication authentication) {

        ResponseEntity<Note> resp = null;
        try {

            Integer userId = userService.getUserId(authentication.getName());

            Note note = noteService.getNote(userId, noteId);
            if (note == null) {
                throw new CloudStorageException("Note could not be accessed.");
            }

//            model.addAttribute("error" , "error message???");
            resp = new ResponseEntity<>(note, HttpStatus.OK);

        }
//        catch (CloudStorageException e) {
//            handleMessage(true, e.getMessage(), redirectAttributes);
//        }
        catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            handleMessage(true, "Note could not be retrieved. Something went wrong.", redirectAttributes);
        }

        return resp;

    }


    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id, Authentication authentication, RedirectAttributes redirectAttributes) {

        try {

            Integer userId = userService.getUserId(authentication.getName());

            Integer res = noteService.deleteNote(userId, id);
            if (res != null && res < 1) {
                throw new CloudStorageException("Note could not be deleted!");
            }

            handleMessage(false, "Note deleted successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Note could not be deleted. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }


    public void handleMessage(boolean error, String message, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("activeTab", "notes");
        redirectAttributes.addFlashAttribute(error ? "errorMsgNote" : "successMsgNote", message);
    }

}