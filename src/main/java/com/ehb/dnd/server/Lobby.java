package com.ehb.dnd.server;

public class Lobby {
    private int id;
    private boolean isRunning;

    public Lobby(int id) {
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
        Lobby other = (Lobby) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
