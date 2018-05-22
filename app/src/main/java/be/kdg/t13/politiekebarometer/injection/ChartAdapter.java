package be.kdg.t13.politiekebarometer.injection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.anychart.Chart;

import java.util.List;

import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.model.Notification;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bague on 28/04/2018.
 */
public class ChartAdapter extends RecyclerView.Adapter<ChartViewHolder> {
    private Context context;
    private List<Chart> charts;

    public ChartAdapter(Context context, List<Chart> charts) {
        this.context = context;
        this.charts = charts;
    }
    @Override
    public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chart, parent, false);
        return new ChartViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ChartViewHolder holder, final int position) {
        Chart chart = charts.get(position);
        holder.title.setText("Grafiek");
        holder.chart.setChart(chart);
    }
    @Override
    public int getItemCount() {
        return charts.size();
    }
}
