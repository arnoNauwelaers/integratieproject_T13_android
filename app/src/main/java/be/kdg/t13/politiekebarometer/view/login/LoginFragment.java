package be.kdg.t13.politiekebarometer.view.login;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import be.kdg.t13.politiekebarometer.utils.ApiManager;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.dashboard.DashboardFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {
    @BindView(R.id.etUsername) EditText etUsername;
    @BindView(R.id.etPassword) EditText etPassword;
    private Unbinder unbinder;

    public LoginFragment() {

    }
    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        UserManager.logIn(etUsername.getText().toString(), etPassword.getText().toString());
        getActivity().invalidateOptionsMenu();
        getActivity().findViewById(R.id.navigation).findViewById(R.id.navigation_dashboard).performClick();
    }
}