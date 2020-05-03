package com.qa.persistence.repo;

import com.qa.persistence.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Note, Long> {

    List<Note> findByTitle(String title);

}
