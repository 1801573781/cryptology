package logicalOper;

// 多项式系数，或者是0，或者是1，这样才能有除法
public class GF2Poly
{
    public static void println(int a[])
    {
        Polynomial.println(a);
    }

    public static String toString(int a[])
    {
        return Polynomial.toString(a);
    }


    // add == xor
    public static int[] add(int a[], int b[])
    {
        return null;
    }

    // sub == xor == add
    public static int[] sub(int a[], int b[])
    {
        return add(a, b);
    }

}
