package de.stekoe.idss;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface Paginator<T> {
    void setResultList(List<T> list);
    List<T> getResultList();

    void setTotalEntries(int totalEntries);
    int getTotalEntries();

    void setCurrentPage(int currentPage);
    int getCurrentPage();
}
