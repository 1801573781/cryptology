package cryptologyMath;

public class Calc
{
    public static final int NORMAL = 0;
    public static final int GFn = 1;
    public static final int GFp = 2;


    public static int add(int a, int b, CalcMode mode, int p)
    {
        switch (mode)
        {
            case GFn_MODE:
                return GFnCal.add(a, b, p);

            case GFp_MODE:
                return GFpCal.add(a, b, p);

            default:
                return (a + b);
        }
    }


    public static int sub(int a, int b, CalcMode mode, int p)
    {
        switch (mode)
        {
            case GFn_MODE:
                return GFnCal.sub(a, b, p);

            case GFp_MODE:
                return GFpCal.sub(a, b, p);

            default:
                return (a - b);
        }
    }


    public static int mul(int a, int b, CalcMode mode, int p)
    {
        switch (mode)
        {
            case GFn_MODE:
                return GFnCal.mul(a, b, p);

            case GFp_MODE:
                return GFpCal.mul(a, b, p);

            default:
                return (a * b);
        }
    }


    public static int div(int a, int b, CalcMode mode, int p)
    {
        switch (mode)
        {
            case GFn_MODE:
                return GFnCal.div(a, b, p);

            case GFp_MODE:
                return GFpCal.div(a, b, p);

            default:
                return (a * b);
        }
    }

    public static int add_inv(int a, CalcMode mode, int p)
    {
        switch (mode)
        {
            case GFn_MODE:
                return GFnCal.add_inv(a, p);

            case GFp_MODE:
                return GFpCal.add_inv(a, p);

            default:
                return (-a);
        }
    }


}
