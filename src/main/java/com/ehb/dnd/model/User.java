package com.ehb.dnd.model;

import com.ehb.dnd.database.UserDb;
import com.lambdaworks.crypto.SCryptUtil;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", x=").append(email);
        sb.append(", y=").append(password);
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
        Document filter = new Document("email", email);
        User user = UserDb.findUserByName(filter);

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
        if(UserDb.insertOneUser(user)){
            return true;
        }
        return false;
    }
}
