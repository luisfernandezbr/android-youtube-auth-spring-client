
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet implements Serializable
{

    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("publishedAt")
    @Expose
    private PublishedAt publishedAt;
    @SerializedName("resourceId")
    @Expose
    private ResourceId resourceId;
    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
    @SerializedName("title")
    @Expose
    private String title;
    private final static long serialVersionUID = 2442345753791707695L;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PublishedAt getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(PublishedAt publishedAt) {
        this.publishedAt = publishedAt;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
