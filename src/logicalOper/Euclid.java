package logicalOper;

public class Euclid
{
    public static int gcd(int a, int b)
    {
        // 1. 根据定义，gcd(0, 0) = 0
        if ((0 == a) && (0 == b))
        {
            return 0;
        }

        int x = Math.abs(a);
        int y = Math.abs(b);

        // 2. 令 a >= b
        if (x < y)
        {
            a = y;
            b = x;
        }
        else
        {
            a = x;
            b = y;
        }

        // 3. 如果 b 等于 0，则 gcd(a, b) = a
        if (0 == b)
        {
            return a;
        }

        // 4. 迭代计算
        return gcd_inner(a, b);
    }

    // 这里保证： a >= b > 0
    private static int gcd_inner(int a, int b)
    {
        int r = a % b;

        if (0 == r)
        {
            return b;
        }
        else
        {
            return gcd_inner(b, r);
        }
    }
}
