package com.malecki.wtt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.malecki.wtt.classes.Book;
import com.malecki.wtt.classes.BookExport;
import com.malecki.wtt.classes.IndustryIdentifiers;
import com.malecki.wtt.cache.LRUCache;
import com.malecki.wtt.classes.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BooksService {

    private final ObjectMapper objectMapper;


    private List<BookExport> BOOKS = new ArrayList<>();


    public List<BookExport> getBOOKS() {
        return BOOKS;
    }

    private LRUCache cache = new LRUCache(5);

    @Autowired
    public BooksService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public BooksService(ObjectMapper objectMapper, List<BookExport> BOOKS) {
        this.objectMapper = objectMapper;
        this.BOOKS = BOOKS;
    }

    public BookExport getBookByIsbn(String isbn) {
        BookExport result = null;
        for (BookExport book : BOOKS) {
            if (book.getIsbn().toLowerCase(Locale.ROOT).equals(isbn.toLowerCase(Locale.ROOT))) {
                result = book;
            }
        }
        if (result == null) {
            throw new NoSuchElementException();
        } else {
            cache.put(result);
            return result;
        }
    }

    public List<BookExport> getBooksByCategory(String category){
        List<BookExport> books = new ArrayList<>();
        for(BookExport b : BOOKS){
            if(b.getCategories() == null)
                continue;
            String[] categories;
            categories = b.getCategories();
            if(categories.length != 0)
            {
                for (String s : categories) {
                    if (s.toLowerCase(Locale.ROOT).equals(category) || s.equals(category))
                        books.add(b);
                }
            }
        }
        return books;
    }

    public BookExport getBookByPageCount(int number){

        BookExport bookExport = null;

        for(BookExport be : BOOKS){
            if(be.getPageCount() > number){
                bookExport =  be;
                break;
            }
        }
        if(bookExport == null){
            throw new NoSuchElementException();
        }
        else
            return bookExport;
    }

    public List<BookExport> bestBooksToBeReadInAMonth(int pages, int hours){
        if(hours > 24)
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        int pagesInMonth = pages * hours * 30;
        List<BookExport> bestBooks = new ArrayList<>();
        for(BookExport be : BOOKS){
            if (pagesInMonth > be.getPageCount())
                bestBooks.add(be);
        }

        bestBooks.sort(
                Comparator.comparingDouble(BookExport::getAverageRating));
        Collections.reverse(bestBooks);
        return bestBooks;
    }

    public Set<BookExport> getRecentlySearchedBooks(){
        return cache.getCache();
    }

    public void init() throws IOException, ParseException {

        Library books = objectMapper.readValue(new File("books.json"), Library.class);

        for (Book item : books.getItems()) {
            BOOKS.add(exportBook(item));
        }
    }

    public static BookExport exportBook(Book book) throws ParseException {

        BookExport be = new BookExport();

        for (IndustryIdentifiers ii : book.getVolumeInfo().getIndustryIdentifiers()) {
            if (ii.getType().equals("ISBN_13")) {
                be.setIsbn(ii.getIdentifier());
                break;
            }
        }
        if (be.getIsbn() == null)
            be.setIsbn(book.getId());

        long publishedDate;
        SimpleDateFormat sd;
        Date d;

        if (book.getVolumeInfo().getPublishedDate() == null) {
            publishedDate = 0;
        } else if (book.getVolumeInfo().getPublishedDate().length() == 4) {
            sd = new SimpleDateFormat("yyyy");
            d = sd.parse(book.getVolumeInfo().getPublishedDate());
            publishedDate = d.getTime();
            if(publishedDate < 0)
               publishedDate = 0;

        } else {
            sd = new SimpleDateFormat("yyyy-MM-dd");
            d = sd.parse(book.getVolumeInfo().getPublishedDate());
            publishedDate = d.getTime();
            if(publishedDate < 0)
                publishedDate = 0;

        }

        be.setAuthors(book.getVolumeInfo().getAuthors());
        be.setTitle(book.getVolumeInfo().getTitle());
        be.setSubtitle(book.getVolumeInfo().getSubtitle());
        be.setPublisher(book.getVolumeInfo().getPublisher());
        be.setPublishedDate(publishedDate);
        be.setDescription(book.getVolumeInfo().getDescription());
        be.setPageCount(book.getVolumeInfo().getPageCount());
        be.setThumbnailUrl(book.getVolumeInfo().getImageLinks().getThumbnail());
        be.setLanguage(book.getVolumeInfo().getLanguage());
        be.setPreviewLink(book.getVolumeInfo().getPreviewLink());
        be.setAverageRating(book.getVolumeInfo().getAverageRating());
        be.setAuthors(book.getVolumeInfo().getAuthors());
        be.setCategories(book.getVolumeInfo().getCategories());

        return be;
    }
}
