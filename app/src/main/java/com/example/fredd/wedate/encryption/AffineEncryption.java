package com.example.fredd.wedate.encryption;

public class AffineEncryption {

    private int POW = (int) Math.pow(2, 16);

    public AffineEncryption() {

    }

    public AffineEncryption(int pow) {
        POW = pow;
    }

    public String encryptStringAffine(String string, AffineKey key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append((char) encryptAffine((int) string.charAt(i), key, POW));
        }

        return stringBuilder.toString();
    }

    public String decryptStringAffine(String string, AffineKey key) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append((char) decryptAffine((int) string.charAt(i), key, POW));
        }

        return stringBuilder.toString();
    }

    public int encryptAffine(int number, AffineKey key, int mod) {
        return (number * key.a + key.b) % mod;
    }

    public int decryptAffine(int number, AffineKey key, int mod) {
        return (modularInverse(key.a, mod) * (number - key.b)) % mod;
    }

    private int modularInverse(int a, int mod) {
        for (int i = 0; i < mod; i++) {
            int temp = (a * i) % mod;
            if (temp == 1)
                return i;
        }

        return -1;
    }
}
