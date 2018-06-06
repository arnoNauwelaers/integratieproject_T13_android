package be.kdg.t13.politiekebarometer.view.home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Chart;
import com.anychart.anychart.ChartsRadar;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.injection.ChartAdapter;
import be.kdg.t13.politiekebarometer.injection.NotificationAdapter;
import be.kdg.t13.politiekebarometer.injection.NotificationRecyclerTouchHelper;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;
import be.kdg.t13.politiekebarometer.utils.ApiManager;
import be.kdg.t13.politiekebarometer.utils.ChartManager;
import be.kdg.t13.politiekebarometer.utils.UserManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends Fragment {
    @BindView(R.id.chartsHome) RecyclerView chartsView;
    private List<SimpleChart> charts;
    private ChartAdapter chartAdapter;
    private Unbinder unbinder;

    public HomeFragment() {

    }
    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        charts = new ArrayList<>();
        chartAdapter = new ChartAdapter(this.getContext(), charts);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        chartsView.setLayoutManager(mLayoutManager);
        chartsView.setItemAnimator(new DefaultItemAnimator());
        chartsView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        chartsView.setAdapter(chartAdapter);
        loadCharts();
        return view;
    }

    private void loadCharts() {
        charts.clear();
        charts.addAll(ChartManager.getHomeCharts());
        chartAdapter.notifyDataSetChanged();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
