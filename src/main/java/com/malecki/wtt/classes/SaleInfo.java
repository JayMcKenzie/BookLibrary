package com.malecki.wtt.classes;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleInfo {

    private String country;
    private String saleability;
    private boolean isEbook;
    private String buyLink;
    private ListPrice listPrice;
    private RetailPrice retailPrice;
    private Offers[] offers;

    public Offers[] getOffers() {
        return offers;
    }

    public void setOffers(Offers[] offers) {
        this.offers = offers;
    }

    public RetailPrice getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice retailPrice) {
        this.retailPrice = retailPrice;
    }

    public ListPrice getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice listPrice) {
        this.listPrice = listPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public boolean getIsEbook() {
        return isEbook;
    }

    public void setIsEbook(boolean ebook) {
        this.isEbook = ebook;
    }
}
