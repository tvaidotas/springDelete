package com.qa.rest;

import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.service.NotesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class NoteControllerUnitTest {

    @InjectMocks
    private NoteController noteController;

    @Mock
    private NotesService notesService;

    private List<Note> noteList;

    private Note testNote;

    private long id = 1L;

    private Note testNoteWithID;

    private NoteDTO noteDTO;

    private final ModelMapper modelMapper = new ModelMapper();

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
    public void getAllNotesTest() {
        when(notesService.readNotes())
            .thenReturn(this.noteList.stream().map(this::mapToDTO).collect(Collectors.toList()));
        assertFalse("Controller has found no notes", this.noteController.getAllNotes().getBody().isEmpty());
        verify(notesService, times(1)).readNotes();
    }

    @Test
    public void createNoteTest() {
        when(this.notesService.createNote(testNote)).thenReturn(this.noteDTO);
        assertEquals(new ResponseEntity<NoteDTO>(this.noteDTO, HttpStatus.CREATED), this.noteController.createNote(testNote));
        verify(this.notesService, times(1)).createNote(this.testNote);
    }

    @Test
    public void deleteNoteTest() {
        this.noteController.deleteNote(id);
        verify(this.notesService, times(1)).deleteNote(id);
    }

    @Test
    public void findNoteByIDTest() {
        when(this.notesService.findNoteById(this.id))
            .thenReturn(this.noteDTO);
        assertEquals(
                new ResponseEntity<NoteDTO>(this.noteDTO, HttpStatus.OK),
                this.noteController.getNote(this.id)
        );
        verify(this.notesService, times(1)).findNoteById(this.id);
    }

    @Test
    public void updateNoteTest() {
        Note newNote = new Note("What a nice day", "Drank my first beer today");
        Note updateNote = new Note(newNote.getTitle(), newNote.getDescription());
        updateNote.setId(this.id);
        when(this.notesService.updateNote(newNote, this.id)).thenReturn(this.mapToDTO(updateNote));
        assertEquals(new ResponseEntity<NoteDTO>(this.mapToDTO(updateNote), HttpStatus.ACCEPTED), this.noteController.updateNote(this.id, newNote));
        verify(this.notesService, times(1)).updateNote(newNote, this.id);
    }

}
