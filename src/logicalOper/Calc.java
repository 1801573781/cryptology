package logicalOper;

public class Calc 
{
	// int 循环左移
	public static int rotateLeftShift(int x, int s)
	{
		assert(s >= 0);
		assert(s < 32);
		
		return ((x << s) | (x >> (32-s)));
	}
	
	// int 循环左移
	public static int rotateLeftShift(int x)
	{
		return rotateLeftShift(x, 8);
	}	
	
	// byte 循环左移
	public static byte rotateLeftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		return (byte)((b << s) | (b >> (32-s)));
	}	
	
	
	// byte 循环左移
	public static byte rotateLeftShift(byte b)
	{
		return rotateLeftShift(b, 8);
	}	
	
	
	// 4个 byte，转成1个 int
	public static int byte2Int(byte[] bs)
	{
		assert(bs != null);
		assert(4 == bs.length);
		
		int i = 0;
		int a = 0;
		
		for (i = 0; i < 4; ++i)
		{
			a |= (0xff & bs[i]);
			
			if (i < 3)
			{				
				a = a << 8;
			}
		}
		
		return a;
	}
	
	
	// 4个 byte，转成1个 int
	public static int byte2Int(byte b1, byte b2, byte b3, byte b4)
	{
		int a = 0;
		
		a |= (0xff & b1);
		a = a << 8;
		
		a |= (0xff & b2);
		a = a << 8;
		
		a |= (0xff & b3);
		a = a << 8;
		
		a |= (0xff & b4);		
		
		return a;
	}
	
	// 1个 int，转成4个 byte
	public static byte[] int2Byte(int x)
	{
		byte[] bs = new byte[4];
		int tmp;
		
		bs[0] = (byte)(x >>> 24);
		bs[1] = (byte)(x >>> 16);
		bs[2] = (byte)(x >>> 8);
		bs[3] = (byte)x;
		
		return bs;					
	}
		
	
}
