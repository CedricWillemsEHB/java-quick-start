package com.ehb.dnd.model;

import java.io.Serializable;

public class Room  implements Serializable {
    String id;
    int x;
    int y;
    boolean accessible;
    boolean goLeft;
    boolean goUp;
    boolean goRight;
    boolean goDown;
    IEvent event;

    public Room() {
        this.event = null;
    }

    public Room(int x, int y){
        this.x = x;
        this.y = y;
        this.accessible = false;
        this.goLeft = false;
        this.goUp = false;
        this.goRight = false;
        this.goDown = false;
        this.event = null;
    }

    public IEvent getEvent() {
        return event;
    }
    public void setEvent(IEvent event) {
        this.event = event;
    }
    public boolean isEventNull(IEvent event) {
        if(this.event == null) {
            this.event = event;
            return true;
        }
        return false;
    }
    public boolean checkEventAvailable() {
        if(this.event == null)
            return false;
        return true;
    }
    public boolean getAccessible(){
        return accessible;
    }
    public void setAccessible( boolean accessible){
        this.accessible = accessible;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isGoLeft() {
        return goLeft;
    }
    public void setGoLeft(boolean goLeft) {
        this.goLeft = goLeft;
    }
    public boolean isGoUp() {
        return goUp;
    }
    public void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }
    public boolean isGoRight() {
        return goRight;
    }
    public void setGoRight(boolean goRight) {
        this.goRight = goRight;
    }
    public boolean isGoDown() {
        return goDown;
    }
    public void setGoDown(boolean goDown) {
        this.goDown = goDown;
    }
    @Override
    public String toString() {
        String s;
        if(event != null){
            s = "Room [x=" + x + ", y=" + y + ", event= " + event.showEvent()+ "]";
        } else {
            s = "Room [x=" + x + ", y=" + y + "]";
        }
        return s;
    }
    public String showWays() {
        String str = "";
        if(goLeft) {
            str += "Je kan west gaan\n";
        }
        if(goUp) {
            str += "Je kan noord gaan\n";
        }
        if(goRight) {
            str += "Je kan oost gaan\n";
        }
        if(goDown) {
            str += "Je kan zuid gaan\n";
        }
        return str;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Room other = (Room) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
    public String checkRoom() {
        if(accessible) {
            return "Open";
        } else {
            return "Close";
        }
    }
}
