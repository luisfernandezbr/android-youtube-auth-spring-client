package br.com.mobiplus.spring.youtubeclient;

import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import com.google.api.client.auth.oauth2.TokenRequest;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SubscriptionListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by luisfernandez on 14/10/17.
 */
@RestController
public class AuthController {

    String CLIENT_SECRET_FILE = "src/main/resources/client_secret_hidden_google_cloud_details.apps.googleusercontent.com.json";

    String refreshToken;

    @PostMapping("/auth")
    @ResponseBody()
    DeferredResult<ResponseEntity<SubscriptionListResponse>> doLogin(@Valid @RequestBody UserDTO userDTO, Errors errors) {
        final DeferredResult<ResponseEntity<SubscriptionListResponse>> deferredResult = new DeferredResult<>();

        try {
            SubscriptionListResponse subscriptionListResponse = this.requestLogin(userDTO.getIdToken());
            ResponseEntity<SubscriptionListResponse> responseEntity = new ResponseEntity<>(subscriptionListResponse, null, HttpStatus.OK);
            deferredResult.setResult(responseEntity);

            return deferredResult;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SubscriptionListResponse requestLogin(String authCode) throws IOException {
        TokenRequest tokenRequest = this.getLoginTokenRequest(authCode);

        GoogleTokenResponse tokenResponse = (GoogleTokenResponse) tokenRequest.execute();
        String accessToken = tokenResponse.getAccessToken();
        refreshToken = tokenResponse.getRefreshToken();

        // Use access token to call API
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        return this.getSubscriptionListResponse(credential);
    }

    private TokenRequest getLoginTokenRequest(String authCode) throws IOException {

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Auth.JSON_FACTORY, this.getFileReader());

        String clientId = clientSecrets.getDetails().getClientId();
        String clientSecret = clientSecrets.getDetails().getClientSecret();

        String tokenServerEncodedUrl = "https://www.googleapis.com/oauth2/v4/token";
        String redirectUri = this.getRedirectUri(clientSecrets);

        TokenRequest tokenRequest = this.getLoginTokenRequest(authCode, Auth.JSON_FACTORY, Auth.HTTP_TRANSPORT, clientId, clientSecret, tokenServerEncodedUrl, redirectUri);
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

    @GetMapping("/authRefresh")
    @ResponseBody()
    DeferredResult<ResponseEntity<SubscriptionListResponse>> doLoginRefresh() {
        final DeferredResult<ResponseEntity<SubscriptionListResponse>> deferredResult = new DeferredResult<>();

        try {
            SubscriptionListResponse subscriptionListResponse = this.requestLoginRefresh(refreshToken);
            ResponseEntity<SubscriptionListResponse> responseEntity = new ResponseEntity<>(subscriptionListResponse, null, HttpStatus.OK);
            deferredResult.setResult(responseEntity);

            return deferredResult;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private SubscriptionListResponse requestLoginRefresh(String refreshToken) throws IOException {
        TokenRequest tokenRequest = this.getLoginRefreshTokenRequest(refreshToken);

        GoogleTokenResponse tokenResponse = (GoogleTokenResponse) tokenRequest.execute();
        String accessToken = tokenResponse.getAccessToken();

        // Use access token to call API
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);

        return this.getSubscriptionListResponse(credential);
    }

    private TokenRequest getLoginRefreshTokenRequest(String refreshToken) throws IOException {

        String tokenServerEncodedUrl = "https://www.googleapis.com/oauth2/v4/token";

        TokenRequest tokenRequest = this.getLoginRefreshTokenRequest(refreshToken, Auth.JSON_FACTORY, Auth.HTTP_TRANSPORT, new GenericUrl(
                tokenServerEncodedUrl));

        return tokenRequest;
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

    private RefreshTokenRequest getLoginRefreshTokenRequest(String refreshToken, JsonFactory defaultInstance, HttpTransport transport, GenericUrl genericUrl) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(Auth.JSON_FACTORY, this.getFileReader());

        String clientId = clientSecrets.getDetails().getClientId();
        String clientSecret = clientSecrets.getDetails().getClientSecret();

        return new GoogleRefreshTokenRequest(
                transport,
                defaultInstance,
                refreshToken,
                clientId,
                clientSecret);
    }

    private SubscriptionListResponse getSubscriptionListResponse(GoogleCredential credential) throws IOException {
        YouTube youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                .setApplicationName("youtube-cmdline-listSubscription-sample")
                .build();

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("part", "snippet,contentDetails");
        parameters.put("mine", "true");

        YouTube.Subscriptions.List subscriptionsListMySubscriptionsRequest =
                youtube.subscriptions()
                        .list(parameters.get("part").toString());
        if (parameters.containsKey("mine") && parameters.get("mine") != "") {
            boolean mine = (parameters.get("mine") == "true") ? true : false;
            subscriptionsListMySubscriptionsRequest.setMine(mine);
        }

        SubscriptionListResponse response = subscriptionsListMySubscriptionsRequest.execute();
        System.out.println(response);
        return response;
    }

    private void validateIdToken(String idTokenString) throws GeneralSecurityException, IOException {
JacksonFactory jacksonFactory = new JacksonFactory();


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new HttpTransport() {
            @Override
            protected LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return null;
            }
        }, jacksonFactory)
                .setAudience(Collections.singletonList(""))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString);

        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
        }
    }
}
