package com.qa.repo;

import com.qa.persistence.domain.Note;
import com.qa.persistence.repo.NotesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {

    @Autowired
    NotesRepository repository;

    private final String title = "Notes title";
    private final String description = "Testing description";

    private final Note note = new Note(title, description);

    private Note savedNote;

    @Before
    public void setUp(){
        this.repository.deleteAll();
        savedNote = this.repository.save(note);
    }

    @Test
    public void testFindByTitle(){
        assertThat(this.repository.findByTitle(this.title)).containsExactly(this.savedNote);
    }

}
