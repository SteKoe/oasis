package de.stekoe.idss.page.component;

import org.apache.wicket.markup.repeater.data.DataView;

public class PaginationInfoLabel {

    private final long itemsPerPage;
    private final long pages;
    private final long totalElements;
    private long currentPage;

    public PaginationInfoLabel(long itemsPerPage, long pages, long totalElements, long currentPage) {
        this.itemsPerPage = itemsPerPage;
        this.pages = pages;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
    }

    public PaginationInfoLabel(DataView dataView) {
        this.itemsPerPage = dataView.getItemsPerPage();
        this.pages = dataView.getPageCount();
        this.totalElements = dataView.getItemCount();
        this.currentPage = dataView.getCurrentPage() + 1;
    }

    public long getItemCountOnCurrentPage() {
        if(getTotalElements() == 0) {
            return 0;
        }

        if(getCurrentPage() < getPages()) {
            return getItemsPerPage();
        } else {
            long entries = getTotalElements() - ((getPages()-1) * getItemsPerPage());
            return entries;
        }
    }

    public long getItemsPerPage() {
        return itemsPerPage;
    }

    public long getPages() {
        if(pages <= 0) {
            return 1;
        } else {
            return pages;
        }
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getStart() {
        if(getTotalElements() == 0) {
            return 0;
        }

        if(getCurrentPage() == 1) {
            return 1;
        } else {
            return (getCurrentPage() - 1) * getItemsPerPage() + 1;
        }
    }

    public long getEnd() {
        if(getTotalElements() == 0) {
            return 0;
        }

        return getStart() + getItemCountOnCurrentPage() - 1;
    }

}
