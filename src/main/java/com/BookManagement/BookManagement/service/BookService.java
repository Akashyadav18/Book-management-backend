package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBooks(Book book);

    Page<Book> getAllBooks(Pageable pageable, String search);

    Book getBookBYId(Long id);

//    void deleteById(Long id);
//    Book updateBook(Long id, Book UpdatedBook);

    void deleteByIdAndUser(Long id, Long userId);

    Book updateBookByUser(Long id, Book updatedBook, Long userId);

}
