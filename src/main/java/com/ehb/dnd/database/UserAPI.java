package com.ehb.dnd.database;

import com.ehb.dnd.model.Player;
import com.ehb.dnd.model.User;
import com.lambdaworks.crypto.SCryptUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserAPI {
    private final static String CONNECTION = "http://localhost:8080/api/user/";

    public static boolean insertOneUser(User newUser){
        System.out.println("insertOneUser" + newUser.getPassword());
        // create a new user.
        try {
            //set up connection
            URL url = new URL(CONNECTION);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            //put user in een json string
            ObjectMapper objectMapper = new ObjectMapper();
            String stringuser = objectMapper.writeValueAsString(newUser);

            //send user
            OutputStream os = conn.getOutputStream();
            os.write(stringuser.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            System.out.println("insertOneUser Output from Server .... \n");
            if (br.readLine() != null) {
                return true;
            }

            conn.disconnect();

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static User findUserByEmail(String email){
        // find this user by email.
        try {
            //set up connection
            URL url = new URL(
                    CONNECTION +"?emailID=" + email);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                return null;
            }
            //Send request
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            //receive response
            String output;
            StringBuffer stringBuilder = new StringBuffer();
            System.out.println("findUserByEmail Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                stringBuilder.append(output);
                System.out.println(output);
            }
            if(stringBuilder!=null) {
                User user = new ObjectMapper().readValue(stringBuilder.toString(), User.class);
                System.out.println(user.toString());
                System.out.println("findUserByEmail" + user.getPassword());
                return user;
            }
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User updateUser(User user){
        // update this user
        try {
            URL url = new URL(CONNECTION);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            ObjectMapper objectMapper = new ObjectMapper();
            String stringuser = user.toJson();

            System.out.println(stringuser);
            OutputStream os = conn.getOutputStream();
            os.write(stringuser.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            StringBuffer stringBuilder = new StringBuffer();
            System.out.println("updateUser Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                stringBuilder.append(output);
                System.out.println(output);
            }
            if(stringBuilder!=null) {
                User u = new ObjectMapper().readValue(stringBuilder.toString(), User.class);
                System.out.println(u.toString());
                return u;
            }
            conn.disconnect();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
