package cryptologyMath;

public class Shift 
{
	
	// int 循环左移 s 位
	// 由于 int，byte 的循环左移算法都是相同的
	// 只是 s % len 涉及到 len
	// 而 int, byte 的 len 不同，所以 将 len 作为 参数传入
	public static int rotateLeftShift(int x, int s, int len)
	{
		assert(s >= 0);
		assert((8 == len) || (32 == len));
		
		s = s % len;		
		
		return ((x << s) | (x >>> (32 - s)));
	}	
	
	
	// int 循环左移 s 位，不带 len 参数
	public static int rotateLeftShift(int x, int s)
	{
		return rotateLeftShift(x, s, 32);
	}		
	
	// byte ѭ������
	public static byte rotateLeftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		
		int x = 0xff & b;
		
		x = rotateLeftShift(x, s, 8);
		
		return (byte)x;
	}		
	
	// byte 左移 s 位，不带 len 参数
	public static byte leftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		
		int x = 0xff & b;
		
		x = x << s;
		
		return (byte)x;
	}		
	
	
	// int array 循环左移 s 位
	// x[0] ... x[s-1] x[s] x[s+1] ... x[n-1] =》x[s] x[s+1] ... x[n-1] x[0] x[1] ... x[s-1]
	// 比如 [1,2,3,4] 左移2位，变成 [3,4,1,2]
	public static int[] rotateLeftShift(int[] x, int s)
	{
		assert(null != x);
		assert(0 <= s);		
				
		int len = x.length;
		
		// 1. 如果 s 比数组的 size 大，则 s 取模
		s = s % len;
		
		// 2. 如果 s 等于0，直接 return
		if (0 == s)
		{
			return x;
		}
		
		// 3. 循环左移
		int[] y = new int[len];

		System.arraycopy(x, s, y, 0, (len - s));
		System.arraycopy(x, 0, y, (len - s), s);
		
		return y;
	}	
	
	
	// byte array 数组循环左移
	// x[0] ... x[s-1] x[s] x[s+1] ... x[n-1] =》x[s] x[s+1] ... x[n-1] x[0] x[1] ... x[s-1]
	// 比如 [1,2,3,4] 左移2位，变成 [3,4,1,2]
	public static byte[] rotateLeftShift(byte[] b, int s)
	{
		assert(null != b);

		assert(0 <= s);

		int len = b.length;

		// 1. 如果 s 比数组的 size 大，则 s 取模
		s = s % len;

		// 2. 如果 s 等于0，直接 return
		if (0 == s)
		{
			return b;
		}

		// 3. 循环左移
		byte[] y = new byte[len];

		System.arraycopy(b, s, y, 0, (len - s));
		System.arraycopy(b, 0, y, (len - s), s);

		return y;
	}


}
