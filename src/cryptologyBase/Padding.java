package cryptologyBase;

public class Padding
{
    // PKCS#7 补齐
    public static byte[] pkcs7Padding(byte[] b)
    {
        assert(null != b);

        int baseLen = 16; // b 要补位 16 字节的整数倍
        int originLen = 0;
        int remainderLen = 0;
        int paddingLen = 0;
        int totalLen = 0;

        originLen = b.length;
        remainderLen = originLen % baseLen;
        paddingLen = baseLen - remainderLen;
        totalLen = originLen + paddingLen;

        byte[] padding = new byte[totalLen];

        System.arraycopy(b, 0, padding, 0, originLen);

        int i;

        for (i = originLen; i < totalLen; ++i)
        {
            padding[i] = (byte) paddingLen;
        }

        return padding;
    }
}
