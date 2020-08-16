package com.ehb.dnd.model;

import com.ehb.dnd.database.UserAPI;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lambdaworks.crypto.SCryptUtil;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Id;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private ObjectId id;
    private String email;
    private String password;
    private List<Player> players;


    public User() {
        players = new ArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(ObjectId id, String email, String password, List<Player> players) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.players = players;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        if(players == null){
            players = new ArrayList<>();
        }
        players.add(player);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", Players= [");
        if(players != null){
            for (Player p: players) {
                sb.append(", ").append(p.toString());
            }
        }

        sb.append("]}");
        return sb.toString();
    }

    public static User checkUserPassword(String email, String password){

        User user = UserAPI.findUserByEmail(email);

        if (user != null){
            System.out.println("User checked: " + user.getPassword());
            if(SCryptUtil.check(password, user.getPassword())){
                System.out.println("Password checked: " + user.toString());
                return user;
            }
        }
        return null;
    }

    public static boolean insertUserWithHash(User user){
        String generatedSecuredPasswordHash = SCryptUtil.scrypt(user.password, 16, 16, 16);
        System.out.println(generatedSecuredPasswordHash);
        user.setPassword(generatedSecuredPasswordHash);
        System.out.println("User1 : " + user.toString());
        if(UserAPI.insertOneUser(user)){
            return true;
        }
        return false;
    }
    public String toJson(){
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"id\": \"").append(id.toString());
        sb.append("\", \"email\":\"").append(email);
        sb.append("\", \"password\":\"").append(password);
        sb.append("\", \"players\": [");
        if(players != null){
            for (int i = 0; i <players.size();i++) {
                if (i==0){
                    sb.append("{");
                }else {
                    sb.append(",{");
                }

                if (players.get(i).getId() != null){
                    sb.append("\"id\": \"").append(players.get(i).getId());
                    sb.append("\", \"name\":\"").append(players.get(i).getName());
                }
                else{
                    sb.append("\"id\": ").append("null");
                    sb.append(", \"name\":\"").append(players.get(i).getName());
                }
                sb.append("\", \"hp\":").append(players.get(i).getHp());
                sb.append(", \"attack\":").append(players.get(i).getAttack());
                if(players.get(i).isDM())
                    sb.append(", \"dm\":").append("true");
                else
                    sb.append(", \"dm\":").append("false");
                sb.append("}");
            }
        }

        sb.append("]}");
        return sb.toString();
    }
}
