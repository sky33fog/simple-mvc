package org.example.app.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.web.controllers.BookShelfController;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = LogManager.getLogger(BookShelfController.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(String bookIdRemove) {
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit () {
        logger.info("default INIT in book repo bean");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in book repo bean");
    }
}
