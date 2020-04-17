package aes;

public enum AES_KEY_LEN 
{
	AES_128(128),
	AES_192(192),
	AES_256(256);
	
	private int key_len = 0;
	
	private AES_KEY_LEN(int l)
	{
		key_len = l;
	}
	
	public int len()
	{
		return this.key_len;
	}
}
