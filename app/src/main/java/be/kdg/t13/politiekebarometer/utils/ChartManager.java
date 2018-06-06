package be.kdg.t13.politiekebarometer.utils;

import com.anychart.anychart.Chart;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;

/**
 * Created by bague on 15/05/2018.
 */

public class ChartManager {
    private static List<SimpleChart> homeCharts = new ArrayList<>();
    private static List<SimpleChart> dashboardCharts = new ArrayList<>();

    public static void refreshHomeCharts() {
        ApiManager.getInstance().getHomeCharts();
    }

    public static void refreshDashboardCharts() {
        ApiManager.getInstance().getDashboardCharts();
    }

    public static void setHomeCharts(List<SimpleChart> charts) {
        homeCharts = charts;
    }

    public static void setDashboardCharts(List<SimpleChart> charts) {
        dashboardCharts = charts;
    }

    public static List<SimpleChart> getHomeCharts() {
        refreshHomeCharts();
        return homeCharts;
    }

    public static List<SimpleChart> getDashboardCharts() {
        refreshDashboardCharts();
        return dashboardCharts;
    }
}
