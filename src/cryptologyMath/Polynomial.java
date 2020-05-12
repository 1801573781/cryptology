package cryptologyMath;

/*
// 多项式运算：
// 1. 系数都是整数
// 2. a[] 代表多项式系数，其中：
// a[0] 代表最低系数(0 次幂的系数)，
// a[n - 1] 代表最低系数（(n - 1) 次幂的系数）
*/

public class Polynomial
{
    // 合法性判断：使用 assert
    private static void verify(int a[])
    {
        assert(null != a);

        // 最高位等于0，应该也可以
        //assert(0 != a[a.length - 1]); // 最高位不等于0

    }

    // 将1个多项式转换成 string
    public static String toString(int a[])
    {
        assert(null != a);

        int n = a.length;

        String s = "";
        int i;

        int abs_ai;

        for (i = (n - 1); i >= 0; --i)
        {
            if (0 != a[i])
            {
                if (0 < a[i])
                {
                    s += " + ";
                }
                else
                {
                    s += " - ";
                }

                abs_ai = Math.abs(a[i]);

                if (1 != abs_ai)
                {
                    s += Integer.toString(abs_ai) + " * ";
                }
                else
                {
                    ; // 空语句
                }

                s += "x" + "^" + Integer.toString(i);
            }
        }

        // if ("" == s），说明这个多项式，啥都没有
        if ("" == s)
        {
            s = "zero";
            return s;
        }
        else
        {
            ; // 空语句
        }

        // 去除开头的 " + " or " - "
        s = s.substring(3, s.length());

        // 在开头添加上符号，如果最高系数是负数的话
        if (0 > a[n - 1])
        {
            s = "-" + s;
        }
        else
        {
            ; // 空语句
        }

        // 去除结尾的 "x^0"
        if (0 != a[0])
        {
            s = s.substring(0, s.length() - 3);

            // 如果 “x^0” 的系数是“1” or “-1”， 还得将“1”补上
            if (1 == Math.abs(a[0]))
            {
                s += "1";
            }
            else
            {
                // 再去掉 “ * x”
                s = s.substring(0, s.length() - 3);
            }
        }
        else
        {
            ; // 空语句
        }

        // 再去掉结尾的 "^1"，如果有“1”次方的话
        if (2 <= n)
        {
            if (0 != a[1])
            {
                s = s.replace("x^1", "x");
            }
            else
            {
                ; // 空语句
            }
        }
        else
        {
            ; // 空语句
        }

        return s;
    }

    // 将多项式打印出来
    public static void println(int a[])
    {
        String s = toString(a);

        System.out.println(s);
    }

    // 多项式加法：normal add
    public static int[] add(int a[], int b[])
    {
        return add(a, b, CalcMode.NOARMAL_MODE, 0);
    }


    // 多项式加法：a(x) - b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    public static int[] add(int a[], int b[], CalcMode mode, int p)
    {
        verify(a);
        verify(b);

        int n = a.length;
        int m = b.length;

        // 1. 首先识别 a，b 谁的系数高，并做好初始化

        // 1.1 先假设 a的系数高
        int h = n; // h: high
        int l = m; // l: low
        int bigger[] = a;
        int smaller[] = null;


        // 1.2 如果 n >= m
        if (n >= m)
        {
            smaller = new int[h];
            System.arraycopy(b, 0, smaller, 0, l);
        }
        // 1.2 如果 n < m，则调整过来
        else
        {
            h = m;
            l = n;
            bigger = b;

            smaller = new int[h];
            System.arraycopy(a, 0, smaller, 0, l);
        }

        // 2. 开始相加

        int c[] = new int[h];

        int i;

        for (i = 0; i < h; ++i)
        {
            c[i] = Calc.add(bigger[i], smaller[i], mode, p);
        }

        return c;
    }

    // 多项式减法：normal sub
    public static int[] sub(int a[], int b[])
    {
        return sub(a, b, CalcMode.NOARMAL_MODE, 0);
    }

    // 多项式减法：a(x) - b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    public static int[] sub(int a[], int b[], CalcMode mode, int p)
    {
        verify(a);
        verify(b);

        int m = b.length;

        int c[] = new int[m];

        int i;

        for (i = 0; i < m; ++i)
        {
            c[i] = Calc.add_inv(b[i], mode, p);
        }

        return add(a, c, mode, p);
    }

    // 多项式乘法：normal mul
    public static int[] mul(int a[], int b[])
    {
        return mul(a, b, CalcMode.NOARMAL_MODE, 0);
    }

    // 多项式乘法：a(x) * b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    public static int[] mul(int a[], int b[], CalcMode mode, int p)
    {
        verify(a);
        verify(b);

        int n = a.length;
        int m = b.length;

        // 1. 构建新的系数
        int t = n + m - 1; // t: total，两者相乘，系数相加

        // 这里面利用了 java 数组的默认值属性：
        // int 数组，每个元素，默认为0
        int c[] = new int[t];
        int d[] = new int[t];

        System.arraycopy(a, 0, c, 0, n);
        System.arraycopy(b, 0, d, 0, m);

        // 2. 开始相乘

        // 相乘后的系数，仍然是利用了 java 的特性：数组元素默认为0
        int[] e = new int[t];
        int tmp = 0;

        int i;
        int j;

        for (i = 0; i < t; ++i)
        {
            for (j = 0; j <= i; ++j)
            {
                tmp = Calc.mul(c[j], d[i - j], mode, p);
                e[i] = Calc.add(e[i], tmp, mode, p);
            }
        }

        return e;

    }

    // 多项式除法：a(x) / b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    // d[0]: 商，d[1]: 余数
    // 如果 商、余数 为0，则 d[0]、d[1] 的值全为0
    // 只有 GF(p)、GF(p^n) 才支持除法
    // 但是，GF(p^n) 的除法（a(x)/b(x)），其算法是乘以 b(x) 的乘法逆元，不是此算法
    // 这个算法，就是 GF(p) 的除法 a(x)/b(x)
    public static int[][] div(int a[], int b[], int p)
    {
        verify(a);
        verify(b);

        // q[]：商，r[]：余数。两者都定义为 int[n]
        // 还是利用 java 数组的特性：两者初始化都为0
        int n = a.length;
        int[] q = new int[n];
        int[] r = new int[n];

        div_inner(a, b, p, q, r);

        int[][] d = new int[2][];
        d[0] = revise(q);
        d[1] = revise(r);

        return d;
    }


    //内部除法迭代函数
    private static int div_inner(int a[], int b[], int p, int q[], int r[])
    {
        verify(a);
        verify(b);

        assert(null != q);
        assert(null != r);

        // 1. 将 a, b 修正一下，去除最高幂的系数为0者
        int [] a2 = revise(a);
        int [] b2 = revise(b);

        // 说明除法已经结束
        if (isZero(a))
        {
            return 0;
        }

        int n = a2.length;
        int m = b2.length;

        // 2. 如果 a2 的系数比 b2 的低，那除法也就结束了
        // 余数就是 a2
        if (n < m)
        {
            System.arraycopy(a2, 0, r, 0, n);

            return 0;
        }

        // 3. 计算 a2[n - 1] / b2[m - 1]

        // 3.1 首先计算 a(x)/b(x) 中，x 的最高幂
        int power = n - m;

        // 3.2 然后计算，a[n - 1] / b[m - 1] 中，最高幂的系数
        int factor = Calc.div(a2[n - 1], b2[m - 1], CalcMode.GFp_MODE, p);

        // 4. 别忘了给 q[] 赋值
        q[power] = factor;

        // 5. 计算 d(x) = c(x) * b2(x)
        // c(x) = factor * x^(n-m)

        // 5.1 构建 c[]
        int c[] = new int[power + 1];
        c[power] = factor;

        // 5.2 计算 c(x) * b2(x)
        int d[] = mul(c, b2, CalcMode.GFp_MODE, p);

        // 6. 计算 e(x) = a2(x) - d(x)
        int e[] = sub(a2, d, CalcMode.GFp_MODE, p);

        // 7. 迭代计算
        return div_inner(e, b2, p, q, r);
    }


    // 修正一下 e[]，因为 e[最高位] 有可能等于0
    private static int[] revise(int e[])
    {
        assert(null != e);

        int n = e.length;

        // 1. 获取 e 的最高位不为0的index
        // 不抽取子函数了，就写在这里了。

        int h = -1;
        int i = 0;

        for (i = (n - 1); i >= 0; --i)
        {
            if (0 != e[i])
            {
                h = i;
                break;
            }
        }

        // 2. 修正 e[]

        // 所有位都为0
        if (-1 == h)
        {
            return new int[1];
        }
        else
        {
            int[] rv = new int [h + 1];

            System.arraycopy(e,0, rv,0, (h + 1));

            return rv;
        }
    }

    // 多项式，求模：r(x) = a(x) mod b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    // 只有 GF(p)，才支持求解最大公因式
    public static int[] mod(int a[], int b[], int p)
    {
        verify(a);
        verify(b);

        int qr[][] = div(a, b, p);

        assert(null != qr);
        assert(2 == qr.length);

        int r[] = qr[1];

        return r;
    }



    // GF(p^n) 多项式乘法：a(x) * b(x)
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    public static int[] mul(int a[], int b[], int p, int n)
    {
        // 由于涉及到 GF(p^n) 的既约多项式，而这些既约多项式，我现在也不知道怎么构建
        // 所以，只能支持几个有限的 p 和 n

        int[] m = getPrimePoly(p, n);

        if (null == m)
        {
            return null;
        }
        else
        {
            ; // do nothing
        }

        // 1. 按照 GF(p) 算法，计算乘法
        int [] c = mul(a, b, CalcMode.GFp_MODE, p);

        assert(null != c);

        // 2. 求解 c(x) mod m(x)

        int d[] = mod(c, m, p);

        return d;
    }

    // 获取既约多项式
    private static int[] getPrimePoly(int p, int n)
    {
        // 由于涉及到 GF(p^n) 的既约多项式，而这些既约多项式，我现在也不知道怎么构建
        // 所以，只能支持几个有限的 p 和 n

        if (2 == p)
        {
            if (3 == n)
            {
                return new int[] {1, 1, 0, 1};
            }
            else if (8 == n)
            {
                return new int[] {1, 1, 0, 1, 1, 0, 0, 0, 1};
            }
            else
            {
                ; // 空语句
            }

        }
        else
        {
            ; //空语句
        }

        return null;
    }

    // GF(p^n) b(x) 的乘法逆元
    public static int[] mul_inv(int b[], int p, int n)
    {
        assert(null != b);
        assert(Prime.isPrime(p));
        assert(2 <= n);

        int [] m = getPrimePoly(p, n);

        assert(null != m);

        int [][] vw = Euclid.ext_euclid(m, b, p, n);

        assert(null != vw);
        assert(2 == vw.length);
        assert(null != vw[1]);

        return mod(vw[1], m, p);
    }


    // GF(p^n) 多项式除法：a(x) / b(x)
    // a(x) / b(x) = a(x) * （b(x) 的乘法逆元）
    // a(x) = a[n-1] * x^(n-1) + ... + a[1] * x + a[0]
    // b(x) = b[m-1] * x^(m-1) + ... + b[1] * x + b[0]
    public static int[] div(int a[], int b[], int p, int n)
    {
        assert(null != a);
        assert(null != b);
        assert(Prime.isPrime(p));
        assert(2 <= n);

        int[] b_inv = mul_inv(b, p, n);

        assert(null != b_inv);

        return mul(a, b, p, n);
    }


    // 如果 r[] 中的每个元素都是0，那么 r[] 就是 zero
    public static boolean isZero(int r[])
    {
        if (null == r)
        {
            return true;
        }
        else
        {
            int n = r.length;

            int i;

            for (i = 0; i < n; ++i)
            {
                if (0 != r[i])
                {
                    return false;
                }
            }

            return true;
        }
    }


}
