package com.qa.rest;

import com.qa.persistence.domain.Note;
import com.qa.persistence.domain.NoteBook;
import com.qa.persistence.dto.NoteBookDTO;
import com.qa.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class NoteBookController {

    private final NotebookService service;

    @Autowired
    public NoteBookController(NotebookService service) {
        this.service = service;
    }

    @GetMapping("/getAllNoteBooks")
    public ResponseEntity<List<NoteBookDTO>> getAllNotes(){
        return ResponseEntity.ok(this.service.readNoteBooks());
    }

    @PostMapping("/createNoteBook")
    public  ResponseEntity<NoteBookDTO> createNote(@RequestBody NoteBook noteBook){
        return new ResponseEntity<NoteBookDTO>(this.service.createNoteBook(noteBook), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteNoteBook/{id}")
    public ResponseEntity<?> deleteNoteBook(@PathVariable Long id){
        return this.service.deleteNoteBook(id)
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/getNoteBook/{id}")
    public ResponseEntity<NoteBookDTO> getNote(@PathVariable Long id){
        return ResponseEntity.ok(this.service.findNoteBookById(id));
    }

    @PutMapping("/updateNoteBook")
    public ResponseEntity<NoteBookDTO> updateNote(@PathParam("id") Long id, @RequestBody NoteBook noteBook){
        return ResponseEntity.ok(this.service.updateNoteBooks(noteBook, id));
    }

    /*
        The main difference between the PUT and PATCH method is that the PUT method uses the request
         URI to supply a modified version of the requested resource which replaces the original version of the resource
         whereas the PATCH method supplies a set of instructions to modify the resource.

         POST is always for creating a resource ( does not matter if it was duplicated )
         PUT is for checking if resource is exists then update , else create new resource. PATCH is always for update a resource.
     */

    @PatchMapping("/update/{id}")
    public ResponseEntity<NoteBookDTO> addNoteToNoteBook(@PathVariable Long id, @RequestBody Note note) {
        return new ResponseEntity<NoteBookDTO>(this.service.addNoteToNoteBook(id, note), HttpStatus.ACCEPTED);
    }

}
