package com.qa.service;

import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.persistence.repo.NotesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class NotesServiceUnitTest {

    @InjectMocks
    private NotesService notesService;

    @Mock
    private NotesRepository repository;

    @Mock
    private ModelMapper modelMapper;

    private List<Note> noteList;

    private Note testNote;

    private long id = 1L;

    private Note testNoteWithID;

    private NoteDTO noteDTO;

    private NoteDTO mapToDTO(Note note) {
        return this.modelMapper.map(note, NoteDTO.class);
    }

    @Before
    public void setUp(){
        this.noteList = new ArrayList<>();
        this.testNote = new Note("My recipes", "Blueberry muffin");
        noteList.add(testNote);
        this.testNoteWithID = new Note(testNote.getTitle(), testNote.getDescription());
        this.testNoteWithID.setId(id);
        this.noteDTO = this.mapToDTO(testNoteWithID);
    }

    @Test
    public void readNotesTest() {
        when(repository.findAll()).thenReturn(this.noteList);
        when(this.modelMapper.map(testNoteWithID, NoteDTO.class)).thenReturn(noteDTO);
        assertFalse("Controller has found no ducks", this.notesService.readNotes().isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void createNotesTest() {
        when(this.repository.save(testNote)).thenReturn(testNoteWithID);
        when(this.modelMapper.map(testNoteWithID, NoteDTO.class)).thenReturn(noteDTO);
        assertEquals(this.noteDTO, this.notesService.createNote(testNote));
        verify(this.repository, times(1)).save(this.testNote);
    }

    @Test
    public void deleteNoteTest() {
        when(this.repository.existsById(id)).thenReturn(true, false);

        this.notesService.deleteNote(id);

        verify(this.repository, times(1)).deleteById(id);
        verify(this.repository, times(2)).existsById(id);
    }

    @Test
    public void findNoteByIDTest() {
        when(this.repository.findById(this.id)).thenReturn(Optional.of(this.testNoteWithID));
        when(this.modelMapper.map(testNoteWithID, NoteDTO.class)).thenReturn(noteDTO);

        assertEquals(this.noteDTO, this.notesService.findNoteById(this.id));

        verify(this.repository, times(1)).findById(this.id);
    }

    @Test
    public void updateNoteTest() {
        Note newNote = new Note("My shopping list", "bacon bacon and more bacon");
        Note noteToUpdate = new Note(newNote.getTitle(), newNote.getDescription());
        noteToUpdate.setId(this.id);

        NoteDTO updatedDTO = new ModelMapper().map(noteToUpdate, NoteDTO.class);


        when(this.repository.findById(this.id)).thenReturn(Optional.of(this.testNoteWithID));
        when(this.modelMapper.map(noteToUpdate, NoteDTO.class)).thenReturn(updatedDTO);

        // You NEED to configure a .equals() method in Duck.java for this to work
        when(this.repository.save(noteToUpdate)).thenReturn(noteToUpdate);

        assertEquals(updatedDTO, this.notesService.updateNote(newNote, this.id));

        verify(this.repository, times(1)).findById(1L);
        verify(this.repository, times(1)).save(noteToUpdate);
    }

}
