package logicalOper;

public class Galois 
{
	private static int[][] E = 
		{
				//0    1     2     3     4     5     6     7     8     9     a     b     c     d     e     f
				{0x01, 0x03, 0x05, 0x0f, 0x11, 0x33, 0x55, 0xff, 0x1a, 0x2e, 0x72, 0x96, 0xa1, 0xf8, 0x13, 0x35}, //0
				{0x5f, 0xe1, 0x38, 0x48, 0xd8, 0x73, 0x95, 0xa4, 0xf7, 0x02, 0x06, 0x0a, 0x1e, 0x22, 0x66, 0xaa}, //1
				{0xe5, 0x34, 0x5c, 0xe4, 0x37, 0x59, 0xeb, 0x26, 0x6a, 0xbe, 0xd9, 0x70, 0x90, 0xab, 0xe6, 0x31}, //2
				{0x53, 0xf5, 0x04, 0x0c, 0x14, 0x3c, 0x44, 0xcc, 0x4f, 0xd1, 0x68, 0xb8, 0xd3, 0x63, 0xb2, 0xcd}, //3
				{0xfc, 0xd4, 0x67, 0xa9, 0xe0, 0x3b, 0x4d, 0xd7, 0x62, 0xa6, 0xf1, 0x08, 0x18, 0x28, 0x78, 0x88}, //4
				{0x83, 0x9e, 0xb9, 0xd0, 0x6b, 0xbd, 0xdc, 0x7f, 0x81, 0x98, 0xb3, 0xce, 0x49, 0xdb, 0x76, 0x9a}, //5
				{}
		};
	
	
	private static byte add(byte b1, byte b2)
	{
		int x = 0xff & b1;
		int y = 0xff & b2;
		
		int z = x ^ y;
		
		return (byte)z;
	}
	
	// b 乘以 0
	private static byte mul_0(byte b)
	{
		return 0;
	}
	
	
	// b 乘以 1
	private static byte mul_1(byte b)
	{
		return b;
	}
	
	// b 乘以 2
	private static byte mul_2(byte b)
	{
		// 先把 b 左移1位
		byte c = Shift.leftShift(b, 1);				
		
		// 获取 b 的最高位
		int flag = (0b10000000) & b;
		
		
		if (0 == flag)
		{
			// 如果最高位等于0，那么 return c
			return c;
		}
		else
		{
			// 如果最高位等于1，那么 return (c ^ )
			return (byte) (c ^ (0b00011011));
		}		
	}
	
	
	// b 乘以 n，n 等于0，或者（n 必须是2的m次方 and n必须小于等于128）
	private static byte mul_base(byte b, int n)
	{		
		assert(128 >= n);
		
		if (0 == n)
		{
			return mul_0(b);
		}
		
		if (1 == n)
		{
			return mul_1(b);
		}
		
		byte c = mul_2(b);
		
		if (2 == n)
		{
			return c;
		}
		
		int m = log2(n) - 1;
		
		int i;				
		
		for (i = 1; i < m; ++i)
		{
			c = mul_2(c);
		}
		
		return c;		
	}
	
	
	
	// 将 b 分解成多个基数的异或
	private static byte[] seperate(byte b)
	{
		byte c[] = new byte[8];
		
		c[0] = (byte) ((0b10000000) & b);
		c[1] = (byte) ((0b01000000) & b);
		c[2] = (byte) ((0b00100000) & b);
		c[3] = (byte) ((0b00010000) & b);
		c[4] = (byte) ((0b00001000) & b);
		c[5] = (byte) ((0b00000100) & b);
		c[6] = (byte) ((0b00000010) & b);
		c[7] = (byte) ((0b00000001) & b);		
		
		return c;
	}
	
	// 两个 byte 相乘
	public static byte mul(byte b1, byte b2)
	{
		// 1. 将 b1 乘以各个基数（这里采用递归，性能有问题，现在头晕，暂时不优化了）
		byte [] c1 = new byte[8];
		
		int i;
		
		for (i = 0; i < 8; ++i)
		{
			double x = Math.pow(2, i);
			c1[i] = mul_base(b1, (int)x);
		}
		
		// 2. 将 b2 转换为8个基数的异或
		byte [] c2 = seperate(b2);
		
		// 3. 根据 c2，修正 c1
		for (i = 0; i < 8; ++i)
		{
			if (0 == c2[i])
			{
				c1[i] = 0;
			}
		}
		
		// 4. 将8个 c1[i] 异或
		byte d = add(c1[0], c1[1]);
		
		for (i = 2; i < 8; ++i)
		{
			d = add(d, c1[i]);
		}				
		
		return d;
	}
	
	
	// java 中没有现成的计算 log2(n) 的函数，我也很无耐啊
	// 暂时只用到这1个计算，就不另外写个 class 了
	private static int log2(int n)
	{
		assert (0 < n);
		
		int i = 0;
		
		while (true)
		{
			n = n >> 1;
			
			if (0 < n)
			{
				++i;
			}
			else
			{
				return i;
			}
		}
	}

}
