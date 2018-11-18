package com.example.fredd.wedate.encryption;

public class HillKey {


    public final int[][] key;
    private int[][] inverseKey;

    HillKey(int[][] key, int mod) throws Exception {
        this.key = key;
        inverseKey = inverse(key, mod);
    }


    public HillKey(int[][] key) throws Exception {
        this.key = key;
        inverseKey = inverse(key,(int)Math.pow(2 , 16));
    }

    public int[][] getInverseKey() {
        return inverseKey;
    }

    public int getDimensions() {
        return key.length;
    }


    private void getCofactor(int mat[][],
                             int temp[][], int p, int q, int n) {
        int i = 0, j = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];

                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    private int[][] adjoint(int A[][], int N) {
        int[][] adj = new int[N][N];

        if (N == 1) {
            adj[0][0] = 1;
            return adj;
        }

        int sign = 1;
        int[][] temp = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                getCofactor(A, temp, i, j, N);
                sign = ((i + j) % 2 == 0) ? 1 : -1;
                adj[j][i] = (sign) * (determinantOfMatrix(temp, N - 1));
            }
        }

        return adj;
    }


    private int determinantOfMatrix(int mat[][], int n) {
        int D = 0;
        if (n == 1)
            return mat[0][0];


        int temp[][] = new int[n][n];


        int sign = 1;


        for (int f = 0; f < n; f++) {

            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            sign = -sign;
        }

        return D;
    }


    private int[][] inverse(int mat[][], int mod) throws Exception {
        int[][] result = new int[mat.length][mat.length];
        int det = determinantOfMatrix(mat, mat.length);
        det = modularInverse(det, mod);
        if (det <= 0)
            throw new Exception("Key is singular");


        int[][] adj = adjoint(mat, mat.length);
        for (int i = 0; i < adj.length; i++)
            for (int j = 0; j < adj[0].length; j++)
                adj[i][j] = findMod(adj[i][j], mod);


        for (int i = 0; i < adj.length; i++)
            for (int j = 0; j < adj[0].length; j++)
                result[i][j] = findMod(adj[i][j] * det, mod);

        return result;
    }

    private static int findMod(int num, int mod) {
        if (num >= 0)
            return num % mod;

        num = Math.abs(num) % mod;
        return mod - num;
    }

    private static int modularInverse(int a, int mod) {
        a = findMod(a, mod);
        for (int i = 0; i < mod; i++) {
            int temp = (a * i) % mod;
            if (temp == 1)
                return i;
        }

        return -1;
    }


    private void display(int mat[][], int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++)
                System.out.print(mat[i][j] + " ");

            System.out.print("\n");
        }
    }
}
