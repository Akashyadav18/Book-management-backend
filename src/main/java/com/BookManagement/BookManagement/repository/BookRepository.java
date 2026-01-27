package com.BookManagement.BookManagement.repository;

import com.BookManagement.BookManagement.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    //select * from book where is_deleted = false
    List<Book> findByIsDeletedFalse();
    //select * from book where id=? and is_deleted=false
    Optional<Book> findByIdAndIsDeletedFalse(Long id);
//    SELECT * FROM books WHERE id = ? AND user_id = ? AND is_deleted = false
    Optional<Book> findByIdAndUserIdAndIsDeletedFalse(Long id, Long userId);

}
