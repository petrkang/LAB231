/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author Peter
 */
public class APIWrapper {

    private static final String APP_ID = "669670516949123";
    private static final String APP_SECRET = "be074028d952d94791850503cd2fe3c7";
    private static final String REDIRECT_URL = "http://localhost:8084/SE130380_J3LP0008/LoginFacebook";
    private String accessToken;
    private final Gson gson;

    public APIWrapper() {
        gson = new Gson();
    }

    public static String getDialogLink() {
        String dialogLink = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";
        return String.format(dialogLink, APP_ID, REDIRECT_URL);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken(String code) {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, APP_ID, APP_SECRET, REDIRECT_URL, code);
        String result = NetUtils.getResult(accessTokenLink);
        JsonElement json = gson.fromJson(result, JsonElement.class);
        JsonObject token = json.getAsJsonObject();
        return token.get("access_token").toString().replace("\"", "");
    }

    public JsonElement getUserDTO() {
        String infoUrl = "https://graph.facebook.com/me?fields=id,name,first_name,last_name&access_token=%s";
        infoUrl = String.format(infoUrl, this.accessToken);

        String result = NetUtils.getResult(infoUrl);
        
        JsonElement json = gson.fromJson(result, JsonElement.class);
        return json;
    }
}
