package logicalOper;

// GF(p) 的运算：加减乘，没有除，n 是整数
public class GFnCal
{
    public static void verify(int a, int b, int n)
    {
        assert(0 <= a);
        assert(0 <= b);
        assert(a < n);
        assert(b < n);
    }

    public static int add(int a, int b, int n)
    {
        verify(a, b, n);

        return Math.floorMod((a + b), n);
    }

    public static int sub(int a, int b, int n)
    {
        verify(a, b, n);

        int c = add_inv(b, n);

        return add(a, c, n);
    }

    public static int mul(int a, int b, int n)
    {
        verify(a, b, n);

        return Math.floorMod((a * b), n);
    }


    public static int add_inv(int a, int n)
    {
        assert(0 <= a);
        assert(a < n);

        return (n - a);
    }

    // 不支持
    public static int div(int a, int b, int n)
    {
        // 直接让其崩溃
        assert(false);

        return 0;
    }
}
