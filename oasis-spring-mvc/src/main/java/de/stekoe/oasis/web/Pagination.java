package de.stekoe.oasis.web;

import java.io.Serializable;

public class Pagination implements Serializable {
    private final int currentPage;
    private final int pages;

    public Pagination(long count, int elementsPerPage, int currentPage) {
        this.pages = (int) Math.ceil(count * 1.0 / elementsPerPage * 1.0);

        if(currentPage > pages) {
            this.currentPage = pages;
        } else if(currentPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = currentPage;
        }
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
