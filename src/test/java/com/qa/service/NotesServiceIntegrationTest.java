package com.qa.service;

import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.persistence.repo.NotesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NotesServiceIntegrationTest {

    @Autowired
    private NotesService notesService;

    @Autowired
    private NotesRepository repository;

    private Note testNote;

    private Note testNoteWithID;

    @Autowired
    private ModelMapper modelMapper;

    private NoteDTO mapToDTO(Note note) {
        return this.modelMapper.map(note, NoteDTO.class);
    }

    @Before
    public void setUp(){
        this.testNote = new Note("My recipes", "Blueberry muffin");
        this.repository.deleteAll();
        this.testNoteWithID = this.repository.save(this.testNote);
    }

    @Test
    public void testReadNotes() {
        assertThat(this.notesService.readNotes()).isEqualTo(Stream.of(this.mapToDTO(testNoteWithID)).collect(Collectors.toList()));
    }

    @Test
    public void testCreateNote() {
        assertEquals(this.mapToDTO(this.testNoteWithID), this.notesService.createNote(testNote));
    }

    @Test
    public void testDeleteNote() {
        assertThat(this.notesService.deleteNote(this.testNoteWithID.getId())).isFalse();
    }

    @Test
    public void testFindNoteByID() {
        assertThat(this.notesService.findNoteById(this.testNoteWithID.getId())).isEqualTo(this.mapToDTO(this.testNoteWithID));
    }

    @Test
    public void updateNoteTest() throws Exception {
        Note newNote = new Note("My shopping list", "bacon bacon and more bacon");
        Note noteToUpdate = new Note(newNote.getTitle(), newNote.getDescription());
        noteToUpdate.setId(this.testNoteWithID.getId());
        assertThat(this.notesService.updateNote(newNote, this.testNoteWithID.getId())).isEqualTo(this.mapToDTO(noteToUpdate));
    }

}
