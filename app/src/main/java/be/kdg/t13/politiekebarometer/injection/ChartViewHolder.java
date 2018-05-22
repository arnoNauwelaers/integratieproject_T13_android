package be.kdg.t13.politiekebarometer.injection;

/**
 * Created by bague on 28/04/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.anychart.AnyChartView;

import be.kdg.t13.politiekebarometer.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartViewHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.title) public TextView title;
    @BindView(R.id.chart) public AnyChartView chart;

    public ChartViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
