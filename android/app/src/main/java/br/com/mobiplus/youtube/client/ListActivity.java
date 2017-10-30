package br.com.mobiplus.youtube.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.mobiplus.simplerecylerview.SimpleLinearRecyclerView;
import br.com.mobiplus.simplerecylerview.adapter.OnItemClickListener;
import br.com.mobiplus.youtube.client.model.Channel;
import br.com.mobiplus.youtube.client.model.subscriptionlist.Item;
import br.com.mobiplus.youtube.client.model.subscriptionlist.SubscriptionList;

public class ListActivity extends AppCompatActivity {

    public static final String EXTRA_SUBSCRIPTION_LIST = "EXTRA_SUBSCRIPTION_LIST";


    public static void start(Context context, SubscriptionList subscriptionList) {
        Intent starter = new Intent(context, ListActivity.class);
        starter.putExtra(EXTRA_SUBSCRIPTION_LIST, subscriptionList);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SubscriptionList subscriptionList = (SubscriptionList) getIntent().getExtras().getSerializable(EXTRA_SUBSCRIPTION_LIST);

        SimpleLinearRecyclerView recyclerView = findViewById(R.id.recyclerView);

        List<Channel> channelList = convert(subscriptionList.getItems());
        recyclerView.setCollection(channelList, new OnItemClickListener<Channel>() {
            @Override
            public void onItemClick(Channel channel, int resId) {
                Toast.makeText(ListActivity.this, "Clicked channel: " + channel.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Channel> convert(List<Item> subscriptionListItems) {
        List<Channel> channelList = new ArrayList<>(subscriptionListItems.size());

        for (int i = 0; i < subscriptionListItems.size(); i++) {
            Item item = subscriptionListItems.get(i);
            Channel channel = new Channel();
            channel.setName(item.getSnippet().getTitle());
            channel.setImageAvatar(item.getSnippet().getThumbnails().getHigh().getUrl());
            channelList.add(channel);
        }

        return channelList;
    }


}
