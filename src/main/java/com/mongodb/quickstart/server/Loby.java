package com.mongodb.quickstart.server;

import com.mongodb.quickstart.models.Player;
import com.mongodb.quickstart.models.Room;

import java.util.List;

public class Loby {
    private int id;
    private boolean isRunning;

    public Loby(int id) {
        this.id = id;
        this.isRunning = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Loby other = (Loby) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
