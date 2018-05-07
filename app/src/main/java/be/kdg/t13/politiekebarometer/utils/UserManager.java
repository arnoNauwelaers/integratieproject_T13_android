package be.kdg.t13.politiekebarometer.utils;

import android.app.Activity;
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
            updateNotifications();
        }
        return instance;
    }

    public static User currentUser() {
        return user;
    }

    public static boolean isLoggedIn() {
        return user != null;
    }

    public static void logIn(String username, String password) {
        int id = ApiManager.getInstance().login(username, password);
        user = new User(id, username, password, "");
    }

    public static void logOut() {
        user = null;
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
        notifications = ApiManager.getInstance().getNotifications();
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }
}
