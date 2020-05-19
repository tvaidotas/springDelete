package com.qa.persistence.dto;

import java.util.List;
import java.util.Objects;

public class NoteBookDTO {

    private Long id;
    private String name;
    private List<NoteDTO> notes;

    public NoteBookDTO() {
    }

    public NoteBookDTO(String name, List<NoteDTO> notes) {
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

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NoteBookDTO that = (NoteBookDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, notes);
    }
}
