package com.malecki.wtt.controller;


import com.malecki.wtt.classes.BookExport;
import com.malecki.wtt.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("book")
public class BooksController {

    private final BooksService booksService;



    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @ResponseBody
    @GetMapping("/init")
    public void init() throws IOException, ParseException {
        booksService.init();
    }

    @ResponseBody
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookExport> getBookByIsbn(@PathVariable("isbn") String isbn){

        try
        {
            BookExport bookExport =  booksService.getBookByIsbn(isbn);
            return new ResponseEntity<>(bookExport, HttpStatus.OK);
        }
        catch (NoSuchElementException e)
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    @ResponseBody
    @GetMapping("/category/{category}")
    public ResponseEntity<List<BookExport>> getBooksByCategory(@PathVariable("category") String category)
    {
        List<BookExport> books = booksService.getBooksByCategory(category);
        return new ResponseEntity<>(books, HttpStatus.OK);

    }

    @ResponseBody
    @GetMapping("/pagecount/{number}")
    public ResponseEntity<BookExport> getBookByPageCount(@PathVariable("number") int number){
        try{
            BookExport bookExport;
            bookExport = booksService.getBookByPageCount(number);
            return new ResponseEntity<>(bookExport, HttpStatus.OK);
        }
        catch (NoSuchElementException e)
        {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Book with higher number of pages not found"
            );
        }


    }

    @ResponseBody
    @GetMapping("/{pagesPerHour}/{avgNumberOfHours}")
    public ResponseEntity<List<BookExport>> bestBooksToReadInAMonth(@PathVariable("pagesPerHour") int pages, @PathVariable("avgNumberOfHours") int hours) {
        try{
            List<BookExport> bestBooks = booksService.bestBooksToBeReadInAMonth(pages, hours);
            return new ResponseEntity<>(bestBooks, HttpStatus.OK);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

    }

    @ResponseBody
    @GetMapping("/recent")
    public ResponseEntity<Set<BookExport>> getRecentlySearchedBooks(){
        Set<BookExport> books = booksService.getRecentlySearchedBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}
