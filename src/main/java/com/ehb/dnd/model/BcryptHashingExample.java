package com.ehb.dnd.model;

import com.lambdaworks.crypto.SCryptUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.security.NoSuchAlgorithmException;

/**
 * Explaination : https://security.stackexchange.com/questions/211/how-to-securely-hash-passwords
 *
 *
 */

public class BcryptHashingExample {
    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        String  originalPassword = "password";
        String generatedSecuredPasswordHashBCrypt = BCrypt.hashpw(originalPassword, BCrypt.gensalt(12));
        System.out.println(generatedSecuredPasswordHashBCrypt);

        boolean matched = BCrypt.checkpw(originalPassword, generatedSecuredPasswordHashBCrypt);
        System.out.println(matched);

        String generatedSecuredPasswordHashSCryptUtil = SCryptUtil.scrypt(originalPassword, 16, 16, 16);
        System.out.println(generatedSecuredPasswordHashSCryptUtil);
        boolean matched2 = SCryptUtil.check(originalPassword, generatedSecuredPasswordHashSCryptUtil);
        System.out.println(matched2);
    }
    public static boolean checkPassword(String password,String generatedSecuredPasswordHash){
        String generatedSecuredPasswordHashBCrypt = BCrypt.hashpw(password, BCrypt.gensalt(12));
        boolean matched = SCryptUtil.check(generatedSecuredPasswordHashBCrypt, generatedSecuredPasswordHash);
        return matched;
    }
}
