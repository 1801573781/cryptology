package cryptologyMath;

public class Galois2exp8
{		
	// b1 + b2，在 GF(2^8) 里，等价于：b1 xor b2
	public static byte add(byte b1, byte b2)
	{		
		return (byte) ((0xff & b1) ^ (0xff & b2));
	}


	// b1 - b2，在 GF(2^8) 里，等价于：b1 xor b2
	public static byte sub(byte b1, byte b2)
	{
		return (byte) ((0xff & b1) ^ (0xff & b2));
	}


	// b1 * b2：算法1
	public static byte mul(byte b1, byte b2)
	{
		// 1. 计算 b1 与 byte 基本盘相乘的结果
		byte [] c1 = mul_base(b1);

		// 2. 将 b2 分解为8个 bit
		byte [] c2 = seperate(b2);

		// 3. 根据 c2，重构 c1
		int i;

		for (i = 0; i < 8; ++i)
		{
			if (0 == c2[i])
			{
				c1[i] = 0;
			}
		}

		// 4. c1 相加
		byte d = add(c1[0], c1[1]);

		for (i = 2; i < 8; ++i)
		{
			d = add(d, c1[i]);
		}

		return d;
	}

	public static byte div(byte b1, byte b2)
	{
		int[] f = byte2Poly(b1);
		int[] g = byte2Poly(b2);

		assert(null != f);
		assert(null != g);

		int[] h = Polynomial.div(f, g, 2, 8);

		assert(null != h);

		return poly2Byte(h);
	}

	public static void printUnsignedByteln(byte b)
	{
		int n = 0xff & b;

		System.out.println(n);
	}


	// b * 0 = 0
	private static byte mul_0(byte b)
	{
		return 0;
	}
	
	
	// b * 1 = b
	private static byte mul_1(byte b)
	{
		return b;
	}
	
	// b * 2
	// b 的二进制表示形式为：b7-b6-b5-b4-b3-b2-b1-b0
	private static byte mul_2(byte b)
	{
		// b 左移1位，变成：b6-b5-b4-b3-b2-b1-0
		byte c = Shift.leftShift(b, 1);				
		
		// 计算 b7
		int flag = (0b10000000) & b;
		
		
		if (0 == flag)
		{
			// 如果 b7 == 0，return c
			return c;
		}
		else
		{
			// 如果 b7 == 1
			return (byte) (c ^ (0b00011011));
		}		
	}		


	// b 分别乘以 byte 的基本盘
	// byte 的基本盘是：1,2,4,8,16,32,64,128
	private static byte[] mul_base(byte b)
	{
		byte [] c = new byte[8];
		
		c[0] = mul_1(b);		
		
		int i;
		
		for (i = 1; i < 8; ++i)
		{
			c[i] = mul_2(c[i-1]);
		}
		
		return c;
	}	
	
	
	// 将1个 byte，分解为8个bit
	// c[0] 记录最低 bit，c[7] 记录最高 bit
	private static byte[] seperate(byte b)
	{
		byte[] c = new byte[8];
		
		c[0] = (byte) ((0b00000001) & b);
		c[1] = (byte) ((0b00000010) & b);
		c[2] = (byte) ((0b00000100) & b);
		c[3] = (byte) ((0b00001000) & b);
		c[4] = (byte) ((0b00010000) & b);
		c[5] = (byte) ((0b00100000) & b);
		c[6] = (byte) ((0b01000000) & b);
		c[7] = (byte) ((0b10000000) & b);
		
		return c;
	}

	// 将1个 byte 转换成 GF(2^8) 的多项式
	private static int[] byte2Poly(byte b)
	{
		byte[] c = seperate(b);

		assert(null != c);

		int[] a = new int[8];

		int i;

		for (i = 0; i < 8; ++i)
		{
			if (0 != c[i])
			{
				a[i] = 1;
			}
			else
			{
				; //空语句
			}
		}

		return a;
	}

	// 将1个 GF(2^8) 的多项式 转换成 byte
	private static byte poly2Byte(int a[])
	{
		assert(null != a);

		// GF(2^8) 的 基本盘
		int [] base = new int[] {1,2,4,8,16,32,64,128};

		int n = a.length;

		int i;
		int d = 0;

		for (i = 0; i < n; ++i)
		{
			d += a[i] * base[i];
		}

		return (byte)d;
	}
	


}
