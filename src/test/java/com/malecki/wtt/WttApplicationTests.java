package com.malecki.wtt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malecki.wtt.classes.BookExport;
import com.malecki.wtt.service.BooksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.assertThrows;

class WttApplicationTests {

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void getBookByIsbn_bookDoesNotExist() {
        BooksService booksService = new BooksService(objectMapper);
        assertThrows(NoSuchElementException.class, () -> booksService.getBookByIsbn("123"));
    }

    @Test
    void getBookByIsbn_bookExists(){
        BookExport expected = new BookExport();
        expected.setIsbn("9780080568782");
        List<BookExport> books = Collections.singletonList(expected);
        BooksService booksService = new BooksService(objectMapper, books);
        BookExport actual = booksService.getBookByIsbn("9780080568782");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBookByPageCount_bookDoesNotExist(){
        BooksService booksService = new BooksService(objectMapper);
        assertThrows(NoSuchElementException.class, () -> booksService.getBookByPageCount(3100));
    }

    @Test
    void getBookByPageCount_bookExists(){
        BookExport expected = new BookExport();
        expected.setPageCount(305);
        List<BookExport> books = Collections.singletonList(expected);
        BooksService booksService = new BooksService(objectMapper, books);
        BookExport actual = booksService.getBookByPageCount(300);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBookByCategory_bookDoesNotExist(){
        List<BookExport> expected = new ArrayList<>();
        BooksService booksService = new BooksService(objectMapper);
        List<BookExport> actual = booksService.getBooksByCategory("thatCategoryDoesNotExist");
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void getBookByCategory_bookExists(){
        BookExport expected = new BookExport();
        expected.setCategories(new String[]{"Computers"});
        List<BookExport> books = Collections.singletonList(expected);
        BooksService booksService = new BooksService(objectMapper, books);
        List<BookExport> actual = booksService.getBooksByCategory("Computers");
        for(BookExport book : actual){
            Assertions.assertEquals(expected,book);
        }
    }

    @Test
    void bestBooksToBeReadInAMonth_tooManyHours(){
        BooksService booksService = new BooksService(objectMapper);
        assertThrows(Exception.class, () -> booksService.bestBooksToBeReadInAMonth(50,25));
    }

    @Test
    void bestBooksToBeReadInAMonth_bookDoesNotExist(){
        List<BookExport> expected = new ArrayList<>();
        BooksService booksService = new BooksService(objectMapper);
        List<BookExport> actual = booksService.bestBooksToBeReadInAMonth(500,24);
        Assertions.assertEquals(expected,actual);
    }

    @Test
    void bestBooksToBeReadInAMonth_bookExists(){
        BookExport expected = new BookExport();
        expected.setPageCount(700);
        List<BookExport> books = Collections.singletonList(expected);
        BooksService booksService = new BooksService(objectMapper, books);
        List<BookExport> actual = booksService.bestBooksToBeReadInAMonth(10,2);
        for(BookExport book : actual){
            Assertions.assertEquals(expected,book);
        }
    }



}
