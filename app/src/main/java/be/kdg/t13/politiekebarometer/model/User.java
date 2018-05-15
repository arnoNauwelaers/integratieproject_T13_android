package be.kdg.t13.politiekebarometer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bague on 27/04/2018.
 */

public class User {
    @SerializedName("Id")
    private String id;
    @SerializedName("Username")
    private String username;

    public User(String id, String username) {
        setId(id);
        setUsername(username);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ID: "+id+" - USERNAME: "+username;
    }
}
