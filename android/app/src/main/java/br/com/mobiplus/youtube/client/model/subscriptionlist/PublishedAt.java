
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublishedAt implements Serializable
{

    @SerializedName("value")
    @Expose
    private Long value;
    @SerializedName("dateOnly")
    @Expose
    private Boolean dateOnly;
    @SerializedName("timeZoneShift")
    @Expose
    private Long timeZoneShift;
    private final static long serialVersionUID = 7676400353121815890L;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getDateOnly() {
        return dateOnly;
    }

    public void setDateOnly(Boolean dateOnly) {
        this.dateOnly = dateOnly;
    }

    public Long getTimeZoneShift() {
        return timeZoneShift;
    }

    public void setTimeZoneShift(Long timeZoneShift) {
        this.timeZoneShift = timeZoneShift;
    }

}
