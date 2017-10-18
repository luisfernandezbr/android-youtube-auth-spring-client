
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PageInfo implements Serializable
{

    @SerializedName("resultsPerPage")
    @Expose
    private Long resultsPerPage;
    @SerializedName("totalResults")
    @Expose
    private Long totalResults;
    private final static long serialVersionUID = -4167869940719099676L;

    public Long getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(Long resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

}
