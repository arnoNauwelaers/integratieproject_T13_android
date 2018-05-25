package be.kdg.t13.politiekebarometer.service.charts;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;
    @SerializedName("Amount")
    public int amount;
}
