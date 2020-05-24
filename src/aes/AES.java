package aes;

import cryptologyBase.*;
import cryptologyMath.ByteBooleanOper;
import cryptologyMath.Galois2exp8;
import cryptologyMath.IBT;
import cryptologyMath.Shift;


public class AES
{
	// 原始密钥长度
	private AES_KEY_LEN key_len = AES_KEY_LEN.AES_256;		
	
	// 加密轮数
	private int round = 0;
	
	// 数据块大小，4 word，16 bytes，128 bits
	private final int blockSize = 4;
	
	// 工作密钥（扩展密钥）
	private int[] wKey = null;
	
	
	
	public AES()
	{		

	}


	// 加密
	public byte[] encrypt(byte[] key, byte[] plainText)
	{
		assert(null != key);
		assert(null != plainText);

		// 1. 初始化 密钥长度（单位是 word）、加密轮数
		init(key);

		// 2. 扩展密钥（生成加密密钥）
		try
		{
			generateWorkKey(key_len, key);
		}
		catch(AESException e)
		{
			return null;
		}

		// 3. 明文补齐
		byte[] plainTextPadding = Padding.pkcs7Padding(plainText);

		// 4. 加密

		// 4.1 计算一共有多少块（block）
		int count = plainTextPadding.length / (blockSize * 4);

		// 4.2 针对每一块，for 循环处理
		int i;
		int r;

		for (i = 0; i < count; ++i)
		{
			byte[] tmp = new byte[this.blockSize * 4];

			System.arraycopy(plainTextPadding, (i * blockSize * 4), tmp, 0, blockSize * 4);

			// 4.2.1 首先来一次轮密钥加
			addRoundKey(0, tmp);

			// 4.2.2 然后来 round 轮 转换
			for (r = 1; r <= round; ++r)
			{
				convert(r, tmp);
			}

			// 4.3.3 再将以上操作后的数据，赋值回去
			System.arraycopy(tmp, 0, plainTextPadding, (i * blockSize * 4), blockSize * 4);
		}

		return plainTextPadding;
	}

	// 初始化 密钥长度（单位是 word）、加密轮数
	private int init(byte[] key)
	{
		assert(null != key);

		int len = key.length;

		if ((AES_KEY_LEN.AES_128.len() * 4) == len)
		{
			key_len = AES_KEY_LEN.AES_128;
			round = 10;
		}
		else if ((AES_KEY_LEN.AES_192.len() * 4) == len)
		{
			key_len = AES_KEY_LEN.AES_192;
			round = 12;
		}
		else if ((AES_KEY_LEN.AES_256.len() * 4) == len)
		{
			key_len = AES_KEY_LEN.AES_256;
			round = 14;
		}
		else
		{
			return -1;
		}

		return 0;

	}

	
	// 生成工作密钥（扩展密钥）
	private int generateWorkKey(AES_KEY_LEN len, byte[] key) throws AESException
	{
		assert(null == key);

		// key_len 的长度单位是 word（4个 byte）
		assert((key_len.len() * 4) == key.length);

		WKey wkey = new WKey();

		try
		{		
			wKey = wkey.genWKey(len, key);
		}
		catch(AESException e)
		{
			throw e;
		}

		return 0;
	}



	// 将 b 与 轮密钥相加，相加后的结果，还写入 b
	// b 的长度是16字节（128比特）
	// 轮密钥加，就是 xor
	private void addRoundKey(int r, byte[] b)
	{
		assert(0 <= r);
		assert(r < round);
		assert(null != b);

		assert((blockSize * 4) == b.length);

		int i;
		int j;

		for (i = 0; i < blockSize; ++i)
		{
			byte[] rKey = IBT.int2ByteArray(wKey[i]);

			for (j = 0; j < 4; ++j)
			{
				b[4 * i + j] = ByteBooleanOper.xor(b[4 * i + j], rKey[j]);
			}
		}
	}



	// 轮转换
	private void convert(int r, byte[] b)
	{
		assert(0 <= r);
		assert(r < round);
		assert(null != b);

		assert((blockSize * 4) == b.length);

		// 1. 字节替换(S-Box)
		this.substituteBytes(b);

		// 2. 行移位
		this.shiftRows(b);

		// 3. 列混淆
		this.mixColumns(b);

		// 4. 轮密钥加
		this.addRoundKey(r, b);
	}




	// 字节替换(S-Box)
	private void substituteBytes(byte[] b)
	{
		assert(null != b);

		int len = b.length;
		int i;

		for (i = 0; i < len; ++i)
		{
			b[i] = SBox.substitute(b[i]);
		}
	}


	// 行移位
	// s，形式上是一维数组，需要脑补为二维数组
	// 这个二维数组为：
	// b[0], b[4], b[8],  b[12]
	// b[1], b[5], b[9],  b[13]
	// b[2], b[6], b[10], b[14]
	// b[3], b[7], b[11], b[15]
	private void shiftRows(byte[] b)
	{
		assert(null != b);
		assert((blockSize * 4) == b.length);

		int row;
		int col;

		// 第0行，不移位
		for (row = 1; row < 4; ++row)
		{
			byte[] tmp = new byte[4];

			for (col = 0; col < 4; ++col)
			{
				tmp[col] = b[4 * col + row];
			}

			// 1. 先循环左移
			tmp = Shift.rotateLeftShift(tmp, row);

			// 2. 再将循环左移的结果赋值回去
			for (col = 0; col < 4; ++col)
			{
				b[4 * col + row] = tmp[col];
			}
		}
	}

	// 列混淆
	private void mixColumns(byte[] b)
	{
		// 列混淆矩阵
		byte[][] mixMatrix =
			{
				{ 0x02, 0x03, 0x01, 0x01 },
				{ 0x01, 0x02, 0x03, 0x01 },
				{ 0x01, 0x01, 0x02, 0x03 },
				{ 0x03, 0x01, 0x01, 0x02 }
			};

		byte[][] s = change2Matrix(b, 4, 4);

		byte[][] mix = Galois2exp8.mul(mixMatrix, s);

		// 再将混淆后的数据，赋值到 b
		int r, c;
		int row = 4;
		int col = 4;


		for (r = 0; r < row; ++r)
		{
			for (c = 0; c < col; ++c)
			{
				b[c * col + r] = mix[r][c];
			}
		}

	}

	// 将一维数组转为二维数组
	private byte[][] change2Matrix(byte[] b, int row, int col)
	{
		assert(null != b);

		assert((row * col) == b.length);

		int r, c;
		byte[][] s = new byte[row][col];

		for (r = 0; r < row; ++r)
		{
			for (c = 0; c < col; ++c)
			{
				s[r][c] = b[c * col + r];
			}
		}

		return s;
	}









}
