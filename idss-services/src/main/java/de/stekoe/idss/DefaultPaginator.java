/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class
        DefaultPaginator<T> implements Paginator<T> {
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
