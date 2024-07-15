package com.librarymanagement.service;

import java.util.List;

import com.librarymanagement.domain.Book;

public interface LibraryService {


    public void addBook(Book book);

    public void removeBook(String isbn); 

    public List<Book> findBookByTitle(String title);

    public List<Book> findBookByAuthor(String author);

    public List<Book> listAllBooks();

    public List<Book> listAvailableBooks();

}
