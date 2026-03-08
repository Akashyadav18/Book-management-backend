package com.BookManagement.BookManagement.service;

import com.BookManagement.BookManagement.entity.Book;
import com.BookManagement.BookManagement.repository.BookRepository;
import com.BookManagement.BookManagement.specification.BookSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookServiceImpl implements BookService{
    @Autowired
    BookRepository bookRepository;

    //while creating books it will automatically generate isbn
    @Override
    public Book createBooks(Book book) {
        book.setIsbn("ISBN-" + System.currentTimeMillis());
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable, String search) {
        Specification<Book> spec = BookSpecification.getSpecification(search);
        return bookRepository.findAll(spec, pageable);
//        specification shirf findAll method ko accept karta h
//        return bookRepository.findByIsDeletedFalse(pageable);
    }



    @Override
    public Book getBookBYId(Long id) {
        return bookRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new RuntimeException("Book not found "+id)
        );
    }

//    @Override
//    public void deleteById(Long id) {
//        Book deleteBook = bookRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
//                () -> new RuntimeException("Book Not Found "+id)
//        );
//        deleteBook.setDeleted(true);
//        bookRepository.save(deleteBook);
//    }
//
//    @Override
//    public Book updateBook(Long id, Book UpdatedBook) {
//        Book book = bookRepository.findByIdAndIsDeletedFalse(id)
//                .orElseThrow(() -> new RuntimeException("Book Not Found "+id));
//        book.setTitle(UpdatedBook.getTitle());
//        book.setAuthor(UpdatedBook.getAuthor());
//        book.setPublicationYear(UpdatedBook.getPublicationYear());
//        return bookRepository.save(book);
//    }

    @Override
    public void deleteByIdAndUser(Long id, Long userId) {
        Book deleteBook = bookRepository
                .findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found or not authorized: " + id));
        if (!deleteBook.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to delete this book");
        }
        deleteBook.setDeleted(true);
        bookRepository.save(deleteBook);
    }

    @Override
    public Book updateBookByUser(Long id, Book updatedBook, Long userId) {
//        SELECT * FROM books WHERE id = ? AND user_id = ? AND is_deleted = false
        Book book = bookRepository
                .findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found or not authorized: " + id));
        if (!book.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to update this book");
        }
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublicationYear(updatedBook.getPublicationYear());
        book.setCategory(updatedBook.getCategory());

        return bookRepository.save(book);
    }


}
