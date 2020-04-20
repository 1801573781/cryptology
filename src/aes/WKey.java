package aes;

import logicalOper.*;

public class WKey 
{
	// 原始密钥
	private byte origin_key[] = null;
	
	// 原始密钥长度
	AES_KEY_LEN origin_key_len = AES_KEY_LEN.AES_256;
	
	// 工作密钥，由原始密钥扩展而得
	private int wkey[] = null;
	
	// 工作密钥长度
	private int wkey_len = 0;
	
	// round const: 轮常量
	private int rc[] =
		{
				0x01, 0x02, 0x04, 0x08,
				0x10, 0x20, 0x40, 0x80,
				0x1b, 0x36				
		};
	
	
	public int[] genWKey(AES_KEY_LEN len, byte[] key) throws CAESException
	{
		// 1. 合法性判断：key
		if (null == key)
		{
			throw new CAESException("key is null");
		}
		
		// 2. 合法性判断：len
		if ((key.length * 8) != len.len())
		{
			String s = String.format("key len(%d) is not eaqual %s", (key.length * 8), len.toString()); 
					
			throw new CAESException(s);
		}
		
		switch (len)
		{
		case AES_128:
			return genWKey_128(key);			
			
		default:
			break;
			
		
		}
		
		return null;
	}
	
	
	private int[] genWKey_128(byte[] key)
	{
		assert(null != key);
		
		wkey_len = 44;
		this.wkey = new int[wkey_len];
		
		genFirst4WKkey(key);
		genTheOtherWKey();

		return this.wkey;
	}
	
	private int genFirst4WKkey(byte[] key)
	{
		assert(null != key);
		assert(null != wkey);
		
		int i, j;		 
		
		for (i = 0; i < 4; ++i)
		{
			wkey[i] = 0x0;	
			
			wkey[i] = Calc.byte2Int(key[i], key[4 + i], key[8 + i], key[12 + i]);						
			
			System.out.printf("wkey[%d] = 0x%x\n", i, wkey[i]);
		}				
		
		return 0;
	}
	
	
	private int genTheOtherWKey()
	{
		int i = 0;
		int tmp;
		
		for (i = 4; i < this.wkey_len; ++i)
		{
			tmp = wkey[i - 1];
			
			if (0 == (i % 4))
			{
				// 1. 循环左移8位
				tmp = Calc.rotateLeftShift(tmp);
				
				// 2. S盒变换
				tmp = SBox.translate(tmp);
				
				// 3. XOR
				tmp ^= this.rc[i / 4 - 1];
			}
			
			// 异或
			wkey[i] = wkey[i - 4] ^ tmp;
		}
		
		return 0;
	}
	
	

}
