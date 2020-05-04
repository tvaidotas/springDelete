package com.qa.service;

import com.qa.exception.NoteBookNotFoundException;
import com.qa.persistence.domain.Note;
import com.qa.persistence.domain.NoteBook;
import com.qa.persistence.dto.NoteBookDTO;
import com.qa.persistence.repo.NoteBookRepository;
import com.qa.persistence.repo.NotesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotebookService {

    private final NoteBookRepository noteBookRepository;

    private final NotesRepository notesRepository;

    private final NotesService notesService;

    private final ModelMapper mapper;

    @Autowired
    public NotebookService(NoteBookRepository noteBookRepository, NotesRepository notesRepository1, NotesService notesService, ModelMapper mapper){
        this.noteBookRepository = noteBookRepository;
        this.notesRepository = notesRepository1;
        this.notesService = notesService;
        this.mapper = mapper;
    }

    private NoteBookDTO mapToDTO(NoteBook noteBook) {
        return this.mapper.map(noteBook, NoteBookDTO.class);
    }

    public NoteBookDTO createNoteBook(NoteBook noteBook){
        return this.mapToDTO(this.noteBookRepository.save(noteBook));
    }

    public boolean deleteNoteBook(Long id){
        if (!this.noteBookRepository.existsById(id)){
            throw new NoteBookNotFoundException();
        }
        this.noteBookRepository.deleteById(id);
        return this.noteBookRepository.existsById(id);
    }

    public NoteBookDTO findNoteBookById(Long id){
        return this.mapToDTO(this.noteBookRepository.findById(id).orElseThrow(NoteBookNotFoundException::new));
    }

    public List<NoteBookDTO> readNoteBooks(){
        return this.noteBookRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public NoteBookDTO updateNoteBooks(NoteBook noteBook, Long id){
        NoteBook toUpdate = this.noteBookRepository.findById(id).orElseThrow(NoteBookNotFoundException::new);
        toUpdate.setName(noteBook.getName());
        return this.mapToDTO(this.noteBookRepository.save(toUpdate));
    }

    public NoteBookDTO addNoteToNoteBook(Long id, Note note){
        NoteBook noteBookToUpdate = this.noteBookRepository.findById(id).orElseThrow(NoteBookNotFoundException::new);
        Note noteToAdd = this.notesRepository.save(note);
        noteBookToUpdate.getNotes().add(noteToAdd);
        return this.mapToDTO(this.noteBookRepository.saveAndFlush(noteBookToUpdate));
    }


}
