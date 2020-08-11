package com.mongodb.quickstart.models;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
        File file = new File("image\\default\\map\\o.png");
        System.out.println("File name : " + file.getName());
    }
}
