
package com.turlington.edjoin;

import java.util.List;

public class EdjoinSearchResult {

    private Search search;
    private Integer totalPages;
    private Integer totalRecords;
    private Integer totalOpenings;
    private Integer displayRecords;
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }
}
