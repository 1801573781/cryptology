package hash;

import java.nio.ByteBuffer;

public class CSHA256 
{
	// 四个种子变量
	private final int A = 0x6a09e667;
	private final int B = 0xbb67ae85;
	private final int C = 0x3c6ef372;
	private final int D = 0xa54ff53a;
	private final int E = 0x510e527f;
	private final int F = 0x9b05688c;
	private final int G = 0x1f83d9ab;
	private final int H = 0x5be0cd19;		
	    
	
	// ABCDEFGH 的临时变量    
	private int Atemp, Btemp, Ctemp, Dtemp, Etemp, Ftemp, Gtemp, Htemp;
	
	// 数据块大小，单位：字节
	private int m_iBlockSize = 64;
	
	// 第2段需要补齐的字节数：8字节(64 bits)
	private int m_iPaddingSize2 = 8;
	    
	private final int K[] =	
		{
				0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5, 
				0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
				0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3, 
				0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,				   
				0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 
				0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,				   
				0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 
				0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
				0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 
				0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
				0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 
				0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,				
				0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 
				0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
				0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 
				0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2	    	
		};
	
	
	
	// 初始化8个临时变量
    private void init()
    {
    	Atemp = A;
        Btemp = B;
        Ctemp = C;
        Dtemp = D;
        Etemp = E;
        Ftemp = F;
        Gtemp = G;
        Htemp = H;
        
        m_iBlockSize = 64;
    	m_iPaddingSize2 = 8;
    }
    
    private void mainLoop(int M[])
    {
        int T1, T2;
        
        int a = Atemp;
        int b = Btemp;
        int c = Ctemp;
        int d = Dtemp;
        int e = Etemp;
        int f = Ftemp;
        int g = Gtemp;
        int h = Htemp;
        
        int W[] = new int[64]; 
        
        int i = 0;
        
        for(i = 0; i < 64; ++i)
        {
            if (i < 16)
            {
                W[i] = M[i];
            }
            else
            {
            	W[i] = G2(W[i-2]) + W[i-7] + G1(W[i-15]) + W[i-16];
            }
            
            T1 = h + F3(e) + F4(e, f, g) + K[i] + W[i];
            T2 = F1(a) + F2(a, b, c);
            
            h = g;
            g = f;
            f = e;
            e = d + T1;
            d = c;
            c = b;
            b = a;
            a = T1 + T2;            
        }
        
        Atemp = a + Atemp;
        Btemp = b + Btemp;
        Ctemp = c + Ctemp;
        Dtemp = d + Dtemp;
        Etemp = e + Etemp;
        Ftemp = f + Ftemp;
        Gtemp = g + Gtemp;
        Htemp = h + Htemp;
     
    }   
    
    
    
    // 循环右移
    private int rotateRight(int x, int s)
    { 	
    	return Integer.rotateRight(x, s);
    }
    
    // 右移
    private int rightShift(int x, int s)
    {    	
        return (x >>> s);
    }
    
    // 算子 F1
    private int F1(int x)
    {
    	return (rotateRight(x, 2) ^ rotateRight(x, 13) ^ rotateRight(x, 22));
    }    
 
    // 算子 F2
    private int F2(int x, int y, int z)
    {
    	return ((x & y) ^ (x & z) ^ (y & z));
    	//return ((x & y) | (x & z) | (y & z));
    }
    
    // 算子 F3
    private int F3(int x)
    {
    	return (rotateRight(x, 6) ^ rotateRight(x, 11) ^ rotateRight(x, 25));
    }    

    // 算子 F4
    private int F4(int x, int y, int z)
    {
    	return ((x & y) ^ ((~x) & z));
    	//return ((x & y) | ((~x) & z));
    }
    
    // 算子 G1
    private int G1(int x)
    {
    	return (rotateRight(x, 7) ^ rotateRight(x, 18) ^ rightShift(x, 3));
    }    
    
 
    // 算子 G2
    private int G2(int x)
    {
    	return (rotateRight(x, 17) ^ rotateRight(x, 19) ^ rightShift(x, 10));
    }
	
    
    /*
     *填充函数
     *处理后应满足bits≡448(mod512),字节就是bytes≡56（mode64)
     *填充方式为先加一个1,其它位补零
     *最后加上64位的原来长度     
     */

    private int[] padding(String str)
    {
    	//以512位，64个字节为一组
    	int num = ((str.length() + 8) / 64) + 1;
    	
    	//64/4=16，所以有16个整数
        int strByte[] = new int[num * 16];
              
        int i;
        
        // 全部初始化0
        for(i = 0; i < num * 16; ++i)
        {
            strByte[i] = 0;
        }        
        
        for(i = 0; i < str.length(); ++i)
        {
        	// 一个整数存储四个字节，小端序
            strByte[i >> 2] |= str.charAt(i) << ((i % 4) * 8);
        }         
      
        // 尾部添加1
        strByte[i >> 2] |= 0x80 << ((i % 4) * 8);
        
        
        // 添加原长度，长度指位的长度，所以要乘8，然后是小端序，所以放在倒数第二个,这里长度只用了32位        
        strByte[num * 16 - 2] = str.length() * 8;
            
        return strByte;
    }
    
    
     private int[] padding2(String str)
     {
    	 // 计算 str 的长度 iLen（单位是 字节）
    	 int iLen = str.length();
    	 
    	 // 计算 iLen mod 64 等于多少
    	 int iMod64 = iLen % m_iBlockSize;
    	 
    	 // 计算需要补齐的第一段字节数（56 - iMod64）
    	 int iPaddingLen1 = (m_iBlockSize - m_iPaddingSize2) - iMod64;
    	 
    	 // 如果原来的长度恰好 iLen ≡ 56（mode64)，那也得再补上64字节。
    	 if (0 == iPaddingLen1)
    	 {
    		 iPaddingLen1 = 64;
    	 }
    	 
    	 // 计算所有补齐后的数据的长度（单位：4字节/32 bits，因为是以 int 开头的）
    	 int iNum = (iLen + iPaddingLen1 + m_iPaddingSize2) / 4;
    	 
    	 // 构建所有补齐后的数据：申请内存
    	 int iPaddingData[] = new int[iNum];  	     	     	 
    	
    	 // 构建所有补齐后的数据：赋值——前 iLen 字节等于 str
    	 System.arraycopy(str.getBytes(), 0, iPaddingData, 0, iLen);
    	 
    	 // 构建所有补齐后的数据：赋值——第1段补齐的数据：第1个字节是：0x80，其余全是0；
    	 char chPadding1[] = new char[iPaddingLen1];
    	 
    	 int i;    	 
    	 
    	 chPadding1[0] = 0x80;
    	 
    	 for (i = 1; i < iPaddingLen1; ++i)
    	 {
    		 
    		 chPadding1[i] = 0;
    	 }
    	 
    	 
    	 System.arraycopy(chPadding1, 0, iPaddingData, (iLen + 1), iPaddingLen1);
    	 
    	 // 构建所有补齐后的数据：赋值——第2段补齐的数据：倒数第2个 int，其值等于 str 的长度，单位是 bit；
    	 iPaddingData[iNum - 2] = iLen * 8;    	 
    	
    	 // 构建所有补齐后的数据：赋值——第2段补齐的数据：最后1个 int，其值等于 0；
    	 iPaddingData[iNum - 1] = 0;
           
         return iPaddingData;
     }
     
     
     /**
      * Pads the given message to have a length
      * that is a multiple of 512 bits (64 bytes), including the addition of a
      * 1-bit, k 0-bits, and the message length as a 64-bit integer.
      *
      * @param message The message to pad.
      * @return A new array with the padded message bytes.
      */
     private byte[] pad(byte[] message)
     {
         final int blockBits = 512;
         final int blockBytes = blockBits / 8;

         // new message length: original + 1-bit and padding + 8-byte length
         int newMessageLength = message.length + 1 + 8;
         int padBytes = blockBytes - (newMessageLength % blockBytes);
         newMessageLength += padBytes;

         // copy message to extended array
         final byte[] paddedMessage = new byte[newMessageLength];
         System.arraycopy(message, 0, paddedMessage, 0, message.length);

         // write 1-bit
         paddedMessage[message.length] = (byte) 0b10000000;

         // skip padBytes many bytes (they are already 0)

         // write 8-byte integer describing the original message length
         int lenPos = message.length + 1 + padBytes;
         ByteBuffer.wrap(paddedMessage, lenPos, 8).putLong(message.length * 8);

         return paddedMessage;
     }
     
     
     /**
      * Converts the given byte array into an int array via big-endian conversion
      * (4 bytes become 1 int).
      *
      * @param bytes The source array.
      * @return The converted array.
      */
     private int[] toIntArray(byte[] bytes)
     {
         if ((bytes.length % Integer.BYTES) != 0)
         {
             throw new IllegalArgumentException("byte array length");
         }

         ByteBuffer buf = ByteBuffer.wrap(bytes);

         int[] result = new int[bytes.length / Integer.BYTES];
         
         for (int i = 0; i < result.length; ++i)
         {
             result[i] = buf.getInt();
         }

         return result;
     }
     
     
     
     /*      
      *整数变成16进制字符串
     */     
     private String changeHex(int a)
     {
    	 String str="";
          
         for(int i = 0; i < 4; i++)
         {
             str += String.format("%2s", Integer.toHexString((((a >> i) * 8) % (1 << 8)) & 0xff)).replace(' ', '0'); 
         }
          
         return str;
     }
     
     
     public String getSHA256(String source)
     {
         init();
         
         byte [] byteStr = source.getBytes();
         byte [] byteStrPadding = this.pad(byteStr);
         int [] intStr = this.toIntArray(byteStrPadding);
         
         //int intStr[] = this.padding(source); 
         
         
         int i = 0;
         int j = 0;
         		
         //for(i = 0; i < strByte.length/16; ++i)
         for(i = 0; i < intStr.length/16; ++i)
         {        
         	int num[] = new int[16];        	        
         	
         	for (j = 0; j < 16; ++j)
         	{            
         		num[j] = intStr[i * 16 + j];        
         	}        	
         
         	mainLoop(num);
         }
         
         return changeHex(Atemp) + changeHex(Btemp) + changeHex(Ctemp) + changeHex(Dtemp) +
        		changeHex(Dtemp) + changeHex(Etemp) + changeHex(Ftemp) + changeHex(Gtemp);     
     }	
     
     
}
