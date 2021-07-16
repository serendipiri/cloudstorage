package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.common.CloudStorageException;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
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
//            noteService.editNote(note);
            handleMessage(false, "Note updated successfully.", redirectAttributes);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Note could not be edited. Something went wrong.", redirectAttributes);
        }
        return "redirect:/home";
    }


    @GetMapping("select")
    public String getEditView (@RequestParam Integer noteId, Authentication authentication, RedirectAttributes redirectAttributes) {

        try {

            Integer userId = userService.getUserId(authentication.getName());

            Note note = noteService.getNote(userId, noteId);
            if (note == null) {
                throw new CloudStorageException("Note could not be accessed.");
            }

            redirectAttributes.addFlashAttribute("note", note);
            redirectAttributes.addFlashAttribute("openModal", true);

        } catch (CloudStorageException e) {
            handleMessage(true, e.getMessage(), redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            handleMessage(true, "Note could not be retrieved. Something went wrong.", redirectAttributes);
        }

        return "redirect:/home";
    }


    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        try {

            Integer res = noteService.deleteFile(id);
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