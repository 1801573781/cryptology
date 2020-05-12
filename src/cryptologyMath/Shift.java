package cryptologyMath;

public class Shift 
{
	
	// int ѭ������
	public static int rotateLeftShift(int x, int s, int len)
	{
		// int, byte ��ѭ�����ƣ���ʹ��������������� byte ��ת���� int ���ô˺���
		// len ��ʶ x ��ԭ��������ռ�еı������������� byte���� len ���� 8
		
		assert(s >= 0);
		assert((8 == len) || (32 == len));
		
		s = s % len;		
		
		return ((x << s) | (x >>> (32 - s)));
	}	
	
	
	// int ѭ������
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
	
	// byte ����
	public static byte leftShift(byte b, int s)
	{
		assert(s >= 0);
		assert(s < 8);
		
		
		int x = 0xff & b;
		
		x = x << s;
		
		return (byte)x;
	}		
	
	
	// int ���飬ѭ������
	// �� x[0] ... x[s-1] �ŵ� x[] �Ķ�β
	// ���磺[1,2,3,4]��ѭ������2λ����ɣ�[3,4,1,2]
	public static int[] rotateLeftShift(int[] x, int s)
	{
		assert(null != x);
		assert(0 <= s);		
				
		int len = x.length;
		
		// 1. �� s �� len ȡ�ࡣ��Ϊ len ��������ѭ�� ���ƣ�x[] ���ֲ���
		s = s % len;
		
		// 2. ��� s == 0����ô�͵���û���ƶ�
		if (0 == s)
		{
			return x;
		}
		
		// 3. x[s] ����� x[0]���������ơ���������ʱ�ȴ������ʱ���� tmp1 ��
		int i;
		int m = len -s;
		int[] y = new int[len];
			
		
		for (i = 0; i < m; ++i)
		{
			y[i] = x[s + i];
			
		}
		
		// 4. x[len - s] ����� x[0]���Դ�����		
		for (i = 0; i < s; ++i)
		{
			y[m + i] = x[i];
		}							
		
		return y;
	}	
	
	
	// byte ���飬ѭ������
	// �� x[0] ... x[s-1] �ŵ� x[] �Ķ�β
	// ���磺[1,2,3,4]��ѭ������2λ����ɣ�[3,4,1,2]
	// Ϊ�˴��븴�ã���Ҫ�Ǹ����߼����㣬��Ϊֻ�������еĻ�����������д������û�н�Լ���ٴ����ϣ�����������Ч��	
	public static byte[] rotateLeftShift(byte[] b, int s)
	{
		assert(null != b);
		
		int len = b.length;
		
		int[] x = new int[len];
		
		int i;
		
		for (i = 0; i < len; ++i)
		{
			x[i] = IBT.byte2Int(b[i]);
		}
		
		int[] y = rotateLeftShift(x, s);
		
		byte[] c = new byte[len];
		
		for (i = 0; i < len; ++i)
		{
			c[i] = IBT.int2SingleByte(y[i]);
		}
		
		return c;		
	}
	
	
	// byte ���飬ѭ������
	// �� x[0] ... x[s-1] �ŵ� x[] �Ķ�β
	// ���磺[1,2,3,4]��ѭ������2λ����ɣ�[3,4,1,2]
	// Ϊ�˴���Ч�ʣ��������ظ�
	public static byte[] rotateLeftShiftB(byte[] x, int s)
	{
		assert(null != x);
		assert(0 <= s);		
				
		int len = x.length;
		
		// 1. �� s �� len ȡ�ࡣ��Ϊ len ��������ѭ�� ���ƣ�x[] ���ֲ���
		s = s % len;
		
		// 2. ��� s == 0����ô�͵���û���ƶ�
		if (0 == s)
		{
			return x;
		}
		
		// 3. x[s] ����� x[0]���������ơ���������ʱ�ȴ������ʱ���� tmp1 ��
		int i;
		int m = len -s;
		byte[] y = new byte[len];
			
		
		for (i = 0; i < m; ++i)
		{
			y[i] = x[s + i];
			
		}
		
		// 4. x[len - s] ����� x[0]���Դ�����		
		for (i = 0; i < s; ++i)
		{
			y[m + i] = x[i];
		}							
		
		return y;
	}			

}
