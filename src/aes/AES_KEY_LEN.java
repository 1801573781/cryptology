package aes;

public enum AES_KEY_LEN 
{
	// 长度单位是 word
	AES_128(4), // 128 bits
	AES_192(6), // 192 bits
	AES_256(8); // 256 bits
	
	private int key_len = 0;
	
	AES_KEY_LEN(int m)
	{
		key_len = m;
	}
	
	public int len()
	{
		return this.key_len;
	}
}
