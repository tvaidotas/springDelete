package com.qa.service;

import com.qa.exception.NoteNotFoundException;
import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.persistence.repo.NotesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotesService {

    private final NotesRepository repository;

    private final ModelMapper mapper;

    @Autowired
    public NotesService(NotesRepository notesRepository, ModelMapper mapper){
        this.repository = notesRepository;
        this.mapper = mapper;
    }

    private NoteDTO mapToDTO(Note note) {
        return this.mapper.map(note, NoteDTO.class);
    }

    public NoteDTO createNote(Note note){
        Note savedNote = this.repository.save(note);
        return this.mapToDTO(savedNote);
    }

    public boolean deleteDuck(Long id){
        if (!this.repository.existsById(id)){
            throw new NoteNotFoundException();
        }
        this.repository.deleteById(id);
        return this.repository.existsById(id);
    }

    public NoteDTO findNoteById(Long id){
        return this.mapToDTO(this.repository.findById(id).orElseThrow(NoteNotFoundException::new));
    }

    public List<NoteDTO> readNotes(){
        return this.repository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public NoteDTO updateNote(Note note, Long id){
        Note toUpdate = this.repository.findById(id).orElseThrow(NoteNotFoundException::new);
        toUpdate.setTitle(note.getTitle());
        toUpdate.setDescription(note.getDescription());
        Note savedNote = this.repository.save(toUpdate);
        return this.mapToDTO(savedNote);
    }

}
