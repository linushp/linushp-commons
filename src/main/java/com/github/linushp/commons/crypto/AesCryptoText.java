package com.github.linushp.commons.crypto;


import static java.nio.charset.StandardCharsets.UTF_8;

public class AesCryptoText {

    private AesCrypto textCrypto = null; // key for text data

    public void init(String aesKey) {
        if (textCrypto == null) {
            textCrypto = new AesCrypto(aesKey);
        }
    }

    public String encryptHex(String str) {
        byte[] bytes = textCrypto.encryptBytes(str.getBytes(UTF_8));
        return BytesToString.byte2Hex(bytes);
    }

    public String encryptBase64(String str) {
        byte[] bytes = textCrypto.encryptBytes(str.getBytes(UTF_8));
        return BytesToString.byte2Base64(bytes);
    }

    public String encryptBase58(String str) {
        byte[] bytes = textCrypto.encryptBytes(str.getBytes(UTF_8));
        return BytesToString.byte2Base58(bytes);
    }


    public String decryptHex(String hexString) {
        byte[] bytes = BytesToString.parseHex(hexString);
        byte[] bytes2 = textCrypto.decryptBytes(bytes);
        return new String(bytes2, UTF_8);
    }

    public String decryptBase64(String base64String) {
        byte[] bytes = BytesToString.parseBase64(base64String);
        byte[] bytes2 = textCrypto.decryptBytes(bytes);
        return new String(bytes2, UTF_8);
    }

    public String decryptBase58(String base58String) {
        byte[] bytes = BytesToString.parseBase58(base58String);
        byte[] bytes2 = textCrypto.decryptBytes(bytes);
        return new String(bytes2, UTF_8);
    }


}
