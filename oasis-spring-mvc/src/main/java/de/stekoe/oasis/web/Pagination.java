package de.stekoe.oasis.web;

import java.io.Serializable;

public class Pagination implements Serializable {
    private final long count;
    private final int elementsPerPage;
    private final int currentPage;
    private final int pages;

    public Pagination(long count, int elementsPerPage, int currentPage) {
        this.count = count;
        this.elementsPerPage = elementsPerPage;
        this.currentPage = currentPage;
        this.pages = (int)(count/elementsPerPage);
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
