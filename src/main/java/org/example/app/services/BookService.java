package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        if (book.getAuthor().isEmpty() && book.getTitle().isEmpty() && book.getSize() == null) {
            return;
        }
        bookRepo.store(book);
    }

    public boolean removeBookId(Integer bookIdRemove) {
        return bookRepo.removeItemById(bookIdRemove);
    }

    public boolean removeBooksByRegex(String regex) {
        return bookRepo.removeByRegex(regex);
    }
}