package br.com.mobiplus.spring.youtubeclient.model;

/**
 * Created by luisfernandez on 14/10/17.
 */
public class AuthDTO {

    private String serverAuthCode;

    public String getServerAuthCode() {
        return serverAuthCode;
    }

    public void setServerAuthCode(String serverAuthCode) {
        this.serverAuthCode = serverAuthCode;
    }
}
