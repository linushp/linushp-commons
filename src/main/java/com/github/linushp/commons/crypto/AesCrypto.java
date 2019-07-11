package com.github.linushp.commons.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AesCrypto {

    private SecretKeySpec keySpec;
    private SecureRandom srandom;

    public AesCrypto(String hexKey) {
        if (!testKeys(hexKey)) {
            throw new RuntimeException("AES Key invalid");
        }
        keySpec = new SecretKeySpec(fromHex(hexKey), "AES");
        srandom = new SecureRandom();
    }


    /**
     * AES-128/192/256 allowed
     *
     * @param hexKey
     * @return
     */
    private boolean testKeys(String hexKey) {
        return hexKey != null && (hexKey.length() == 32 || hexKey.length() == 48 || hexKey.length() == 64);
    }

    public byte[] fromHex(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        int length = hexStr.length();
        if (length % 2 == 1) {
            return null;
        }
        int byteLength = length / 2;
        byte[] raw = new byte[byteLength];
        for (int i = 0; i < byteLength; i++) {
            raw[i] = (byte) Integer.parseInt(hexStr.substring(i + i, i + i + 2), 16);
        }
        return raw;
    }

    public byte[] decryptBytes(byte[] sBytes) {
        if (sBytes == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int ivSize = cipher.getBlockSize();
            IvParameterSpec ivSpec = new IvParameterSpec(sBytes, 0, ivSize);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] payload = cipher.doFinal(sBytes, ivSize, sBytes.length - ivSize);
            return payload;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] encryptBytes(byte[] sBytes) {
        if (sBytes == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];
            srandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] payload = cipher.doFinal(sBytes);
            byte[] encrypted = new byte[iv.length + payload.length];
            System.arraycopy(iv, 0, encrypted, 0, iv.length);
            System.arraycopy(payload, 0, encrypted, iv.length, payload.length);
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}