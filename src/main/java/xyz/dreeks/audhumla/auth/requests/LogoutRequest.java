package xyz.dreeks.audhumla.auth.requests;

public class LogoutRequest {

    public String username;
    public String password;

    public LogoutRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
