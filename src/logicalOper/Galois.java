package logicalOper;

public class Galois 
{		
	// b1 + b2��ʵ���ϵ��� b1 xor b2
	private static byte add(byte b1, byte b2)
	{		
		return (byte) ((0xff & b1) ^ (0xff & b2));
	}
	
	// b ���� 0
	private static byte mul_0(byte b)
	{
		return 0;
	}
	
	
	// b ���� 1
	private static byte mul_1(byte b)
	{
		return b;
	}
	
	// b ���� 2
	private static byte mul_2(byte b)
	{
		// �Ȱ� b ����1λ
		byte c = Shift.leftShift(b, 1);				
		
		// ��ȡ b �����λ
		int flag = (0b10000000) & b;
		
		
		if (0 == flag)
		{
			// ������λ����0����ô return c
			return c;
		}
		else
		{
			// ������λ����1����ô return (c ^ )
			return (byte) (c ^ (0b00011011));
		}		
	}		
	
	
	// b ���Ի�����1,2,4,8,16,32,64,128
	private static byte[] mul_base(byte b)
	{
		byte [] c = new byte[8];
		
		c[0] = mul_1(b);		
		
		int i;
		
		for (i = 1; i < 8; ++i)
		{
			c[i] = mul_2(c[i-1]);
		}
		
		return c;
	}	
	
	
	// �� b �ֽ�ɶ�����������
	private static byte[] seperate(byte b)
	{
		byte[] c = new byte[8];
		
		c[0] = (byte) ((0b00000001) & b);
		c[1] = (byte) ((0b00000010) & b);
		c[2] = (byte) ((0b00000100) & b);
		c[3] = (byte) ((0b00001000) & b);
		c[4] = (byte) ((0b00010000) & b);
		c[5] = (byte) ((0b00100000) & b);
		c[6] = (byte) ((0b01000000) & b);
		c[7] = (byte) ((0b10000000) & b);
		
		return c;
	}
	
	// ���� byte ���
	public static byte mul(byte b1, byte b2)
	{
		// 1. �� b1 ���Ը�������
		byte [] c1 = mul_base(b1);
		
		// 2. �� b2 ת��Ϊ8�����������
		byte [] c2 = seperate(b2);
		
		// 3. ���� c2������ c1
		int i;
		
		for (i = 0; i < 8; ++i)
		{
			if (0 == c2[i])
			{
				c1[i] = 0;
			}
		}
		
		// 4. ��8�� c1[i] ���
		byte d = add(c1[0], c1[1]);
		
		for (i = 2; i < 8; ++i)
		{
			d = add(d, c1[i]);
		}				
		
		return d;
	}		

}
