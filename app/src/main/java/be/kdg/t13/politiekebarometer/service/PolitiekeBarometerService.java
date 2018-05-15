package be.kdg.t13.politiekebarometer.service;

import java.util.List;
import java.util.Map;

import be.kdg.t13.politiekebarometer.model.Item;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by bague on 27/04/2018.
 */

public interface PolitiekeBarometerService {
    /*@GET("GetNotifications")
    Call<Notification[]> getNotifications();

    @GET("Search")
    //Call<Item[]> getSearchResults(@QueryMap Map<String, String> options);
    Call<Item[]> getSearchResults();

    @FormUrlEncoded
    @POST("Login")
    Call<Integer> login(@Field("Username") String username, @Field("Pssword") String password);
    */

    @POST("RequestToken")
    Call<String> requestToken(@Body TokenRequest request);

    @GET("GetUserInfo")
    Call<User> getUserInfo();

    @GET("GetNotifications")
    Call<List<Notification>> getNotifications();

    @GET("Search")
    Call<List<String>> getSearchResults();

    /* <DEBUG> */
    @GET("test")
    Call<String> debugApi();
    @GET("/")
    Call<String> testConnection();
    /* </DEBUG> */

    /*

    RequestToken
    GetUserData
    GetNotifications
    GetChartData

     */
}
