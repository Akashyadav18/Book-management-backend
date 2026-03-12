package com.BookManagement.BookManagement.controller;

import com.BookManagement.BookManagement.apiResponse.ApiResponse;
import com.BookManagement.BookManagement.entity.Book;
import com.BookManagement.BookManagement.service.BookService;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:4200/", allowCredentials = "true")
public class Controller {

    @Autowired
    BookService bookService;

    @PostMapping("/createBook")
    public ResponseEntity<ApiResponse<Book>> createBooks(@RequestBody Book book, HttpSession session) {
    try {
        Long userId = (Long) session.getAttribute("USER_ID");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>("Please login first", null));
        }
        book.setUserId(userId);
        Book savedBook = bookService.createBooks(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Book created successfully", savedBook));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("Failed to create book", null));
    }
}

//while create and get all data, operation can fail due to many reasons -> DB conn issues,
    //unexpected sys issues i.e Exception
//    @GetMapping("/getAllBooks")
//    public ResponseEntity<?> getAllBooks(){
//        try{
//            List<Book> books = bookService.getAllBooks();
//            if(books.isEmpty()){
//                return ResponseEntity.status(HttpStatus.NO_CONTENT)//204
//                         .body(new ApiResponse<>("NO Book Found", null));
//            }
//            return new ResponseEntity<>(books, HttpStatus.OK);//200
//        }catch (Exception e){
//            return new ResponseEntity<>("Error while fetching book", HttpStatus.INTERNAL_SERVER_ERROR);//500
//        }
//    }
    @GetMapping("/getAllBooks")
    public ResponseEntity<?> getAllBooks(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "5", required = false) int pageSize,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "ASC", required = false) String sortDir,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer publicationYear
    ){
        try{
            Sort sort = null;
            if(sortDir.equalsIgnoreCase("ASC")){
                sort = Sort.by(sortBy).ascending();
            }
            else {
                sort = Sort.by(sortBy).descending();
            }
            Page<Book> books = bookService.getAllBooks(PageRequest.of(pageNo -1, pageSize, sort), title, author, category, publicationYear);
            if(books.isEmpty()){
                return ResponseEntity.status(HttpStatus.NO_CONTENT)//204
                        .body(new ApiResponse<>("NO Book Found", null));
            }
            return new ResponseEntity<>(books, HttpStatus.OK);//200
        }catch (Exception e){
            return new ResponseEntity<>("Error while fetching book", HttpStatus.INTERNAL_SERVER_ERROR);//500
        }
    }

    //Service layer typically throws unchecked exceptions for business rule violations, which map well to 4xx HTTP errors.
//while in getById, update, delete, they usually fail due to business logic-> id not found, invalid input
    //i.e RuntimeException
    @GetMapping("/getBook/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id, HttpSession session){
        try{
            Book bookById = bookService.getBookBYId(id);
            return new ResponseEntity<>(bookById, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>("Book not found with id: "+id, HttpStatus.NOT_FOUND); //404
        }
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id,  HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("USER_ID");

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Please login first", null));
            }
            bookService.deleteByIdAndUser(id, userId);
            return ResponseEntity.ok(
                    new ApiResponse<>("Book deleted successfully", null)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Book not found with id: " + id, null));
        }
    }


    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book,  HttpSession session){
        try {
            Long userId = (Long) session.getAttribute("USER_ID");
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ApiResponse<>("Please login first", null));
            }
            Book updatedBook = bookService.updateBookByUser(id, book, userId);
            return ResponseEntity.ok(
                    new ApiResponse<>("Book updated successfully", updatedBook)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Failed to update book with id: " + id, null));
        }
    }
}
