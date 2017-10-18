
package br.com.mobiplus.youtube.client.model.subscriptionlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails implements Serializable
{

    @SerializedName("default")
    @Expose
    private Default _default;
    @SerializedName("high")
    @Expose
    private High high;
    @SerializedName("medium")
    @Expose
    private Medium medium;
    private final static long serialVersionUID = -2885554723454095749L;

    public Default getDefault() {
        return _default;
    }

    public void setDefault(Default _default) {
        this._default = _default;
    }

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

}
