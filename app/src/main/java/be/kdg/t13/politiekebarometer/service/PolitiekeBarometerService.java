package be.kdg.t13.politiekebarometer.service;

import java.util.List;

import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.model.User;
import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by bague on 27/04/2018.
 */

public interface PolitiekeBarometerService {
    /* <DEBUG> */
    @GET("test")
    Call<String> debugApi();
    @GET("/")
    Call<String> testConnection();
    /* </DEBUG> */

    @POST("RequestToken")
    Call<String> requestToken(@Body TokenRequest request);

    @GET("GetUserInfo")
    Call<User> getUserInfo();

    @GET("GetNotifications")
    Call<List<Notification>> getNotifications();

    @GET("Search")
    Call<List<String>> getSearchResults();

    @GET("GetHomeCharts")
    Call<List<SimpleChart>> getHomeChartData();

    @GET("GetDashboardCharts")
    Call<ResponseBody> getDashboardCharts();
}
