package com.ehb.dnd;

import com.ehb.dnd.database.MongoDbClient;
import com.ehb.dnd.model.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static java.util.Collections.singletonList;

public class MappingPOJO {

    public static void main(String[] args) {
        MongoDbClient dbClient = new MongoDbClient().getInstance();
        try (MongoClient mongoClient = dbClient.getClient()) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);

            /*// create a new grade.
            Player newPlayer = new Player("Player1",99,99,true);
            players.insertOne(newPlayer);
            System.out.println("Player inserted.");
*/
            // find this grade.
            Player player = players.find(eq("name", "Player1")).first();
            System.out.println("Player found:\t" + player);
            // update this grade: adding an exam grade
            Document filterByGradeId = new Document("_id", player.getId());
            System.out.println(filterByGradeId.toJson());
            player.setDM(false);
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Player updatedPlayer = players.findOneAndReplace(filterByGradeId, player, returnDocAfterReplace);
            System.out.println("Player replaced:\t" + updatedPlayer);

            // delete this grade
            System.out.println("Player deleted:\t" + players.deleteOne(filterByGradeId));
        }

    }

    public void mappingRoom(MongoClientSettings clientSettings){
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Room> rooms = db.getCollection("room", Room.class);

            // create a new grade.
            Room newRoom = new Room(20,20);
            Combat c = new Combat();
            c.setupEvent("Wolf",null);
            newRoom.setEvent(null);
            rooms.insertOne(newRoom);
            System.out.println("Room inserted.");

            // find this grade.
            Room map = rooms.find(eq("x", 20)).first();
            System.out.println("Room found:\t" + map.toString());

           /* // update this grade: adding an exam grade
            Document filterByGradeId = new Document("_id", map.getId());
            while(!map.findPath(10, 10, 10, 10, 20,3,5));
            map.makeRoomConnected();

            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Map updatedPlayer = maps.findOneAndReplace(filterByGradeId, map, returnDocAfterReplace);
            System.out.println("Map replaced:\t" + updatedPlayer);

            // delete this grade
            System.out.println("Map deleted:\t" + maps.deleteOne(filterByGradeId));*/
        }
    }

    public void mappingMap(MongoClientSettings clientSettings){
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Map> maps = db.getCollection("map", Map.class);

            // create a new grade.
            Map newMap = new Map(20,20);
            while(!newMap.findPath(10, 10, 10, 10, 20,3,5));
            newMap.makeRoomConnected();
            maps.insertOne(newMap);
            System.out.println("Map inserted.");

            // find this grade.
            Map map = maps.find(eq("x", 20)).first();
            System.out.println("Map found:\t" + map.toString());

           /* // update this grade: adding an exam grade
            Document filterByGradeId = new Document("_id", map.getId());
            while(!map.findPath(10, 10, 10, 10, 20,3,5));
            map.makeRoomConnected();

            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Map updatedPlayer = maps.findOneAndReplace(filterByGradeId, map, returnDocAfterReplace);
            System.out.println("Map replaced:\t" + updatedPlayer);

            // delete this grade
            System.out.println("Map deleted:\t" + maps.deleteOne(filterByGradeId));*/
        }
    }

    public void mappingPlayer(MongoClientSettings clientSettings){
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);

            // create a new grade.
            Player newPlayer = new Player("Player1",59,12,true);
            players.insertOne(newPlayer);
            System.out.println("Player inserted.");

            // find this grade.
            Player player = players.find(eq("name", "Player1")).first();
            System.out.println("Player found:\t" + player);

            // update this grade: adding an exam grade
            Document filterByGradeId = new Document("_id", player.getId());
            player.setDM(false);
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Player updatedPlayer = players.findOneAndReplace(filterByGradeId, player, returnDocAfterReplace);
            System.out.println("Player replaced:\t" + updatedPlayer);

            // delete this grade
            System.out.println("Player deleted:\t" + players.deleteOne(filterByGradeId));
        }
    }

    public void mappingGrade(MongoClientSettings clientSettings){
        try (MongoClient mongoClient = MongoClients.create(clientSettings)) {
            MongoDatabase db = mongoClient.getDatabase("sample_training");
            MongoCollection<Grade> grades = db.getCollection("grades", Grade.class);

            // create a new grade.
            Grade newGrade = new Grade().setStudentId(10003d)
                    .setClassId(10d)
                    .setScores(singletonList(new Score().setType("homework").setScore(50d)));
            grades.insertOne(newGrade);
            System.out.println("Grade inserted.");

            // find this grade.
            Grade grade = grades.find(eq("student_id", 10003d)).first();
            System.out.println("Grade found:\t" + grade);

            // update this grade: adding an exam grade
            List<Score> newScores = new ArrayList<>(grade.getScores());
            newScores.add(new Score().setType("exam").setScore(42d));
            grade.setScores(newScores);
            Document filterByGradeId = new Document("_id", grade.getId());
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Grade updatedGrade = grades.findOneAndReplace(filterByGradeId, grade, returnDocAfterReplace);
            System.out.println("Grade replaced:\t" + updatedGrade);

            // delete this grade
            System.out.println("Grade deleted:\t" + grades.deleteOne(filterByGradeId));
        }
    }
}
