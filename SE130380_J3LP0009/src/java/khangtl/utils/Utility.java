/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @author Peter
 */
public class Utility implements Serializable {

    private static final double EXCHANGE_RATE_VND_USD = 0.0000430200;

    public static String hashStringSHA256(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        text = Base64.getEncoder().encodeToString(hash);
        return text;
    }

    public static double convertVNDToUSD(int money) {
        return money * EXCHANGE_RATE_VND_USD;
    }
}
