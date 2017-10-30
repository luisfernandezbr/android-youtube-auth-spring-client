package br.com.mobiplus.spring.youtubeclient.controller;

import br.com.mobiplus.spring.youtubeclient.model.AuthDTO;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by luisfernandez on 14/10/17.
 */
@RestController
public class YoutubeSubscriptionController {

    private static final String CLIENT_SECRET_FILE = "src/main/resources/client_secret_hidden_google_cloud_details.apps.googleusercontent.com.json";

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    
    String refreshToken;

    @PostMapping("/api/subscriptions")
    @ResponseBody()
    DeferredResult<ResponseEntity<SubscriptionListResponse>> listSubscriptions(@Valid @RequestBody AuthDTO authDTO, Errors errors) {
        final DeferredResult<ResponseEntity<SubscriptionListResponse>> deferredResult = new DeferredResult<>();

        try {
            SubscriptionListResponse subscriptionListResponse = this.requestLogin(authDTO.getServerAuthCode());
            ResponseEntity<SubscriptionListResponse> responseEntity = new ResponseEntity<>(subscriptionListResponse, null, HttpStatus.OK);
            deferredResult.setResult(responseEntity);

            return deferredResult;
        } catch (IOException e) {
            e.printStackTrace();
            deferredResult.setErrorResult("" + e.getMessage());
        }

        return deferredResult;
    }

    private SubscriptionListResponse requestLogin(String serverAuthCode) throws IOException {
        TokenRequest tokenRequest = this.getLoginTokenRequest(serverAuthCode);

        GoogleTokenResponse tokenResponse = (GoogleTokenResponse) tokenRequest.execute();
        String accessToken = tokenResponse.getAccessToken();
        refreshToken = tokenResponse.getRefreshToken();

        // Use access token to call API
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        return this.getSubscriptionListResponse(credential);
    }

    private TokenRequest getLoginTokenRequest(String authCode) throws IOException {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, this.getFileReader());

        String clientId = clientSecrets.getDetails().getClientId();
        String clientSecret = clientSecrets.getDetails().getClientSecret();

        String tokenServerEncodedUrl = "https://www.googleapis.com/oauth2/v4/token";
        String redirectUri = this.getRedirectUri(clientSecrets);

        TokenRequest tokenRequest = this.getLoginTokenRequest(authCode, JSON_FACTORY, HTTP_TRANSPORT, clientId, clientSecret, tokenServerEncodedUrl, redirectUri);
        tokenRequest.set("access_type", "offline");
        return tokenRequest;
    }

    /**
     * Specify the same redirect URI that you use with your web
     * app. If you don't have a web version of your app, you can
     * specify an empty string.
     */
    private String getRedirectUri(GoogleClientSecrets clientSecrets) {
        return clientSecrets.getDetails().getRedirectUris().get(0);
    }

    private FileReader getFileReader() throws FileNotFoundException {
        return new FileReader(CLIENT_SECRET_FILE);
    }

    private GoogleAuthorizationCodeTokenRequest getLoginTokenRequest(String authCode, JsonFactory defaultInstance, HttpTransport transport, String clientId, String clientSecret, String tokenServerEncodedUrl, String redirectUri) {
        return new GoogleAuthorizationCodeTokenRequest(
                transport,
                defaultInstance,
                tokenServerEncodedUrl,
                clientId,
                clientSecret,
                authCode,
                redirectUri);
    }

    private SubscriptionListResponse getSubscriptionListResponse(GoogleCredential credential) throws IOException {
        YouTube youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("youtube-cmdline-listSubscription-sample")
                .build();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("part", "snippet,contentDetails");
        parameters.put("mine", "true");

        YouTube.Subscriptions.List subscriptionsListMySubscriptionsRequest =
                youtube.subscriptions()
                        .list(parameters.get("part").toString());
        if (parameters.containsKey("mine") && parameters.get("mine") != "") {
            boolean mine = (parameters.get("mine") == "true") ? true : false;
            subscriptionsListMySubscriptionsRequest.setMine(mine);
        }

        subscriptionsListMySubscriptionsRequest.setMaxResults(50L);

        SubscriptionListResponse response = subscriptionsListMySubscriptionsRequest.execute();
        System.out.println(response);
        return response;
    }
}
