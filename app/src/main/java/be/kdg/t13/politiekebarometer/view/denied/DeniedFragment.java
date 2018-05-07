package be.kdg.t13.politiekebarometer.view.denied;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeniedFragment extends Fragment {
    private Unbinder unbinder;

    public DeniedFragment() {

    }
    public static DeniedFragment newInstance() {
        Bundle args = new Bundle();
        DeniedFragment fragment = new DeniedFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_denied, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.denied_button)
    public void login() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fContent, LoginFragment.newInstance(), LoginFragment.class.getName())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}
