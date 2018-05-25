package be.kdg.t13.politiekebarometer.service.charts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SimpleChart {
    @SerializedName("Name")
    public String name;
    @SerializedName("Type")
    public int type;
    @SerializedName("Data")
    public List<ChartItemData> data;
}

