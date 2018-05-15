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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.model.User;
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
    @BindView(R.id.loginSpinner) ProgressBar spinner;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.txtError) TextView txtError;

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
        spinner.setVisibility(View.GONE);
        txtError.setVisibility(View.GONE);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        ApiManager.getInstance().requestToken(etUsername.getText().toString(), etPassword.getText().toString(), (MainActivity)getActivity(), this);
        loggingIn();
        MainActivity.setLoading(true);
    }

    public void loggingIn() {
        btnLogin.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);
    }
    public void showForm(boolean error) {
        if(error) {
            txtError.setVisibility(View.VISIBLE);
        }
        btnLogin.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
    }
    public static void finishLogin(MainActivity a, LoginFragment frag) {
        String token = ApiManager.getInstance().getToken();
        UserManager.finishRequestToken();
        if(token == null || token.isEmpty()) {
            frag.showForm(true);
        }
        MainActivity.setLoading(false);
    }
}