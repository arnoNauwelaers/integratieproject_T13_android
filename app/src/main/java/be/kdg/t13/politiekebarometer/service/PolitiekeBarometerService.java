package be.kdg.t13.politiekebarometer.service;

import be.kdg.t13.politiekebarometer.model.Notification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bague on 27/04/2018.
 */

public interface PolitiekeBarometerService {
    @GET("GetNotifications")
    Call<Notification[]> getNotifications();

    @POST("Login")
    Call<Integer> login(@Body String username, @Body String password);
}
