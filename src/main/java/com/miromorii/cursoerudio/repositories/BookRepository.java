package com.miromorii.cursoerudio.repositories;

import com.miromorii.cursoerudio.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
