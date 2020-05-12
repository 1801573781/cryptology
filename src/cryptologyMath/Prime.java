package cryptologyMath;

// 判断一个整数是否是素数
public class Prime
{
    public static boolean isPrime(int p)
    {
        if (0 == p)
        {
            return false;
        }
        else
        {
            ; // 空语句
        }

        int abs_p = Math.abs(p);

        if (1 == abs_p)
        {
            return false;
        }
        else
        {
            ; // 空语句
        }

        if (2 == abs_p)
        {
            return true;
        }
        else
        {
            ; // 空语句
        }

        if (0 == (abs_p % 2))
        {
            return false;
        }
        else
        {
            ; // 空语句
        }

        double sqrt_p = Math.sqrt(abs_p);
        int i;

        for (i = 0; i <= sqrt_p; ++i)
        {
            if (0 == (abs_p % i))
            {
                return false;
            }
        }

        return true;
    }
}
