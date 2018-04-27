package be.kdg.t13.politiekebarometer.model;

/**
 * Created by bague on 27/04/2018.
 */

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private Notification[] notifications;

    public User(int id, String username, String password, String email, Notification[] notifications) {
        setId(id);
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setNotifications(notifications);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Notification[] getNotifications() {
        return notifications;
    }

    public void setNotifications(Notification[] notifications) {
        this.notifications = notifications;
    }
}
