package com.ehb.dnd.model;

import org.bson.types.ObjectId;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    private ObjectId id;
    private String name;
    private int hp;
    private int attack;
    private boolean isDM;

    public Player() {
    }

    public Player(String name, int hp, int attack) {
        super();
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.isDM = false;
    }
    public Player(String name, int hp, int attack, boolean isDM) {
        super();
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.isDM = isDM;
    }

    public ObjectId getId() {
        return id;
    }
    public void setId(ObjectId id) {
        this.id = id;
    }
    public boolean isDM() {
        return isDM;
    }
    public void setDM(boolean isDM) {
        this.isDM = isDM;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (name != other.getName())
            return false;
        return true;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Player{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", hp=").append(hp);
        sb.append(", attack=").append(attack);
        sb.append(", isDM=").append(isDM);
        sb.append('}');
        return sb.toString();
    }

}
