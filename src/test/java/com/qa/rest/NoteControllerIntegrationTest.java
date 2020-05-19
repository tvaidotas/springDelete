package com.qa.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.persistence.domain.Note;
import com.qa.persistence.dto.NoteDTO;
import com.qa.persistence.repo.NotesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private NotesRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private ObjectMapper mapper = new ObjectMapper();

    private Note testNote;

    private long id;

    private Note testNoteWithID;

    private NoteDTO noteDTO;

    private NoteDTO mapToDTO(Note note) {
        return this.modelMapper.map(note, NoteDTO.class);
    }

    @Before
    public void setUp(){
        this.repository.deleteAll();
        this.testNote = new Note("My recipes", "Blueberry muffin");
        this.testNoteWithID = this.repository.save(this.testNote);
        this.id = this.testNoteWithID.getId();
        this.noteDTO = this.mapToDTO(testNoteWithID);
    }

    @Test
    public void getAllNotesTest() throws Exception {
        List<NoteDTO> noteDTOList = new ArrayList<>();
        noteDTOList.add(this.noteDTO);
        String content = this.mock.perform(
            request(HttpMethod.GET, "/getAll")
            .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(noteDTOList), content);
    }

    @Test
    public void createNoteTest() throws Exception {
        String result = this.mock.perform(
            request(HttpMethod.POST, "/createNote").contentType(MediaType.APPLICATION_JSON)
            .content(this.mapper.writeValueAsString(testNote)).accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        Assertions.assertEquals(this.mapper.writeValueAsString(noteDTO), result);
    }

    @Test
    public void deleteNoteTest() throws Exception {
        this.mock.perform(request(HttpMethod.DELETE, "/deleteNote/" + this.id)).andExpect(status().isNoContent());
    }

//    @Test
//    public void deleteNoteForNonExistentIDTest() throws Exception {
//        this.mock.perform(request(HttpMethod.DELETE, "/deleteNote/" + 65)).andExpect(status().is4xxClientError());
//    }

    @Test
    public void updateNoteTest() throws Exception {
        Note newNote = new Note("My shopping list", "bacon bacon and more bacon");
        Note noteToUpdate = new Note(newNote.getTitle(), newNote.getDescription());
        noteToUpdate.setId(this.id);
        String result = this.mock.perform(
            request(HttpMethod.PUT, "/updateNote/?id=" + this.id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newNote))
        )
        .andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
        assertEquals(this.mapper.writeValueAsString(this.mapToDTO(noteToUpdate)), result);
    }

}
