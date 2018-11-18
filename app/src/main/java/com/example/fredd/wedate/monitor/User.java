package com.example.fredd.wedate.monitor;

import com.example.fredd.wedate.encryption.AffineEncryption;
import com.example.fredd.wedate.encryption.AffineKey;
import com.example.fredd.wedate.encryption.DoubleEncryption;
import com.example.fredd.wedate.encryption.HillKey;
import com.example.fredd.wedate.encryption.MD5;

public class User {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String sex;
    private String tag;
    private int birthYear;

    private MD5 md5 = new MD5();
    private boolean encrypted = false;

    public User(String username, String password, String firstName, String lastName, String sex, String tag, int birthYear) {

        this.username = username;
        this.password = md5.encrypt(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.setTag(tag);
        this.setBirthYear(birthYear);
    }

    public User() {

    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordAndEncrypt(String password) {
        this.password = md5.encrypt(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void encrypt() throws Exception {

        if (encrypted)
            return;
        AffineKey affineKey = new AffineKey(7, 3, (int) Math.pow(2, 16));
        int[][] arr = {{1, 1, 1},
                {4, 4, 1},
                {1, 8, 1}};
        HillKey hillKey = new HillKey(arr);
        DoubleEncryption encryption = new DoubleEncryption();
        AffineEncryption affineEncryption = new AffineEncryption();

        username = encryption.encrypt(username, hillKey, affineKey);
        firstName = encryption.encrypt(firstName, hillKey, affineKey);
        lastName = encryption.encrypt(lastName, hillKey, affineKey);
        sex = encryption.encrypt(sex, hillKey, affineKey);
        tag = encryption.encrypt(tag, hillKey, affineKey);
        birthYear = affineEncryption.encryptAffine(birthYear, affineKey, (int) Math.pow(2, 16));
        encrypted = true;


    }

    public String encryptString(String str) throws Exception {
        AffineKey affineKey = new AffineKey(7, 3, (int) Math.pow(2, 16));
        int[][] arr = {{1, 1, 1},
                {4, 4, 1},
                {1, 8, 1}};
        HillKey hillKey = new HillKey(arr);
        DoubleEncryption encryption = new DoubleEncryption();
        return encryption.encrypt(str , hillKey,affineKey);
    }

    public void decrypt() throws Exception {
        if (!encrypted)
            return;
        AffineKey affineKey = new AffineKey(7, 3, (int) Math.pow(2, 16));
        int[][] arr = {{1, 1, 1},
                {4, 4, 1},
                {1, 8, 1}};
        HillKey hillKey = new HillKey(arr);
        DoubleEncryption decryption = new DoubleEncryption();
        AffineEncryption affineDecryption = new AffineEncryption();

        username = decryption.decrypt(username, hillKey, affineKey);
        firstName = decryption.decrypt(firstName, hillKey, affineKey);
        lastName = decryption.decrypt(lastName, hillKey, affineKey);
        sex = decryption.decrypt(sex, hillKey, affineKey);
        tag = decryption.decrypt(tag, hillKey, affineKey);
        birthYear = affineDecryption.decryptAffine(birthYear, affineKey, (int) Math.pow(2, 16));
        encrypted = false;
    }
}
