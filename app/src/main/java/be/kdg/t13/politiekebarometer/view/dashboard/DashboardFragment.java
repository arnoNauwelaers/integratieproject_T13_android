package be.kdg.t13.politiekebarometer.view.dashboard;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.Chart;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.MainActivity;
import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.injection.ChartAdapter;
import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;
import be.kdg.t13.politiekebarometer.utils.ChartManager;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import be.kdg.t13.politiekebarometer.view.login.LoginFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DashboardFragment extends Fragment {
    @Nullable @BindView(R.id.chartsDash) RecyclerView chartsView;
    private List<SimpleChart> charts;
    private ChartAdapter chartAdapter;
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
        if(UserManager.getInstance().isLoggedIn()) {
            charts = new ArrayList<>();
            chartAdapter = new ChartAdapter(this.getContext(), charts);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            chartsView.setLayoutManager(mLayoutManager);
            chartsView.setItemAnimator(new DefaultItemAnimator());
            chartsView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
            chartsView.setAdapter(chartAdapter);
            loadCharts();
        }
        return view;
    }

    private void loadCharts() {
        charts.clear();
        charts.addAll(ChartManager.getDashboardCharts());
        chartAdapter.notifyDataSetChanged();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
