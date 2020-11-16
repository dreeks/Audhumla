package xyz.dreeks.audhumla.auth.requests;

import xyz.dreeks.audhumla.auth.YggdrasilAgent;

public class AuthenticateRequest {

    public AuthenticateRequest() {}

    public AuthenticateRequest(String username, String password, String clientToken) {
        this.username = username;
        this.password = password;
        this.clientToken = clientToken;
    }

    public AuthenticateRequest(String username, String password) {
        this(username, password, null);
    }

    public YggdrasilAgent agent;
    public String username;
    public String password;
    public String clientToken;

    public AuthenticateRequest setAgent(YggdrasilAgent agent) {
        this.agent = agent;
        return this;
    }
}
