package xyz.dreeks.audhumla.auth.requests;

public class InvalidateRequest {

    public String accessToken;
    public String clientToken;

    public InvalidateRequest(String accessToken, String clientToken) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
    }

}
