package be.kdg.t13.politiekebarometer.service;

import java.util.Map;

import be.kdg.t13.politiekebarometer.model.Item;
import be.kdg.t13.politiekebarometer.model.Notification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by bague on 27/04/2018.
 */

public interface PolitiekeBarometerService {
    @GET("GetNotifications")
    Call<Notification[]> getNotifications();

    @GET("Search")
    Call<Item[]> getSearchResults(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST("Login")
    Call<Integer> login(@Field("username") String username, @Field("username") String password);
}
