package com.librarymanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.librarymanagement.domain.Book;
import com.librarymanagement.service.LibraryService;
import com.librarymanagement.utility.ConstantMessage;
import com.librarymanagement.utility.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

	private final LibraryService libraryService;
	
	private static final Logger logger = LoggerFactory.getLogger(LibraryController.class);

	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@PostMapping("/addBook")
	public ResponseEntity<Response> addBook(@RequestBody Book book) {
		logger.info("Adding book: {}", book.getTitle());
		Response response = new Response();
		try {
			libraryService.addBook(book);
			response.setMessage(ConstantMessage.BOOK_ADDED);
			response.setDetails(book);
			logger.info("Book added successfully: {}", book.getTitle());
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (RuntimeException e) {
			logger.error("Failed to add book: {}", e.getMessage());
			response.setMessage(ConstantMessage.BOOK_ADDED_FAILED);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/removeBook/{isbn}")
	public ResponseEntity<Response> removeBook(@PathVariable String isbn) {
		Response response = new Response();
		try {
			libraryService.removeBook(isbn);
			response.setMessage(ConstantMessage.BOOK_REMOVED);
			response.setDetails("Book Deleted");
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.setMessage(ConstantMessage.BOOK_REMOVED_FAILED);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

	@GetMapping("/findBookByTitle")
	public ResponseEntity<Response> findBookByTitle(@RequestParam String title) {
		Response response = new Response();
		try {
			response.setMessage(ConstantMessage.BOOKS_FOUND_BY_TITLE);
			response.setDetails(libraryService.findBookByTitle(title));
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.setMessage(ConstantMessage.BOOKS_FOUND_FAILED_BY_TITLE);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/findBookByAuthor")
	public ResponseEntity<Response> findBookByAuthor(@RequestParam String author) {
		Response response = new Response();
		try {
			response.setMessage(ConstantMessage.BOOKS_FOUND_BY_AUTHOR);
			response.setDetails(libraryService.findBookByAuthor(author));
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.setMessage(ConstantMessage.BOOKS_FOUND_FAILED_BY_AUTHOR);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/listAllBooks")
	public ResponseEntity<Response> listAllBooks() {
		Response response = new Response();
		try {
			response.setMessage(ConstantMessage.BOOKS_LISTED);
			response.setDetails(libraryService.listAllBooks());
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.setMessage(ConstantMessage.FAILED_TO_LIST_ALL_BOOKS);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

	@GetMapping("/listAvailableBooks")
	public ResponseEntity<Response> listAvailableBooks() {
		Response response = new Response();
		try {
			response.setMessage(ConstantMessage.AVAILABLE_BOOKS_LISTED);
			response.setDetails(libraryService.listAvailableBooks());
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			response.setMessage(ConstantMessage.FAILED_TO_LIST_AVAILABLE_BOOKS);
			response.setDetails(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
