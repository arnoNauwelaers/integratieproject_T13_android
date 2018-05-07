package be.kdg.t13.politiekebarometer.view.search;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchFragment extends Fragment {
    @BindView(R.id.search_value) TextView searchText;
    private Unbinder unbinder;
    private String searchValue;

    public SearchFragment() {

    }
    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        searchValue = getArguments().getString("searchValue");
        searchText.setText(searchValue);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
