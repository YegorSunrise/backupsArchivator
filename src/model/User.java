package model;

import java.time.LocalDate;

public class User {

    private String username;
    private LocalDate lastActive;
    private boolean isActive;


    public User(String username, LocalDate lastActive) {
        this.username = username;
        this.lastActive = lastActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDate lastActive) {
        this.lastActive = lastActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", lastActive=" + lastActive +
                ", isActive=" + isActive +
                '}';
    }
}
