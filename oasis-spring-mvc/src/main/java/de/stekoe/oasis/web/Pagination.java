package de.stekoe.oasis.web;

import java.io.Serializable;

public class Pagination implements Serializable {
    private final int currentPage;
    private final int pages;
    private double progress = 0L;

    public Pagination(long count, int elementsPerPage, int currentPage) {
        this.pages = (int) Math.ceil(count * 1.0 / elementsPerPage * 1.0);

        if(currentPage > pages) {
            this.currentPage = pages;
        } else if(currentPage < 0) {
            this.currentPage = 0;
        } else {
            this.currentPage = currentPage;
        }

        double curPage = this.currentPage+1.0;
        this.progress = curPage/count;
    }

    public double getProgress() {
        return this.progress;
    }

    public int getPages() {
        return pages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
