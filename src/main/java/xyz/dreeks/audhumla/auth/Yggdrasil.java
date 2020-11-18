package xyz.dreeks.audhumla.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import xyz.dreeks.audhumla.Main;
import xyz.dreeks.audhumla.auth.requests.*;
import xyz.dreeks.audhumla.auth.responses.AuthenticateResponse;
import xyz.dreeks.audhumla.auth.responses.Error;
import xyz.dreeks.audhumla.auth.responses.RefreshResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Inspired by github.com/Kronos666/mclaunch-util-lib
 * More info about Yggdrasil: https://wiki.vg/Authentication
 */
public class Yggdrasil {

    private YggdrasilAgent agent;
    private static boolean debug = true;

    public Yggdrasil(YggdrasilAgent agt) {
        this.agent = agt;
    }

    /**
     * Authenticate an user with his username and password and a previously acquired accessToken
     *
     * @param username
     * @param password
     * @param clientToken
     *
     * @return The appropriated response object
     *
     * @throws IOException
     * @throws Error
     */
    public AuthenticateResponse authenticate(String username, String password, String clientToken) throws IOException, Error {
        AuthenticateRequest authRq = (new AuthenticateRequest(username, password, clientToken)).setAgent(this.agent);
        return this.send(authRq, "authenticate", AuthenticateResponse.class);
    }

    /**
     * Authenticate an user with his username and password
     *
     * @param username
     * @param password
     *
     * @return The appropriated response object
     *
     * @throws IOException
     * @throws Error
     */
    public AuthenticateResponse authenticate(String username, String password) throws IOException, Error {
        AuthenticateRequest authRq = (new AuthenticateRequest(username, password)).setAgent(this.agent);
        return this.send(authRq, "authenticate", AuthenticateResponse.class);
    }

    /**
     * Refresh an access token, provided access token gets invalidated and a new one is returned
     *
     * @param accessToken
     * @param clientToken
     *
     * @return The appropriated response object
     *
     * @throws IOException
     * @throws Error
     */
    public RefreshResponse refresh(String accessToken, String clientToken) throws IOException, Error {
        RefreshRequest req = new RefreshRequest(accessToken, clientToken);
        return send(req, "refresh", RefreshResponse.class);
    }

    /**
     * Check if an access token is valid
     *
     * @param accessToken
     *
     * @throws IOException
     * @throws Error
     */
    public void validate(String accessToken) throws IOException, Error {
        ValidateRequest req = new ValidateRequest(accessToken);
        send(req, "validate", null);
    }

    /**
     * Invalidate all the access tokens of the provided account (username+password)
     *
     * @param username
     * @param password
     *
     * @throws IOException
     * @throws Error
     */
    public void signout(String username, String password) throws IOException, Error {
        LogoutRequest req = new LogoutRequest(username, password);
        send(req, "signout", null);
    }

    /**
     * Invalidate an access token
     *
     * @param accessToken
     * @param clientToken
     *
     * @throws IOException
     * @throws Error
     */
    public void invalidate(String accessToken, String clientToken) throws IOException, Error {
        InvalidateRequest req = new InvalidateRequest(accessToken, clientToken);
        send(req, "invalidate", null);
    }

    /**
     * Internal use only, build request to the yggdrasil authentication server
     */
    private <T> T send(Object data, String route, Class<T> responseClass) throws IOException, Error {
        long currentTime = System.currentTimeMillis();
        Gson gson = new Gson();

        // Serialize the request in json
        String request = gson.toJson(data);

        if(debug) {
            System.out.println(String.format("[%s] request:  %s", currentTime, request));
        }

        // Url and data
        URL url = new URL(Main.config.yggdrasilURL + (Main.config.yggdrasilURL.endsWith("/") ? "" : "/") + route);
        byte[] postDataByte = request.getBytes();

        // Open the connection and specify the headers
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", Integer.toString(postDataByte.length));
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        // Write the data
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(postDataByte);
        wr.flush();
        wr.close();

        // True if no problem, false otherwise
        boolean status = String.valueOf(connection.getResponseCode()).startsWith("2");

        BufferedReader br = new BufferedReader(new InputStreamReader(status ? connection.getInputStream() : connection.getErrorStream()));
        String response = br.readLine();

        if(debug) {
            System.out.println(String.format("[%s] response: %s", currentTime, response));
        }

        if(responseClass != null && (response == null || response.isEmpty())) {
            throw new IOException("Empty response");
        }

        if(status) {
            return responseClass == null ? null : gson.fromJson(response, responseClass);
        }
        else {
            throw Main.gson.fromJson(response, Error.class);
        }
    }
}
