package br.com.mobiplus.youtube.client.http;

import br.com.mobiplus.youtube.client.model.AuthDTO;
import br.com.mobiplus.youtube.client.model.subscriptionlist.SubscriptionList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface StadiumsService {

    @POST("api/subscriptions")
    Call<SubscriptionList> listStadiums(@Body AuthDTO authDTO);
}
