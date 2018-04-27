package be.kdg.t13.politiekebarometer.view.login;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import be.kdg.t13.politiekebarometer.utils.ApiManager;
import be.kdg.t13.politiekebarometer.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginFragment extends Fragment {
    @BindView(R.id.loginButton) Button btnLogin;
    @BindView(R.id.loginName) EditText etLogin;
    @BindView(R.id.loginPassword) EditText etPassword;
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

    @OnClick(R.id.loginButton)
    public void submit(View view) {
        ApiManager.login(etLogin.getText().toString(), etPassword.getText().toString());
    }
}