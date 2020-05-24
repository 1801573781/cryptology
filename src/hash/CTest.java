/**
 * 
 */
package hash;

import aes.*;
import cryptologyMath.*;

import java.io.IOException;

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


		BMPTest bmpTest = new BMPTest();
		try
		{
			bmpTest.test("D:\\密码学\\book\\AES\\11 AES 加密工作模式\\image\\test.bmp");
		}
		catch (IOException ex)
		{
			System.out.println(ex.toString());
		}
		
		byte[] key =
			{
					0x01, 0x02, 0x03, 0x04,
					0x05, 0x06, 0x07, 0x08,
					0x09, 0x0a, 0x0b, 0x0c,
					0x0d, 0x0e, 0x0f, 0x10
			};

		byte[] plainText =
			{
					0x01, 0x02, 0x03, 0x04,
					0x05, 0x06, 0x07, 0x08,
					0x09, 0x0a, 0x0b, 0x0c,
					0x0d, 0x0e, 0x0f, 0x10
			};

		AES aes = new AES();

		byte[] cipherText = aes.encrypt(key, plainText);

		
		
		byte[] x = {1, 2, 3, 4, 5, 6};
		
		byte[] y = Shift.rotateLeftShift(x, 3);
		
		for (i = 0; i < y.length; ++i)
		{
			System.out.printf("y[%d] = %d\n", i, y[i]);
		}
		
		byte z = Galois2exp8.mul((byte)0x3a, (byte)0x24);
		
		System.out.printf("z = 0x%x\n", (0xff & z));
		
		System.out.println("hello IJ");

		System.out.println("hello eclipse");

		d = Euclid.gcd(28, 96);

		System.out.println(Integer.toString(d));

		System.out.println("test");

		int XY[] = Euclid.ext_euclid(1759, 550);

		System.out.printf("x = %d, y = %d\n", XY[0], XY[1]);

		int ddd[] = {-9, -20, 0, 1, -1, -3};

		Polynomial.println(ddd);


		ddd = new int[] {1, 0, 0, 1, -1, -1};

		Polynomial.println(ddd);

		ddd = Polynomial.sub(new int[] {3, 2, 1, 0}, new int[] {1, 2, 3, 4});
		Polynomial.println(ddd);


		ddd = Polynomial.mul(new int[] {-1, 1, 0, 0, 0}, new int[] {1, 1, 0, 0, 0});
		Polynomial.println(ddd);

		System.out.println("div1");

		int[][] dddd = Polynomial.div(new int[] {1, 1, 0, 1, 0, 0}, new int[] {1, 1, 0, 0}, 2);
		Polynomial.println(dddd[0]);
		Polynomial.println(dddd[1]);

		System.out.println("div2");

		dddd = Polynomial.div(new int[] {0, 0, 5}, new int[] {0, 3}, 7);
		Polynomial.println(dddd[0]);
		Polynomial.println(dddd[1]);

		System.out.println("gcd1");

		ddd = Euclid.gcd(new int[] {0, 0, 5}, new int[] {0, 3}, 7);
		Polynomial.println(ddd);

		System.out.println("gcd2");

		ddd = Euclid.gcd(new int[] {1, 1, 0, 1}, new int[] {1, 1}, 2);
		Polynomial.println(ddd);


		System.out.println("GF(2^8)");

		ddd = Polynomial.mul(new int[] {0, 1, 1}, new int[] {1, 0, 1}, 2, 3);
		Polynomial.println(ddd);

		System.out.println("mul_inv");

		ddd = Polynomial.mul_inv(new int[] {1, 1, 0, 0, 0, 0, 0, 1}, 2, 8);
		Polynomial.println(ddd);

		ddd = Polynomial.mul(new int[] {1, 1, 0, 0, 0, 0, 0, 1}, new int[] {0, 0, 0, 0, 0, 0, 0, 1}, 2, 8);
		Polynomial.println(ddd);

		System.out.println("GF(2^8) div");
		byte bbb = Galois2exp8.div((byte)28, (byte)56);
		Galois2exp8.printUnsignedByteln(bbb);

		System.out.println("2 * 36");
		bbb = Galois2exp8.mul((byte)2,(byte)0x36);
		Galois2exp8.printUnsignedByteln(bbb);

		System.out.println("2 * bbb");
		bbb = Galois2exp8.mul((byte)2,bbb);
		Galois2exp8.printUnsignedByteln(bbb);

		System.out.println("2 * bbb");
		bbb = Galois2exp8.mul((byte)2,bbb);
		Galois2exp8.printUnsignedByteln(bbb);

		System.out.println("2 * bbb");
		bbb = Galois2exp8.mul((byte)2,bbb);
		Galois2exp8.printUnsignedByteln(bbb);






	}
}
