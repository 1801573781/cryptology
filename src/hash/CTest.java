/**
 * 
 */
package hash;

import aes.*;
import logicalOper.*;

/**
 * @author lzb
 *
 */
public class CTest 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub								
		
		//System.out.println("hello world!");
		
		CMD5 md5 = new CMD5();
		CSHA256 sha256 = new CSHA256();
		
		
		String s = "abcd";
		int i = 0;
		int N = 9;
		
		/*
		for (i = 0; i < N; ++i)
		{
			s += s;
		}
		*/
		
		String strMD5 = md5.getMD5(s);
		String strSHA256 = sha256.getSHA256(s);
		
		System.out.println(strMD5);
		System.out.println(strSHA256);
		
		
		byte a = (byte)0xfe;
		int b = a;
		int c = 0xff & a;
		
		int d = 0xfe;
		byte e = (byte)d;
		int f = e;
		
		System.out.printf("a = %d\n", a);
		System.out.printf("b = %d\n", b);
		System.out.printf("c = %d\n", c);				
		System.out.printf("d = %d\n", d);
		System.out.printf("e = %d\n", e);
		System.out.printf("f = %d\n", f);
		
		
		byte[] key =
			{
					0x01, 0x02, 0x03, 0x04,
					0x05, 0x06, 0x07, 0x08,
					0x09, 0x0a, 0x0b, 0x0c,
					0x0d, 0x0e, 0x0f, 0x10
			};
		
		int[] W = null;
		
		WKey wkey = new WKey();
		
		try
		{		
			W = wkey.genWKey(AES_KEY_LEN.AES_128, key);
			
			int m = 5;
		}
		catch(AESException ex)
		{
			System.out.println(ex.toString());
		}
		
		
		byte[] x = {1, 2, 3, 4, 5, 6};
		
		byte[] y = Shift.rotateLeftShift(x, 3);
		
		for (i = 0; i < y.length; ++i)
		{
			System.out.printf("y[%d] = %d\n", i, y[i]);
		}
		
		byte z = Galois.mul((byte)0x3a, (byte)0x24);		
		
		System.out.printf("z = 0x%x\n", (0xff & z));
		
		System.out.println("hello IJ");

		System.out.println("hello eclipse");

		d = Euclid.gcd(28, 96);

		System.out.println(Integer.toString(d));

		System.out.println("test");

		int XY[] = Euclid.ext_gcd(1759, 550);

		System.out.printf("x = %d, y = %d\n", XY[0], XY[1]);

	}
}
