package com.qa.persistence.repo;

import com.qa.persistence.domain.NoteBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteBookRepository extends JpaRepository<NoteBook, Long> {

}
