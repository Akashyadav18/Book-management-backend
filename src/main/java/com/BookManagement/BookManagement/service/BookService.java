package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    Book createBooks(Book book);

    public List<Book> getAllBooks();

    Book getBookBYId(Long id);

//    void deleteById(Long id);
//
//    Book updateBook(Long id, Book UpdatedBook);

    void deleteByIdAndUser(Long id, Long userId);

    Book updateBookByUser(Long id, Book updatedBook, Long userId);

}
