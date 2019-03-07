package com.jadepool.sdk;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Utils {

    public static byte[] byteToByteArr(byte data) {
        return new byte[]{data};
    }

    public static byte[] shortToByteArr(short data) {
        return new byte[] {
                (byte)((data >> 0) & 0xff),
                (byte)((data >> 8) & 0xff)
        };
    }

    public static byte[] intToByteArr(int data) {
        return new byte[] {
                (byte)((data >> 0) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 24) & 0xff)
        };
    }

    public static byte[] longToByteArr(long data) {
        return new byte[] {
                (byte)((data >> 0) & 0xff),
                (byte)((data >> 8) & 0xff),
                (byte)((data >> 16) & 0xff),
                (byte)((data >> 24) & 0xff),
                (byte)((data >> 32) & 0xff),
                (byte)((data >> 40) & 0xff),
                (byte)((data >> 48) & 0xff),
                (byte)((data >> 56) & 0xff),
        };
    }

    public static byte[] stringToByteArr(String data) {
        return (data == null) ? null : data.getBytes();
    }

    public static byte[] sha256(byte[] input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(input, 0, input.length);
        return digest.digest();
    }

    public static String byteArrayToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for(byte a: data)
            sb.append(String.format("%02x", a));
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    static HttpResponse get(String url) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type", "application/json");

        return httpClient.execute(request);
    }

    static HttpResponse post(String url, String data) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        StringEntity params =new StringEntity(data);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);

        return httpClient.execute(request);
    }

    static HttpResponse patch(String url, String data) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPatch request = new HttpPatch(url);
        StringEntity params =new StringEntity(data);
        request.addHeader("content-type", "application/json");
        request.setEntity(params);

        return httpClient.execute(request);
    }

    public static boolean isHex (String data) {
        return data.matches("\\p{XDigit}+");
    }

    public static boolean isBase64 (String data) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            decoder.decode(data);
            return true;
        } catch(IllegalArgumentException ex) {
            return false;
        }
    }

    static String sign (byte[] byteArr, String key) {
        BigInteger privKey = new BigInteger(key, 16);
        BigInteger pubKey = Sign.publicKeyFromPrivate(privKey);
        ECKeyPair keyPair = new ECKeyPair(privKey, pubKey);
        Sign.SignatureData signature = Sign.signMessage(byteArr, keyPair, false);
        String r = Utils.byteArrayToHex(signature.getR());
        String s = Utils.byteArrayToHex(signature.getS());
        return r + s;
    }
}
