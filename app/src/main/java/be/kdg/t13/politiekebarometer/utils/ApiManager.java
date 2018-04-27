package be.kdg.t13.politiekebarometer.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import be.kdg.t13.politiekebarometer.service.PolitiekeBarometerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bague on 27/04/2018.
 */

public class ApiManager {
    private static ApiManager instance = null;
    private static String APILINK = "https://localhost:44330/api/Basic/";

    private static Retrofit retrofit;
    private static PolitiekeBarometerService service;

    protected ApiManager() { }
    public static ApiManager getInstance() {
        if(instance == null) {
            instance = new ApiManager();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
            retrofit = new Retrofit.Builder().baseUrl(APILINK).addConverterFactory(GsonConverterFactory.create(gson)).build();
            service = retrofit.create(PolitiekeBarometerService.class);
        }
        return instance;
    }

    public static int login(String username, String password) {
        final int[] userId = new int[1];
        Call<Integer> call = service.login(username, password);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                userId[0] = response.body();
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                userId[0] = -1;
            }
        });
        return userId[0];
    }
}
