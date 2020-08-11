package com.mongodb.quickstart.models;

import java.io.Serializable;
import java.util.List;

public interface IEvent extends Serializable {
    boolean setupEvent(String name, List<ICharacters> characters);
    String showEvent();
    List<ICharacters> getCharacters();
    void changeCharacters(List<ICharacters> characters);
}
