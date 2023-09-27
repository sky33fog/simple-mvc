package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = LogManager.getLogger(BookShelfController.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeByRegex(String regex) {
        boolean deletionFlag = false;
        for (Book book : retreiveAll()) {
            if(book.getAuthor().equals(regex) || book.getTitle().equals(regex)) {
                logger.info("remove book by regex - " + "\"" + regex + "\"" + ": " + book);
                repo.remove(book);
                deletionFlag = true;
            }
        }
        if (regex.matches("\\d+")) {
            for (Book book : retreiveAll()) {
                if (book.getSize() != null && book.getSize() == Integer.parseInt(regex)) {
                    logger.info("remove book by regex - " + "\"" + regex + "\"" + ": " + book);
                    repo.remove(book);
                    deletionFlag = true;
                }
            }
        }
        return deletionFlag;
    }
}
