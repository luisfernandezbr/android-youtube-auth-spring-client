
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails implements Serializable
{

    @SerializedName("activityType")
    @Expose
    private String activityType;
    @SerializedName("newItemCount")
    @Expose
    private Long newItemCount;
    @SerializedName("totalItemCount")
    @Expose
    private Long totalItemCount;
    private final static long serialVersionUID = -7318382071130705312L;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public Long getNewItemCount() {
        return newItemCount;
    }

    public void setNewItemCount(Long newItemCount) {
        this.newItemCount = newItemCount;
    }

    public Long getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(Long totalItemCount) {
        this.totalItemCount = totalItemCount;
    }

}
