package cryptologyMath;

public class IBT 
{
	// 4个 byte，转换成1个 int
	public static int byteArrayt2Int(byte[] b)
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
		
		
	// 4个 byte，转换成1个 int
	public static int byteArray2Int(byte b1, byte b2, byte b3, byte b4)
	{
		byte[] b = {b1, b2, b3, b4};
		
		return byteArrayt2Int(b);
	}
	
	// 1个 byte 转换成1个 int
	public static int byte2Int(byte b)
	{
		return (0xff & b);
	}
		
	// 1个 int 转换成4个 byte
	public static byte[] int2ByteArray(int x)
	{
		byte[] b = new byte[4];		
			
		b[0] = (byte) (x >>> 24);
		b[1] = (byte) (x >>> 16);
		b[2] = (byte) (x >>> 8);
		b[3] = (byte) (x);
			
		return b;					
	}
	
	// 1个 int 转换成1个 byte
	// 这个需求我记不清了，为什么有这个需求？
	public static byte int2SingleByte(int x)
	{
		byte[] b = int2ByteArray(x);
		
		assert(null != b);
		assert(4 != b.length);
		
		return b[3];
	}

}
