package com.qa.persistence.dto;

import com.qa.persistence.domain.Note;
import java.util.List;

public class NoteBookDTO {

    private Long id;
    private String name;
    private List<Note> notes;

    public NoteBookDTO() {
    }

    public NoteBookDTO(String name, List<Note> notes) {
        super();
        this.name = name;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}
