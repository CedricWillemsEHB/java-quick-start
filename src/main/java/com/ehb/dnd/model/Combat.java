package com.ehb.dnd.model;

import java.util.List;

public class Combat implements IEvent {
    private String name;
    List<ICharacters> monsters;


    @Override
    public boolean setupEvent(String name, List<ICharacters> characters) {
        if(!name.isEmpty()) {
            this.name = name;
            if(characters != null){
                if(!characters.isEmpty()) {
                    //this.monsters = characters;
                }
            }

            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ICharacters> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<ICharacters> monsters) {
        this.monsters = monsters;
    }

    @Override
    public String showEvent() {
        // TODO Auto-generated method stub

        return this.getClass().getSimpleName();
    }
    @Override
    public List<ICharacters> getCharacters() {
        // TODO Auto-generated method stub
        return monsters;
    }

    @Override
    public void changeCharacters(List<ICharacters> characters) {
        // TODO Auto-generated method stub
        setMonsters(characters);
    }

}
