package com.qa.persistence.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "note_name", unique = true)
    private String title;
    private String description;

    @ManyToOne(targetEntity = NoteBook.class)
    private NoteBook noteBook;

    public Note(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public Note(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id.equals(note.id) &&
                title.equals(note.title) &&
                description.equals(note.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description);
    }

    public NoteBook getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(NoteBook noteBook) {
        this.noteBook = noteBook;
    }

}
