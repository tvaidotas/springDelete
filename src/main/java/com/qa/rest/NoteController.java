package com.qa.rest;

import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<NoteDTO>> getAllNotes(){
        return ResponseEntity.ok(this.service.readNotes());
    }

    @PostMapping("/createNote")
    public ResponseEntity<NoteDTO> createNote(@RequestBody Note note){
        return new ResponseEntity<NoteDTO>(this.service.createNote(note), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteNote/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id){
        return this.service.deleteNote(id)
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/getNote/{id}")
    public ResponseEntity<NoteDTO> getNote(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findNoteById(id));
    }

    @PutMapping("/updateNote")
    public ResponseEntity<NoteDTO> updateNote(@PathParam("id") Long id, @RequestBody Note note){
        return new ResponseEntity<NoteDTO>(this.service.updateNote(note, id), HttpStatus.ACCEPTED);
    }

}
