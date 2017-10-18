package br.com.mobiplus.youtube.client.http;

import java.util.concurrent.TimeUnit;

import br.com.mobiplus.youtube.client.model.AuthDTO;
import br.com.mobiplus.youtube.client.model.subscriptionlist.SubscriptionList;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFacade {

    private static final String BASE_URL = "http://192.168.100.149:8080";

    private Retrofit retrofit;
    private StadiumsService service;

    public RetrofitFacade() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(this.getClient())
                .build();

        this.service = this.retrofit.create(StadiumsService.class);
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(this.getLoggingInterceptor())
                .build();
    }

    private Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    public void listSubscribedChannels(AuthDTO authDTO, Callback<SubscriptionList> callback) {
        Call<SubscriptionList> call = this.service.listStadiums(authDTO);
        call.enqueue(callback);
    }
}
