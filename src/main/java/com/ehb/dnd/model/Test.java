package com.ehb.dnd.model;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            /*{
                "id": "5f3419131237d766cface56a",
                    "email": "test@test.com",
                    "password": "$s0$41010$SXJj4rGbkBIgnV6SzzFm6Q==$kcy7FODNvm63PmD9FmMp4s562rYwdlKwrvC2C2xkCII=",
                    "players": [
                {
                    "id": null,
                        "name": "Sir Worked",
                        "hp": 450,
                        "attack": 15,
                        "dm": false
                },
                {
                    "id": null,
                        "name": "Sir DM",
                        "hp": 1,
                        "attack": 15,
                        "dm": true
                }
    ]
            }*/
            URL url = new URL(
                    "http://localhost:8080/api/user/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            User user = new User();
            ObjectId objectId = new ObjectId("5f3419131237d766cface56a");
            user.setId(objectId);
            user.setEmail("test@test.com");
            user.setPassword("$s0$41010$SXJj4rGbkBIgnV6SzzFm6Q==$kcy7FODNvm63PmD9FmMp4s562rYwdlKwrvC2C2xkCII=");

            List<Player> players = new ArrayList<>();
            Player p1 = new Player();
            p1.setName("Sir Work");
            p1.setHp(450);
            p1.setAttack(15);
            p1.setDM(false);
            Player p2 = new Player("Sir DM",1,15,true);
            players.add(p1);
            players.add(p2);

            user.setPlayers(players);

            ObjectMapper objectMapper = new ObjectMapper();
            String stringuser = user.toJson();

            System.out.println(stringuser);
            OutputStream os = conn.getOutputStream();
            os.write(stringuser.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();

        }



    }

    /*User user = new User();
        user.setEmail("test@test.com");

        user.setPassword("mypassword");
        *//*System.out.println("User1 : " + user.toString());
        //UserDb.insertOneUser(user);
        Document filterByGradeId = new Document("email", user.getEmail());
        User u = UserDb.findUserByName(filterByGradeId);
        System.out.println("User2 : " + u.toString());*//*
        Document filterByGradeId = new Document("email", user.getEmail());
        filterByGradeId.append("password", user.getPassword());
        user = UserDb.findUserByName(filterByGradeId);
        System.out.println("User3 : " + user.toString());
        //user = UserDb.findUserByName(filterByGradeId);
        //System.out.println("User4 : " + user.toString());*/

//        File file = new File("image\\default\\map\\o.png");
//        System.out.println("File name : " + file.getName());

    /* Map map = new Map(20,20);
        while (!map.findPath(10, 10, 10, 10, 20, 3, 5)) ;
        map.makeRoomConnected();
        String s = Serializator.convertToByteString(map);
        System.out.println(s);
        Map m = (Map) Serializator.convertFromByteString(s);
        System.out.println(m.toString());
        for (Room r:
             m.getMap()) {

        }*/
}
