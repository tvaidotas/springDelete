package com.qa.service;

import com.qa.exception.NoteNotFoundException;
import com.qa.persistence.domain.Note;
import com.qa.persistence.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NotesService {

    private final NotesRepository repository;

    @Autowired
    public NotesService(NotesRepository notesRepository){
        this.repository = notesRepository;
    }

    public Note createNote(Note note){
        return this.repository.save(note);
    }

    public boolean deleteDuck(Long id){
        if (!this.repository.existsById(id)){
            throw new NoteNotFoundException();
        }
        this.repository.deleteById(id);
        return this.repository.existsById(id);
    }

    public Note findNoteById(Long id){
        return this.repository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    public List<Note> readNotes(){
        return this.repository.findAll();
    }

    public Note updateNote(Note note, Long id){
        Note toUpdate = findNoteById(id);
        toUpdate.setTitle(note.getTitle());
        toUpdate.setDescription(note.getDescription());
        return this.repository.save(toUpdate);
    }

}
