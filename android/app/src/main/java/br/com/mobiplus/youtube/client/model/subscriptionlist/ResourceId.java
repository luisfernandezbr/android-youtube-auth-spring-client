
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResourceId implements Serializable
{

    @SerializedName("channelId")
    @Expose
    private String channelId;
    @SerializedName("kind")
    @Expose
    private String kind;
    private final static long serialVersionUID = -1019763433901349812L;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

}
