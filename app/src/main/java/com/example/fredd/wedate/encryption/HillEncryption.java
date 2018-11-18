package com.example.fredd.wedate.encryption;

public class HillEncryption {

    private int mod = (int) Math.pow(2, 16);

    public HillEncryption(int mod) {
        this.mod = mod;
    }

    public HillEncryption() {

    }


    public String encryptString(String str, HillKey key) {
        StringBuilder encrypted = new StringBuilder();
        int dimensions = key.getDimensions();
        int remaining = dimensions - str.length() % dimensions;
        str = str + makeString((char) 50000, remaining);

        for (int i = 0; i < str.length(); i += dimensions) {
            String temp = str.substring(i, i + dimensions);
            int[][] mat = new int[1][dimensions];
            for (int j = 0; j < dimensions; j++)
                mat[0][j] = (int) temp.charAt(j);
            int[][] result = mul(mat, key.key);
            for (int j = 0; j < result[0].length; j++)
                encrypted.append((char) result[0][j]);
        }

        return encrypted.toString();

    }

    public String decryptString(String str, HillKey key) {
        StringBuilder decrypted = new StringBuilder();
        int dimensions = key.getDimensions();
        for (int i = 0; i < str.length(); i += dimensions) {
            String temp = str.substring(i, i + dimensions);
            int[][] mat = new int[1][dimensions];
            for (int j = 0; j < dimensions; j++)
                mat[0][j] = (int) temp.charAt(j);
            int[][] result = mul(mat, key.getInverseKey());
            for (int j = 0; j < result[0].length; j++)
                decrypted.append((char) result[0][j]);
        }

        for (int i = decrypted.length() - 1; i >= 0; i--)
            if ((int) decrypted.charAt(i) == 50000)
                decrypted.deleteCharAt(i);
        return decrypted.toString();

    }

    private String makeString(char c, int num) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < num; i++)
            builder.append(c);
        return builder.toString();
    }

    private static int[][] mul(int[][] a, int[][] b) {

        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        int[][] c = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++)
            for (int j = 0; j < colsB; j++)
                for (int k = 0; k < colsA; k++)
                    c[i][j] += a[i][k] * b[k][j];


        return c;
    }

    public static void main(String[] args) throws Exception {
        String test = "whosthebitch";
        int[][] arr = {{1, 1, 1},
                {4, 4, 1},
                {1, 8, 1}};
        HillKey key = new HillKey(arr);
        HillEncryption hillEncryption = new HillEncryption();
        String en = hillEncryption.encryptString(test, key);
        System.out.println(hillEncryption.decryptString(en, key));
    }


}
