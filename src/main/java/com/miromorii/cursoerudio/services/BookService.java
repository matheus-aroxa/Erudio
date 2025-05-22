package com.miromorii.cursoerudio.services;

import com.miromorii.cursoerudio.controllers.BookController;
import com.miromorii.cursoerudio.data.dto.v1.BookDTO;
import com.miromorii.cursoerudio.exceptions.RequiredObjectIsNullException;
import com.miromorii.cursoerudio.exceptions.ResourceNotFoundException;
import com.miromorii.cursoerudio.mapper.ObjectMapper;
import com.miromorii.cursoerudio.models.Book;
import com.miromorii.cursoerudio.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public BookDTO findById(Long id) {
        logger.info("Finding a book");
        BookDTO bookDTO = ObjectMapper.parseObject(bookRepository.findById(id).get(), BookDTO.class);
        return addHateoasLinks(bookDTO);
    }

    public List<BookDTO> findAll() {
        logger.info("Finding all books");

        List<BookDTO> dtos = ObjectMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
        for (BookDTO dto : dtos) {
            addHateoasLinks(dto);
        }
        return dtos;
    }

    public BookDTO create(BookDTO bookDTO) {
        logger.info("Creating a book");

        if(bookDTO == null) throw new RequiredObjectIsNullException();
        return addHateoasLinks(ObjectMapper.parseObject(bookRepository.save(ObjectMapper.parseObject(bookDTO, Book.class)), BookDTO.class));
    }

    public BookDTO update(BookDTO bookDTO) {
        logger.info("Updating a book");

        if(bookDTO == null) throw new RequiredObjectIsNullException();
        Book book =bookRepository.findById(bookDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id"));

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setLaunchDate(bookDTO.getLaunchDate());
        book.setPrice(bookDTO.getPrice());

        return addHateoasLinks(ObjectMapper.parseObject(bookRepository.save(book), BookDTO.class));
    }

    public void delete(Long id) {
        logger.info("Deleting a book");

        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id"));
        bookRepository.deleteById(id);
    }

    private static BookDTO addHateoasLinks(BookDTO bookDTO) {
        bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getId())).withSelfRel().withType("GET"));
        bookDTO.add(linkTo(methodOn(BookController.class).findAll()).withRel("findAll").withType("GET"));
        bookDTO.add(linkTo(methodOn(BookController.class).create(bookDTO)).withRel("create").withType("POST"));
        bookDTO.add(linkTo(methodOn(BookController.class).update(bookDTO)).withRel("update").withType("PUT"));
        return bookDTO.add(linkTo(methodOn(BookController.class).delete(bookDTO.getId())).withRel("delete").withType("DELETE"));
    }
}
