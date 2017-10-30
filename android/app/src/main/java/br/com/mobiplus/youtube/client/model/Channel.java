package br.com.mobiplus.youtube.client.model;

import java.io.Serializable;

import br.com.mobiplus.simplerecylerview.annotations.ImageAdapter;
import br.com.mobiplus.simplerecylerview.annotations.LayoutAdapter;
import br.com.mobiplus.simplerecylerview.annotations.TextAdapter;
import br.com.mobiplus.youtube.client.R;

/**
 * Created by luisfernandez on 21/10/17.
 */
@LayoutAdapter(layoutResId = R.layout.item_channel_list)
public class Channel implements Serializable {

    private String name;
    private String imageAvatar;

    @TextAdapter(resId = R.id.textChannelName)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ImageAdapter(resId = R.id.imageChannelAvatar)
    public String getImageAvatar() {
        return imageAvatar;
    }

    public void setImageAvatar(String imageAvatar) {
        this.imageAvatar = imageAvatar;
    }
}
