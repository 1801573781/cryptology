/**
 * 
 */
package hash;

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
	}
}
