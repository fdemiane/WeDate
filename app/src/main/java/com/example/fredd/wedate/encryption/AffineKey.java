package com.example.fredd.wedate.encryption;

public class AffineKey {

    public final int a;
    public final int b;

    public AffineKey(int a, int b, int mod) throws Exception {
        if(gcd(a,mod) !=1)
            throw new Exception("Affine Key not tolerated");
        this.a = a;
        this.b = b;
    }

    private int gcd(int a, int b) {
        if (a == 0)
            return b;

        return gcd(b % a, a);
    }

}
