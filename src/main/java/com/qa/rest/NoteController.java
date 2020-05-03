package com.qa.rest;

import com.qa.persistence.domain.Note;
import com.qa.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class NoteController {

    private final NotesService service;

    @Autowired
    public NoteController(NotesService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<Note> getAllNotes(){
        return this.service.readNotes();
    }

    @PostMapping("/createNote")
    public Note createNote(@RequestBody Note note){
        return this.service.createNote(note);
    }

    @DeleteMapping("/deleteNote/{id}")
    public boolean deleteNote(@PathVariable Long id){
        return this.service.deleteDuck(id);
    }

    @GetMapping("/getNote/{id}")
    public Note getNote(@PathVariable Long id){
        return this.service.findNoteById(id);
    }

    @PutMapping("/updateNote")
    public Note updateNote(@PathParam("id") Long id, @RequestBody Note note){
        return this.service.updateNote(note, id);
    }

}
