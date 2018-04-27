package be.kdg.t13.politiekebarometer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import be.kdg.t13.politiekebarometer.view.dashboard.DashboardFragment;
import be.kdg.t13.politiekebarometer.view.home.HomeFragment;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import be.kdg.t13.politiekebarometer.view.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity {
    private View content;
    private BottomNavigationView navigation;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem loginItem = menu.findItem(R.id.action_login);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.action_login:
                changeFragment(LoginFragment.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseViews();
        addEventHandlers();
        finishCreate();
    }

    private void initialiseViews() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        content = (View) findViewById(R.id.fContent);
    }

    private void addEventHandlers() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        changeFragment(HomeFragment.newInstance());
                        return true;
                    case R.id.navigation_dashboard:
                        changeFragment(DashboardFragment.newInstance());
                        return true;
                    case R.id.navigation_notifications:
                        changeFragment(NotificationsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    private void finishCreate() {
        changeFragment(HomeFragment.newInstance());
    }

    private void changeFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(content.getId(), f, f.getClass().getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
