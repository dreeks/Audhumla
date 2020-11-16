package xyz.dreeks.audhumla.auth.requests;

public class RefreshRequest {
    public String accessToken;
    public String clientToken;

    public RefreshRequest() {}

    public RefreshRequest(String accessToken, String clientToken) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
    }

}
