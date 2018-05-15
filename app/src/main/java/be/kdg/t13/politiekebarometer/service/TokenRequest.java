package be.kdg.t13.politiekebarometer.service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bague on 8/05/2018.
 */

public class TokenRequest {
    @SerializedName("Username")
    String username;
    @SerializedName("Password")
    String password;

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toJSON() {
        return "{\"Username\":\""+username+"\",\"Password\":\""+password+"\"}";
    }
}
