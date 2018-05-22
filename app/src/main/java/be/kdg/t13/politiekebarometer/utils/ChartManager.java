package be.kdg.t13.politiekebarometer.utils;

import com.anychart.anychart.Chart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bague on 15/05/2018.
 */

public class ChartManager {
    private static List<Chart> homeCharts = new ArrayList<>();
    private static List<Chart> dashboardCharts = new ArrayList<>();

    public static void refreshHomeCharts() {
        ApiManager.getInstance().getHomeCharts();
    }

    public static void refreshDashboardCharts() {
        ApiManager.getInstance().getDashboardCharts();
    }

    public static void setHomeCharts(List<Chart> charts) {
        homeCharts = charts;
    }

    public static void setDashboardCharts(List<Chart> charts) {
        dashboardCharts = charts;
    }

    public static List<Chart> getHomeCharts() {
        refreshHomeCharts();
        return homeCharts;
    }

    public static List<Chart> getDashboardCharts() {
        refreshDashboardCharts();
        return dashboardCharts;
    }
}
