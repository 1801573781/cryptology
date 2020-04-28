package aes;

public enum AES_KEY_LEN 
{
	AES_128(128),
	AES_192(192),
	AES_256(256);
	
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
