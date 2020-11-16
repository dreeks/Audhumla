package xyz.dreeks.audhumla.auth.responses;

import xyz.dreeks.audhumla.auth.YggdrasilProfile;

import java.util.List;

public class AuthenticateResponse {

    public String accessToken;
    public String clientToken;
    public YggdrasilProfile selectedProfile;
    public List<YggdrasilProfile> availableProfiles;

}
