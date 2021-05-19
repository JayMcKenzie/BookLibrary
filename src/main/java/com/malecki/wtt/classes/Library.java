package com.malecki.wtt.classes;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Library {

    private String requestedUrl;
    private Book[] items;

    public String getRequestedUrl() {
        return requestedUrl;
    }

    public void setRequestedUrl(String requestedUrl) {
        this.requestedUrl = requestedUrl;
    }


    public Book[] getItems() {
        return items;
    }

    public void setItems(Book[] items) {
        this.items = items;
    }


}
