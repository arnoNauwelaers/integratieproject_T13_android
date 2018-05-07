package be.kdg.t13.politiekebarometer.model;

/**
 * Created by bague on 27/04/2018.
 */

public class Notification {
    private int id;
    private String message;

    public Notification(int id, String message) {
        setId(id);
        setMessage(message);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
