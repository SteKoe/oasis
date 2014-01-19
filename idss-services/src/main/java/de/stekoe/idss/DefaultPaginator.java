package de.stekoe.idss;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultPaginator<T> implements Paginator<T> {
    private List<T> list = new ArrayList<T>();
    private int totalEntries;
    private int currentPage;


    @Override
    public void setResultList(List<T> list) {
        this.list = list;
    }

    @Override
    public List<T> getResultList() {
        return this.list;
    }

    @Override
    public void setTotalEntries(int totalEntries) {
        this.totalEntries = totalEntries;
    }

    @Override
    public int getTotalEntries() {
        return this.totalEntries;
    }

    @Override
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public int getCurrentPage() {
        return this.currentPage;
    }
}
