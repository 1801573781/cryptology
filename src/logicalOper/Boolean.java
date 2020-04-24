package logicalOper;

// ²¼¶ûÔËËã
public class Boolean 
{
	public static byte[] xor(byte[] b, int x)
	{
		assert(null != b);
		
		int len = b.length;
		
		assert(4 == len);
		
		int y = IBT.byteArrayt2Int(b);
		
		y = y ^ x;
		
		return IBT.int2ByteArray(y);				
	}
	
	public static byte xor(byte b1, byte b2)
	{
		int x = 0xff & b1;
		int y = 0xff & b2;
		
		return (byte) (x ^ y);
	}

}
