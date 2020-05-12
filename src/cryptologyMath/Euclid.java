package cryptologyMath;

public class Euclid
{
    // 欧几里得算法
    // 求解 d = gcd(a, b)
    public static int gcd(int a, int b)
    {
        // 1. 根据定义，gcd(0, 0) = 0
        if ((0 == a) && (0 == b))
        {
            return 0;
        }

        // 2. 将 a、b 取绝对值，然后排序，大的排前面
        int c[] = order(a, b);

        // 3. 如果 b 等于 0，则 gcd(a, b) = a
        if (0 == c[1])
        {
            return c[0];
        }

        // 4. 迭代计算
        return gcd_inner(c[0], c[1]);
    }

    // 将 a、b 取绝对值，然后排序，大的排前面
    private static int[] order(int a, int b)
    {
        int c[] = new int[2];

        c[0] = Math.abs(a);
        c[1] = Math.abs(b);

        // 2. 令 a >= b
        if (c[0] < c[1])
        {
            int tmp = c[0];
            c[0] = c[1];
            c[1] = tmp;
        }
        else
        {
            ;// 空语句
        }

        return c;

    }

    // 这里保证： a >= b > 0
    private static int gcd_inner(int a, int b)
    {
        assert(a > 0);
        assert(b > 0);
        assert(a >= b);

        int r = a % b;

        if (0 == r)
        {
            // 迭代结束
            return b;
        }
        else
        {
            // 修改 a， b 的值，继续迭代
            a = b;
            b = r;

            return gcd_inner(a, b);
        }
    }


    // 扩展欧几里得算法
    // ax + by = gcd(a, b), a, b, x, y 都是整数
    // 已知 a, b, 求解 x, y
    public static int[] ext_euclid(int a, int b)
    {
        // XY[0]，相当于 x，XY[1] 相当于 y
        int XY[] = new int[2];

        // 1. 先处理特殊情况： a == 0 or b == 0
        if (0 == a)
        {
            XY[0] = 0;
            XY[1] = 1;

            return XY;
        }
        else
        {
            ; // 空语句
        }

        if (0 == b)
        {
            XY[0] = 1;
            XY[1] = 0;

            return XY;
        }
        else
        {
            ; // 空语句
        }

        // 2. 将 a、b 取绝对值，然后排序，大的排前面
        int c[] = order(a, b);

        // 3. 赋初值：
        int x[] = new int[3];
        int y[] = new int[3];

        x[1] = 0;
        x[0] = 1;

        y[1] = 1;
        y[0] = 0;

        // 假设 gcd(a, b) = b:
        x[2] = 0;
        y[2] = 1;

        // 4. 迭代计算
        ext_euclid_inner(c[0], c[1], x, y);

        // 5. 构建返回值
        XY[0] = x[2];
        XY[1] = y[2];

        return XY;
    }


    // 这里保证： a >= b > 0, x[] : x[3], y[] : y[3]
    private static int ext_euclid_inner(int a, int b, int x[], int y[])
    {
        assert(a > 0);
        assert(b > 0);
        assert(a >= b);

        assert(null != x);
        assert(3 == x.length);

        assert(null != y);
        assert(3 == y.length);

        int r = a % b;
        int q = a / b;


        if (0 == r)
        {
            // 迭代结束
            return 0;
        }
        else
        {
            // 计算
            x[2] = x[0] - q * x[1];
            y[2] = y[0] - q * y[1];

            // 修改 a, b, x, y，继续迭代
            x[0] = x[1];
            x[1] = x[2];

            y[0] = y[1];
            y[1] = y[2];

            a = b;
            b = r;

            return ext_euclid_inner(a, b, x, y);
        }
    }



    // 多项式，求最大公因式：gcd(a(x), b(x))
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    // 只有 GF(p)、GF(p^n)，才支持求解最大公因式
    public static int[] gcd(int a[], int b[], int p)
    {
        assert(null != a);
        assert(null != b);

        int r[] = Polynomial.mod(a, b, p);

        if (Polynomial.isZero(r))
        {
            return b;
        }
        else
        {
            return gcd(b, r, p);
        }
    }





    // GF(p^n) 多项式，扩展欧几里得算法
    // a(x) * v(x) + b(x) * w(x) = gcd(a(x), b(x))
    public static int[][] ext_euclid(int a[], int b[], int p, int n)
    {
        assert(null != a);
        assert(null != b);


        // 1. 先初始化 v0, v1, w0, w1
        int[] v0 = new int[] {1};
        int[] v1 = new int[] {0};

        int[] w0 = new int [] {0};
        int[] w1 = new int [] {1};

        // 2. 迭代计算
        return ext_euclid_inner(a, b, v0, v1, w0, w1, p, n);
    }


    public static int[][] ext_euclid_inner(int a[], int b[], int v0[], int v1[], int w0[], int w1[], int p, int n)
    {
        int qr[][] = Polynomial.div(a, b, p);

        assert(null != qr);
        assert(2 == qr.length);

        int q[] = qr[0];
        int r[] = qr[1];

        if (Polynomial.isZero(r))
        {
            int vw[][] = new int[2][];

            vw[0] = v1;
            vw[1] = w1;

            return vw;
        }
        else
        {
            int v2[] = Polynomial.sub(v0, Polynomial.mul(q, v1, p, n), CalcMode.GFp_MODE, p);
            int w2[] = Polynomial.sub(w0, Polynomial.mul(q, w1, p, n), CalcMode.GFp_MODE, p);

            return ext_euclid_inner(b, r, v1, v2, w1, w2, p, n);
        }
    }




}
