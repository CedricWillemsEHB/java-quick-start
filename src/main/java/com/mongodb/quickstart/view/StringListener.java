package com.mongodb.quickstart.view;

import java.io.IOException;
//Interface to give actions to the buttons
public interface StringListener {
    public void makeMap() throws IOException;
    public void clickNorth() throws IOException;
    public void clickSouth() throws IOException;
    public void clickEast() throws IOException;
    public void clickWest() throws IOException;
    public void clickFight() throws IOException;
    public void clickRun() throws IOException;
}
