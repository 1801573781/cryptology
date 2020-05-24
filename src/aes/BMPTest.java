package aes;

import cryptologyMath.IBT;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class BMPTest
{
    private int width;
    private int height;
    private int fileSize;
    private int bmpOffset;
    private int bmpSize;

    // �ļ���
    private String fileName = null;

    private BufferedInputStream bis = null;

    // �ļ�ͷ
    private int headerSize = 0x36;
    private byte[] header = null;

    // ͼ������
    private byte[] bmpData = null;


    public BMPTest()
    {}

    /*
        �ļ�ͷ��ʽ
        0000-0001���ļ���ʶ��Ϊ��ĸASCII�롰BM����42 4D��
        0002-0005�������ļ���С����λ�ֽڡ�
        0006-0009�����λ�õ������Ǳ�΢�����ġ�
        000A-000D����¼ͼ������������ʼλ�á����ļ���ʼ��λͼ����(bitmap data)֮���ƫ����
        000E-0011��ͼ��������Ϣ��Ĵ�С
        0012-0015��ͼ���ȡ�������Ϊ��λ��
        0016-0019��ͼ��߶ȡ�������Ϊ��λ��
        001A-001B��ͼ���plane��������Ϊ1����
        001C- 001D����¼����,Ҳ������ɫ��
            1 - Monochrome bitmap��
            4 - 16 color bitmap��
            8 - 256 color bitmap��
            F - 16λλͼ��
            18 - 24bit (true color) bitmap��
            20 - 32λλͼ��
        001E-0021������ѹ����ʽ����ֵλ0����ѹ����1��8λѹ����2��4λѹ����3��Bitfieldsѹ������
        0022-0025��ͼ�������ݵĴ�С����λ�ֽڣ�����������4�ı�����
        0026-0029��ˮƽÿ���ж������أ����豸�޹�λͼ��.DIB���У�ÿ�ֽ���00H��д��
        002A-002D����ֱÿ���ж������أ����豸�޹�λͼ��.DIB���У�ÿ�ֽ���00H��д��
        002E-0031����ͼ�����õ���ɫ����
        0032-0035��ָ����Ҫ����ɫ�����������ֵ������ɫ��ʱ�����ߵ���0ʱ������ʾ������ɫ��һ����Ҫ��
    * */
    public void test(String bmpFile) throws IOException
    {
        FileInputStream fis = new FileInputStream(bmpFile);
        bis = new BufferedInputStream(fis);

        fileName = bmpFile;

        // ��ȡ �ļ�ͷ
        header = new byte[headerSize];
        bis.read(header);

        fileName = fileName;

        fileSize = IBT.byteArray2Int(header[5], header[4],header[3],header[2]);

        bmpOffset = IBT.byteArray2Int(header[0x0d], header[0x0c],header[0x0b],header[0x0a]);



    }


    private void readBmpDataSize()
    {





    }

}
