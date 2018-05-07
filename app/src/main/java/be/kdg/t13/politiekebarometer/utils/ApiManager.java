package be.kdg.t13.politiekebarometer.utils;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.model.Item;
import be.kdg.t13.politiekebarometer.model.Notification;
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

    public static List<Notification> getNotifications() {
        final List<Notification> notifications = new ArrayList<>();
        Call<Notification[]> call = service.getNotifications();
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                notifications.add(new Notification(1, response.body().toString()));
            }
            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {
                notifications.add(new Notification(1, "Error"));
            }
        });
        notifications.add(new Notification(1,"test"));
        notifications.add(new Notification(1,"bart de wever"));
        notifications.add(new Notification(1,"theo"));
        return notifications;
    }

    public static List<Item> getSearchResults() {
        final List<Item> items = new ArrayList<>();
        Call<Item[]> call = service.getSearchResults();
        call.enqueue(new Callback<Item[]>() {
            @Override
            public void onResponse(Call<Item[]> call, Response<Item[]> response) {
                String[] itemsStrings = response.body().toString().split(",");
                for(String itemString : itemsStrings) {
                    //NEW ITEM ATTRIBUTEN TOEVOEGEN
                    items.add(new Item());
                }
            }
            @Override
            public void onFailure(Call<Item[]> call, Throwable t) {
                //error
            }
        });
        items.add(new Item());
        items.add(new Item());
        items.add(new Item());
        return items;
    }
}
