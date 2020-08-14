package com.ehb.dnd.model;

import com.ehb.dnd.database.UserDb;
import com.lambdaworks.crypto.SCryptUtil;
import org.bson.Document;

public class ScryptPasswordHashingDemo {
    public static void main(String[] args){
        /*String originalPassword = "password";
        String generatedSecuredPasswordHash = SCryptUtil.scrypt(originalPassword, 16, 16, 16);
        System.out.println(generatedSecuredPasswordHash);

        boolean matched = SCryptUtil.check("password", generatedSecuredPasswordHash);
        System.out.println(matched);

        matched = SCryptUtil.check("passwordno", generatedSecuredPasswordHash);
        System.out.println(matched);*/

        //insertUserWithHash();
        boolean matched = checkUserPassword("test@test.com", "password");
        System.out.println(matched);
    }
    private static boolean checkUserPassword(String email, String password){
        Document filter = new Document("email", email);
        User user = UserDb.findUserByName(filter);

        if (user != null){
            System.out.println("Password1 : " + password +"\n Password2 : " + user.getPassword());
            return SCryptUtil.check(password, user.getPassword());
        }
        return false;
    }
    private static void insertUserWithHash(){
        String  originalPassword = "password";
        String generatedSecuredPasswordHash = SCryptUtil.scrypt(originalPassword, 16, 16, 16);
        System.out.println(generatedSecuredPasswordHash);

        User user = new User();
        user.setEmail("test@test.com");

        user.setPassword(generatedSecuredPasswordHash);
        System.out.println("User1 : " + user.toString());
        UserDb.insertOneUser(user);
    }
}
