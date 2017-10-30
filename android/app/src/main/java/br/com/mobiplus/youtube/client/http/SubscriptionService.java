package br.com.mobiplus.youtube.client.http;

import br.com.mobiplus.youtube.client.model.AuthDTO;
import br.com.mobiplus.youtube.client.model.subscriptionlist.SubscriptionList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SubscriptionService {

    @POST("api/subscriptions")
    Call<SubscriptionList> listSubscriptions(@Body AuthDTO authDTO);
}
