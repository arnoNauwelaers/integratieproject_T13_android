package be.kdg.t13.politiekebarometer.view.dashboard;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardFragment extends Fragment {
    private Unbinder unbinder;

    public DashboardFragment() {

    }
    public static DashboardFragment newInstance() {
        Bundle args = new Bundle();
        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(!UserManager.isLoggedIn()) {
            UserManager.redirectToLogin((MainActivity)getActivity());
        }
        View view = inflater.inflate(UserManager.getInstance().isLoggedIn()?R.layout.fragment_dashboard:R.layout.fragment_denied, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
