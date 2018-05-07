package be.kdg.t13.politiekebarometer.view.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.kdg.t13.politiekebarometer.R;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by bague on 29/04/2018.
 */

public class ProfileFragment extends Fragment {
    private Unbinder unbinder;

    public ProfileFragment() {

    }
    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

