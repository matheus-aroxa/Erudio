package com.miromorii.cursoerudio.unittests.mapper.mocks;

import com.miromorii.cursoerudio.data.dto.v1.BookDTO;
import com.miromorii.cursoerudio.data.dto.v1.PersonDTO;
import com.miromorii.cursoerudio.models.Book;
import com.miromorii.cursoerudio.models.Person;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockBook {

    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookDTO> mockDTOList() {
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockDTO(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setTitle("Tittle Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(LocalDate.of(2020, 1, 1));
        book.setPrice(10);
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setTitle("Tittle Test" + number);
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(LocalDate.of(2020, 1, 1));
        book.setPrice(10);
        return book;
    }

}