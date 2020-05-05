package logicalOper;

// GF(p) 的运算：加减乘除，p 是素数
public class GFpCal
{
    public static void verify(int a, int b, int p)
    {
        GFnCal.verify(a, b, p);

        // p 得是素数
        assert(Prime.isPrime(p));
    }

    public static int add(int a, int b, int p)
    {
        verify(a, b, p);

        return GFnCal.add(a, b, p);
    }

    public static int sub(int a, int b, int p)
    {
        verify(a, b, p);

        return GFnCal.sub(a, b, p);
    }

    public static int mul(int a, int b, int p)
    {
        verify(a, b, p);

        return GFnCal.mul(a, b, p);
    }

    // 计算 b/a
    public static int div(int a, int b, int p)
    {
        verify(a, b, p);

        int c = mul_inv(a, p);

        return mul(b, c, p);
    }

    public static int add_inv(int a, int p)
    {
        return GFnCal.add_inv(a, p);
    }

    public static int mul_inv(int a, int p)
    {
        assert(0 <= a);
        assert(a < p);
        assert(Prime.isPrime(p));

        int XY[] = Euclid.ext_gcd(a, p);

        assert(null != XY);

        return XY[1];
    }

}
