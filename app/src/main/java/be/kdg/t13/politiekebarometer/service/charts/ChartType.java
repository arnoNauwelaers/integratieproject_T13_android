package be.kdg.t13.politiekebarometer.service.charts;

public enum ChartType {
    bar(0),
    radar(1),
    line(2),
    pie(3),
    polarArea(4);

    private final int id;
    ChartType(final int id) {
        this.id = id;
    }
    public int toInt() {
        return this.id;
    }
}
