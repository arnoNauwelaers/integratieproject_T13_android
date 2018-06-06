package be.kdg.t13.politiekebarometer.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.model.User;
import be.kdg.t13.politiekebarometer.service.PolitiekeBarometerService;
import be.kdg.t13.politiekebarometer.view.denied.DeniedFragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bague on 29/04/2018.
 */

public class UserManager {
    private static UserManager instance = null;

    private static User user;
    private static List<Notification> notifications;

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
            notifications = new ArrayList<>();
            //updateNotifications();
            notifications.add(new Notification(0, "Bart de Wever is #1 trending."));
            notifications.add(new Notification(0, "Theo Francken heeft de afgelopen 48 uur 12 keer het woord immigratie in zijn tweets gebruikt."));
        }
        return instance;
    }

    public static User currentUser() {
        return user;
    }

    public static boolean isLoggedIn() {
        return user != null;
    }

    public static void finishRequestToken(MainActivity a) {
        String token = ApiManager.getInstance().getToken();
        if(token != null && !token.isEmpty()) {
            ApiManager.getInstance().setUserInfo(a);
            saveTokenToDevice(a);
        }else{
            user = null;
        }
    }

    public static void saveTokenToDevice(MainActivity a) {
        SharedPreferences settings = a.getSharedPreferences("POLITIEKE_BAROMETER", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("ACCESS_TOKEN", ApiManager.getInstance().getToken());
        editor.apply();
    }

    public static void getTokenFromDevice(MainActivity a) {
        SharedPreferences settings = a.getSharedPreferences("POLITIEKE_BAROMETER", 0);
        ApiManager.setToken(settings.getString("ACCESS_TOKEN", "000"), a);
        if(ApiManager.getToken() != null && !ApiManager.getToken().isEmpty() && !ApiManager.getToken().equals("000")) {
            ApiManager.getInstance().setUserInfo(a);
        }
    }

    public static void logOut(MainActivity a) {
        user = null;
        ApiManager.getInstance().resetToken(a);
    }

    public static void redirectToLogin(MainActivity a) {
        if(a.CURRENT_FRAGMENT.compareTo(DeniedFragment.class.getName())!=0) {
            a.CURRENT_FRAGMENT = DeniedFragment.class.getName();
            a.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fContent, DeniedFragment.newInstance(), DeniedFragment.class.getName())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    public static void updateNotifications() {
        //notifications = ApiManager.getInstance().getNotifications();
    }

    public static List<Notification> getNotifications() {
        updateNotifications();
        return notifications;
    }

    public static void setUser(User u) {
        user = u;
    }
}
