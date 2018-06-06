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

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.Chart;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.ValueDataEntry;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import be.kdg.t13.politiekebarometer.R;
import be.kdg.t13.politiekebarometer.model.Notification;
import be.kdg.t13.politiekebarometer.service.charts.ChartItemData;
import be.kdg.t13.politiekebarometer.service.charts.Item;
import be.kdg.t13.politiekebarometer.service.charts.SimpleChart;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bague on 28/04/2018.
 */
public class ChartAdapter extends RecyclerView.Adapter<ChartViewHolder> {
    private Context context;
    private List<SimpleChart> charts;

    public ChartAdapter(Context context, List<SimpleChart> charts) {
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
        SimpleChart chart = charts.get(position);

        List<PieEntry> entries = new ArrayList<>();
        int i = 0;
        for(ChartItemData chartData : chart.data) {
            for(Item item : chartData.data) {
                if(i < 10 && item.amount > 5) {
                    entries.add(new PieEntry(item.amount, item.name));
                    i++;
                }
            }
        }
        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataset);

        holder.chart.setData(data);
        Description desc = new Description();
        desc.setText(chart.name);
        holder.chart.setDescription(desc);
        holder.chart.invalidate();
        holder.title.setText("Grafiek");
    }
    @Override
    public int getItemCount() {
        return charts.size();
    }
}
