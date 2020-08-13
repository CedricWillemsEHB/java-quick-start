package com.mongodb.quickstart.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.quickstart.models.Player;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class PlayerDb {
    private static MongoClient setup(){
        MongoDbClient mongoDbClient = new MongoDbClient().getInstance();
        return mongoDbClient.getClient();
    }

    public static void insertOnePlayer(Player newPlayer){
        // create a new grade.
        try (MongoClient mongoClient = setup()) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);
            players.insertOne(newPlayer);
            System.out.println("Player inserted.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    public static Player findPlayerByName(String name){
        // find this grade.
        try (MongoClient mongoClient = setup()) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);
            Player player = players.find(eq("name", "Player1")).first();
            System.out.println("Player found:\t" + player);
            return player;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean updatePlayer(Player player, Document filter){
        // update this grade: adding an exam grade
        try (MongoClient mongoClient = setup()) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);
            System.out.println(filter.toJson());
            player.setDM(false);
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            Player updatedPlayer = players.findOneAndReplace(filter, player, returnDocAfterReplace);
            if(updatedPlayer != null){
                System.out.println("Player replaced:\t" + updatedPlayer.toString());
                return true;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void deletePlayer(Document filter){
        // delete this grade
        try (MongoClient mongoClient = setup()) {
            MongoDatabase db = mongoClient.getDatabase("dnd");
            MongoCollection<Player> players = db.getCollection("player", Player.class);
            System.out.println("Player deleted:\t" + players.deleteOne(filter));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
