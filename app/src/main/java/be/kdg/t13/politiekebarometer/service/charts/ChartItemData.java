package be.kdg.t13.politiekebarometer.service.charts;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChartItemData {
    @SerializedName("Id")
    public int id;
    @SerializedName("Data")
    public List<Item> data;
}
