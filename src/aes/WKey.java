package aes;

import cryptologyMath.*;

// Work Key，也就是扩展密钥
public class WKey 
{
	// 原始密钥长度
	int origin_key_len_word = AES_KEY_LEN.AES_256.len();
	
	// 工作密钥（扩展密钥）
	private int[] wkey = null;
	
	// 工作密钥（扩展密钥）长度，长度单位是 word
	private int wkey_len_word = 0;

	// 加密轮数
	private int round = 0;
	
	// round const: 轮常数
	private final int[] rc =
		{
				0x01, 0x02, 0x04, 0x08,
				0x10, 0x20, 0x40, 0x80,
				0x1b, 0x36, 0x6c, 0xd8,
				0xa8, 0x4d
		};


	
	public int[] genWKey(AES_KEY_LEN len, byte[] key) throws AESException
	{
		assert(null != key);
		assert(key.length == (len.len() * 32));


		origin_key_len_word = len.len();

		// 1、定义轮数
		switch (len)
		{
			case AES_128:
				round = 10;
				break;

			case AES_192:
				round = 12;
				break;

			case AES_256:
				round = 14;
				break;
		}

		// 2、申请 wkey 内存
		wkey_len_word = 4 * (round + 1);

		this.wkey = new int[wkey_len_word];

		// 3、初始化前 origin_key_len_word 个 wkey
		initWkey(key);

		// 4、构建剩余的 wkey
		genTheOtherWKey();
		
		return wkey;
	}
	

	// 初始化前 origin_key_len_word 个 wkey
	private int initWkey(byte[] key)
	{
		assert(null != key);
		assert(null != wkey);
		
		int i, j;		 
		
		for (i = 0; i < origin_key_len_word; ++i)
		{
			wkey[i] = IBT.byteArray2Int(key[4 * i], key[4 * i + 1], key[4 * i + 2], key[4 * i + 3]);
		}				
		
		return 0;
	}
	
	// 构建剩余的 wkey
	private int genTheOtherWKey()
	{
		int i = 0;
		int tmp;
		
		for (i = origin_key_len_word; i < wkey_len_word; ++i)
		{
			tmp = wkey[i - 1];
			
			if (0 == (i % origin_key_len_word))
			{
				// 1. 循环左移
				tmp = Shift.rotateLeftShift(tmp, 8);
				
				// 2. S 盒转换
				tmp = SBox.substitute(tmp);
				
				// 3. XOR
				tmp ^= this.rc[i - 1];
			}
			else if ((6 < origin_key_len_word) && (4 == i % origin_key_len_word))
			{
				tmp = SBox.substitute(tmp);
			}
			else
			{
				; // do nothing
			}

			wkey[i] = wkey[i - origin_key_len_word] ^ tmp;
		}
		
		return 0;
	}



	

}
