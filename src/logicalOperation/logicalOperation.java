package logicalOperation;

public class logicalOperation 
{
	// int 循环左移
	public static int rotateLeftShift(int x, int s)
	{
		assert(s >= 0);
		assert(s < 32);
		
		return ((x << s) | (x >> (32-s)));
	}
	
	// byte 循环左移
	public static byte rotateLeftShift(byte x, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		return (byte)((x << s) | (x >> (32-s)));
	}	
	
	// 4个 byte，转成1个 int
	public static int byte2Int(byte[] b)
	{
		assert(b != null);
		assert(4 == b.length);
		
		int i = 0;
		int a = 0;
		
		for (i = 0; i < 4; ++i)
		{
			a |= (0xff & b[i]);
			
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
		
	
}
