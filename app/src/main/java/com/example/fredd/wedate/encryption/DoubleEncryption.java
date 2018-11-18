package com.example.fredd.wedate.encryption;

public class DoubleEncryption {

    private AffineEncryption affineEncryption = new AffineEncryption();
    private HillEncryption hillEncryption = new HillEncryption();

    public String encrypt(String msg, HillKey hillKey , AffineKey affineKey)
    {
        String affine = affineEncryption.encryptStringAffine(msg , affineKey);
        String hill = hillEncryption.encryptString(affine , hillKey);
        return hill;
    }

    public String decrypt(String msg, HillKey hillKey, AffineKey affineKey)
    {
        String hill = hillEncryption.decryptString(msg ,hillKey);
        String res = affineEncryption.decryptStringAffine(hill , affineKey);
        return res;
    }
}
