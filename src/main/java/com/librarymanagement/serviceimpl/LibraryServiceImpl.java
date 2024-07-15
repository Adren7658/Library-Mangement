package com.librarymanagement.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.librarymanagement.domain.Book;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.service.LibraryService;
import com.librarymanagement.utility.ConstantMessage;

@Service
public class LibraryServiceImpl implements LibraryService {
	
	private final BookRepository bookRepository;

    public LibraryServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        try {
            if (!bookRepository.existsById(book.getIsbn())) {
                bookRepository.save(book);
            } else {
                throw new RuntimeException(ConstantMessage.BOOK_WITH_ISBN + book.getIsbn() + ConstantMessage.ALREADY_EXISTS);
            }
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_ADDING_BOOK + e.getMessage());
        }
    }

    public void removeBook(String isbn) {
        try {
            if (bookRepository.existsById(isbn)) {
                bookRepository.deleteById(isbn);
            } else {
                throw new RuntimeException(ConstantMessage.BOOK_WITH_ISBN + isbn + ConstantMessage.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_REMOVING_BOOK + e.getMessage());
        }
    }

    public List<Book> findBookByTitle(String title) {
        try {
            return bookRepository.findByTitleContainingIgnoreCase(title);
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_FINDING_BOOKS_BY_TITLE + e.getMessage());
        }
    }

    public List<Book> findBookByAuthor(String author) {
        try {
            return bookRepository.findByAuthorContainingIgnoreCase(author);
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_FINDING_BOOKS_BY_AUTHOR + e.getMessage());
        }
    }

    public List<Book> listAllBooks() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_LISTING_BOOKS + e.getMessage());
        }
    }

    public List<Book> listAvailableBooks() {
        try {
            return bookRepository.findAll().stream()
                    .filter(Book::isAvailability)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(ConstantMessage.ERROR_LISTING_AVAILABLE_BOOKS + e.getMessage());
        }
    }

}
