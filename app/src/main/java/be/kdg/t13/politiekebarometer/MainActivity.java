package be.kdg.t13.politiekebarometer;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;

import java.util.HashMap;
import java.util.Map;

import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.dashboard.DashboardFragment;
import be.kdg.t13.politiekebarometer.view.home.HomeFragment;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import be.kdg.t13.politiekebarometer.view.notifications.NotificationsFragment;
import be.kdg.t13.politiekebarometer.view.profile.ProfileFragment;
import be.kdg.t13.politiekebarometer.view.search.SearchFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fContent) View content;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    public String CURRENT_FRAGMENT = "";
    private String previousSearch = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(UserManager.getInstance().isLoggedIn()) {
            getMenuInflater().inflate(R.menu.app_bar_logged_in, menu);
        }else{
            getMenuInflater().inflate(R.menu.app_bar, menu);
        }
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(CURRENT_FRAGMENT.compareTo(LoginFragment.class.getName())==0||CURRENT_FRAGMENT.compareTo(NotificationsFragment.class.getName())==0) {
            searchItem.setVisible(false);
        }else{
            searchItem.setVisible(true);
        }
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {return true;}
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!(previousSearch.compareTo(query)==0)) {
                    previousSearch = query;
                    getSupportActionBar().setTitle("Zoeken");
                    Map<String, String> args = new HashMap<>();
                    args.put("searchValue", query);
                    changeFragmentWithArgs(SearchFragment.newInstance(), args);
                }
                item.collapseActionView();
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_login:
                if(UserManager.getInstance().isLoggedIn()) {
                    getSupportActionBar().setTitle("Mijn profiel");
                    changeFragment(ProfileFragment.newInstance());
                }else{
                    getSupportActionBar().setTitle("Inloggen");
                    changeFragment(LoginFragment.newInstance());
                }
                return true;
            case R.id.action_logout:
                UserManager.getInstance().logOut();
                invalidateOptionsMenu();
                findViewById(R.id.navigation).findViewById(R.id.navigation_home).performClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addEventHandlers();
        finishCreate();
    }

    private void addEventHandlers() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportActionBar().setTitle("Home");
                        changeFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_dashboard:
                        getSupportActionBar().setTitle("Mijn Dashboard");
                        changeFragment(DashboardFragment.newInstance());
                        return true;
                    case R.id.navigation_notifications:
                        getSupportActionBar().setTitle("Meldingen");
                        changeFragment(NotificationsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    private void finishCreate() {
        getSupportActionBar().setTitle("Home");
        changeFragment(HomeFragment.newInstance());
    }

    private void changeFragment(Fragment f) {
        CURRENT_FRAGMENT = f.getClass().getName();
        getSupportFragmentManager().beginTransaction()
                .replace(content.getId(), f, f.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        invalidateOptionsMenu();
    }
    private void changeFragment(Fragment f, boolean override) {
        if(override) {
            if(CURRENT_FRAGMENT.compareTo(f.getClass().getName())==0) {
                return;
            }
        }
        changeFragment(f);
    }

    private void changeFragmentWithArgs(Fragment f, Map<String, String> args) {
        Bundle arguments = new Bundle();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            arguments.putString(entry.getKey() , entry.getValue());
        }
        f.setArguments(arguments);
        changeFragment(f);
    }
    private void changeFragment(Fragment f, Map<String, String> args, boolean override) {
        if(override) {
            if(CURRENT_FRAGMENT.compareTo(f.getClass().getName())==0) {
                return;
            }
        }
        changeFragmentWithArgs(f, args);
    }
}
