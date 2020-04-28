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

        // 3. 计算 r1：a = q1 * b + r1, r1 = a % b
        int r1 = a % b;

        if (0 == r1)
        {
            return b;
        }

        // 4. 计算 r2：b = q2 * r1 + r2, r2 = b % r1
        int r2 = b % r1;

        if (0 == r2)
        {
            return r1;
        }

        // 5. 迭代计算
        int r3;

        while (true)
        {
            r3 = r1 % r2;

            if (0 == r3)
            {
                return r2;
            }

            r1 = r2;
            r2 = r3;
        }
    }
}
