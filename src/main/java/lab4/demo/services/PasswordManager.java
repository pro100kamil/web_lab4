package lab4.demo.services;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordManager {
    private static final String algorithmName = "SHA-384";
    private static final int hashLength = 96;

    private static final String pepper = "*63&^mVLC(#";

    public static String getHash(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = md.digest((password + pepper).getBytes(StandardCharsets.UTF_8));

        BigInteger bigInt = new BigInteger(1, hash);

        StringBuilder strHash = new StringBuilder(bigInt.toString(16));

        while (strHash.length() < hashLength) {
            strHash.insert(0, "0");
        }

        return strHash.toString();
    }
}