package logicalOper;

public class Shift 
{
	
	// int 循环左移
	public static int rotateLeftShift(int x, int s, int len)
	{
		// int, byte 的循环左移，都使用这个函数，但是 byte 会转换成 int 调用此函数
		// len 标识 x 的原本类型所占有的比特数量，比如 byte，其 len 等于 8
		
		assert(s >= 0);
		assert((8 == len) || (32 == len));
		
		s = s % len;		
		
		return ((x << s) | (x >>> (32 - s)));
	}	
	
	
	// int 循环左移
	public static int rotateLeftShift(int x, int s)
	{
		return rotateLeftShift(x, s, 32);
	}		
	
	// byte 循环左移
	public static byte rotateLeftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		
		int x = 0xff | b;
		
		x = rotateLeftShift(x, s, 8);
		
		return (byte)x;
	}		
	
	// byte 左移
	public static byte leftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		
		int x = 0xff | b;
		
		x = x << s;
		
		return (byte)x;
	}		
	
	
	// int 数组，循环左移
	// 将 x[0] ... x[s-1] 放到 x[] 的队尾
	// 比如：[1,2,3,4]，循环左移2位，变成：[3,4,1,2]
	public static int[] rotateLeftShift(int x[], int s)
	{
		assert(null != x);
		assert(0 <= s);		
				
		int len = x.length;
		
		// 1. 将 s 对 len 取余。因为 len 的整数倍循环 左移，x[] 保持不变
		s = s % len;
		
		// 2. 如果 s == 0，那么就等于没有移动
		if (0 == s)
		{
			return x;
		}
		
		// 3. x[s] 变成了 x[0]，依次类推——不过此时先存放在临时数组 tmp1 中
		int i;
		int m = len -s;
		int y[] = new int[len];			
			
		
		for (i = 0; i < m; ++i)
		{
			y[i] = x[s + i];
			
		}
		
		// 4. x[len - s] 变成了 x[0]，以此类推		
		for (i = 0; i < s; ++i)
		{
			y[m + i] = x[i];
		}							
		
		return y;
	}	
	
	
	// byte 数组，循环左移
	// 将 x[0] ... x[s-1] 放到 x[] 的队尾
	// 比如：[1,2,3,4]，循环左移2位，变成：[3,4,1,2]
	// 为了代码复用（主要是复用逻辑运算，因为只看代码行的话，下面这种写法，并没有节约多少代马上），牺牲运行效率	
	public static byte[] rotateLeftShift(byte b[], int s)
	{
		assert(null != b);
		
		int len = b.length;
		
		int x[] = new int[len];
		
		int i;
		
		for (i = 0; i < len; ++i)
		{
			x[i] = IBT.byte2Int(b[i]);
		}
		
		int y[] = rotateLeftShift(x, s);
		
		byte c[] = new byte[len];
		
		for (i = 0; i < len; ++i)
		{
			c[i] = IBT.int2SingleByte(y[i]);
		}
		
		return c;		
	}
	
	
	// byte 数组，循环左移
	// 将 x[0] ... x[s-1] 放到 x[] 的队尾
	// 比如：[1,2,3,4]，循环左移2位，变成：[3,4,1,2]
	// 为了代码效率，代码变成重复
	public static byte[] rotateLeftShiftB(byte x[], int s)
	{
		assert(null != x);
		assert(0 <= s);		
				
		int len = x.length;
		
		// 1. 将 s 对 len 取余。因为 len 的整数倍循环 左移，x[] 保持不变
		s = s % len;
		
		// 2. 如果 s == 0，那么就等于没有移动
		if (0 == s)
		{
			return x;
		}
		
		// 3. x[s] 变成了 x[0]，依次类推——不过此时先存放在临时数组 tmp1 中
		int i;
		int m = len -s;
		byte y[] = new byte[len];			
			
		
		for (i = 0; i < m; ++i)
		{
			y[i] = x[s + i];
			
		}
		
		// 4. x[len - s] 变成了 x[0]，以此类推		
		for (i = 0; i < s; ++i)
		{
			y[m + i] = x[i];
		}							
		
		return y;
	}			

}
