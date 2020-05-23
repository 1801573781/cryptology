package cryptologyBase;

public class Padding
{
    // PKCS#7 ����
    public static byte[] pkcs7Padding(byte[] b)
    {
        assert(null != b);

        int baseLen = 16; // b Ҫ��λ 16 �ֽڵ�������
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
