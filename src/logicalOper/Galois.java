package logicalOper;

public class Galois 
{		
	// b1 + b2，实际上等于 b1 xor b2
	private static byte add(byte b1, byte b2)
	{		
		return (byte) ((0xff & b1) ^ (0xff & b2));
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
	
	
	// b 乘以基数：1,2,4,8,16,32,64,128
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
	
	
	// 将 b 分解成多个基数的异或
	private static byte[] seperate(byte b)
	{
		byte c[] = new byte[8];
		
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
	
	// 两个 byte 相乘
	public static byte mul(byte b1, byte b2)
	{
		// 1. 将 b1 乘以各个基数
		byte [] c1 = mul_base(b1);
		
		// 2. 将 b2 转换为8个基数的异或
		byte [] c2 = seperate(b2);
		
		// 3. 根据 c2，修正 c1
		int i;
		
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

}
