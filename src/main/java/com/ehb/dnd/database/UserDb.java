package com.ehb.dnd.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.ehb.dnd.model.User;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class UserDb {
    private final static String DATABASE = "dnd";
    private final static String COLLECTION = "user";


    private static MongoClient setup(){
        MongoDbClient mongoDbClient = new MongoDbClient().getInstance();
        return mongoDbClient.getClient();
    }

    public static boolean insertOneUser(User newUser){
        // create a new grade.
        try {
            MongoClient mongoClient = setup();
            MongoDatabase db = mongoClient.getDatabase(DATABASE);
            MongoCollection<User> users = db.getCollection(COLLECTION, User.class);
            Document filter = new Document("email", newUser.getEmail());
            User user = users.find(filter).first();
            if (user == null){
                users.insertOne(newUser);
                System.out.println("User inserted.");
                return true;
            } else {
                System.out.println("User wasn't inserted.");
                return false;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static User findUserByName(Document filter){
        // find this grade.
        try{
            MongoClient mongoClient = setup();
            MongoDatabase db = mongoClient.getDatabase(DATABASE);
            MongoCollection<User> users = db.getCollection(COLLECTION, User.class);
            User user = users.find(filter).first();
            System.out.println("User found:\t" + user);
            return user;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static User updateUser(User User, Document filter){
        // update this grade: adding an exam grade
        try {
            MongoClient mongoClient = setup();
            MongoDatabase db = mongoClient.getDatabase(DATABASE);
            MongoCollection<User> users = db.getCollection(COLLECTION, User.class);
            FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
            User updatedUser = users.findOneAndReplace(filter, User, returnDocAfterReplace);
            if(updatedUser != null){
                System.out.println("User replaced:\t" + updatedUser.toString());
                return updatedUser;
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void deleteUser(Document filter){
        // delete this grade
        try {
            MongoClient mongoClient = setup();
            MongoDatabase db = mongoClient.getDatabase(DATABASE);
            MongoCollection<User> users = db.getCollection(COLLECTION, User.class);
            System.out.println("User deleted:\t" + users.deleteOne(filter));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
