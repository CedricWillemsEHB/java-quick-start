package com.mongodb.quickstart.models;

import com.mongodb.quickstart.database.UserDb;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        Room r1 = new Room(1,2);
        Room r2 = new Room(1,2);
        System.out.println("" + r1.equals(r2));

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
