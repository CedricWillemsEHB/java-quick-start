package com.mongodb.quickstart.models;

import java.io.Serializable;
import java.util.List;

public interface ICharacters extends Serializable {
    boolean setupCharater();
    List<String> showActoins();
    int getHP();
    void getAttacked(int dommage);
    int attack();
}
