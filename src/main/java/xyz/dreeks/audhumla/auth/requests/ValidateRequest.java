package xyz.dreeks.audhumla.auth.requests;

public class ValidateRequest {
    public String accessToken;

    public ValidateRequest(String s) {
        this.accessToken = s;
    }

}
