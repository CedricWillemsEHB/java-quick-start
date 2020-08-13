package com.ehb.dnd.model;

import java.io.Serializable;
import java.util.List;

public class Monster implements ICharacters, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private int hp;
    private int attack;
    public Monster(String name, int hp, int attack) {
        super();
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }
    @Override
    public String toString() {
        return "Monster ["+ name + ", hp=" + hp + ", attack="+attack+"]";
    }

    public void doSomething() {

    }
    @Override
    public boolean setupCharater() {
        return false;
    }
    @Override
    public List<String> showActoins() {
        return null;
    }
    @Override
    public int getHP() {
        return hp;
    }
    @Override
    public void getAttacked(int dommage) {
        this.hp -= dommage;
        if(this.hp < 0) {
            this.hp = 0;
        }
    }
    @Override
    public int attack() {
        return attack;
    }
}
