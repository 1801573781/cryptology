package aes;

import cryptologyMath.IBT;

import java.io.*;

public class BMPTest
{
    private int width;
    private int height;
    private int fileSize;
    private int bmpOffset;
    private int bmpSize;

    // 文件名
    private String fileName = null;

    private BufferedInputStream bis = null;

    // 文件头
    private int headerSize = 0x36;
    private byte[] header = null;

    // 图像数据
    private byte[] bmpData = null;

    // 图像加密数据
    private byte[] bmpCipherData = null;

    // 密钥
    private byte[] key = null;


    public BMPTest()
    {}

    /*
        文件头格式
        0000-0001：文件标识，为字母ASCII码“BM”，42 4D。
        0002-0005：整个文件大小，单位字节。
        0006-0009：这个位置的数字是被微软保留的。
        000A-000D：记录图像数据区的起始位置。从文件开始到位图数据(bitmap data)之间的偏移量
        000E-0011：图像描述信息块的大小
        0012-0015：图像宽度。以像素为单位。
        0016-0019：图像高度。以像素为单位。
        001A-001B：图像的plane总数（恒为1）。
        001C- 001D：记录像素,也就是颜色：
            1 - Monochrome bitmap，
            4 - 16 color bitmap，
            8 - 256 color bitmap，
            F - 16位位图，
            18 - 24bit (true color) bitmap，
            20 - 32位位图。
        001E-0021：数据压缩方式（数值位0：不压缩；1：8位压缩；2：4位压缩；3：Bitfields压缩）。
        0022-0025：图像区数据的大小。单位字节，该数必须是4的倍数。
        0026-0029：水平每米有多少像素，在设备无关位图（.DIB）中，每字节以00H填写。
        002A-002D：垂直每米有多少像素，在设备无关位图（.DIB）中，每字节以00H填写。
        002E-0031：此图像所用的颜色数。
        0032-0035：指定重要的颜色数。当该域的值等于颜色数时（或者等于0时），表示所有颜色都一样重要。
    * */
    public void test(String bmpFile) throws IOException
    {
        // 1. 初始化 bmp 相关信息
        initBmpPara(bmpFile);

        // 2. AES 加密
        encrypt();

        // 3. 将加密后的数据，写到新的 bmp 文件
        writeCipher2NewBmpFile();

    }


    // 初始化 bmp 相关信息
    private void initBmpPara(String bmpFile) throws IOException
    {
        FileInputStream fis = new FileInputStream(bmpFile);
        bis = new BufferedInputStream(fis);

        fileName = bmpFile;

        // 读取 文件头
        header = new byte[headerSize];
        bis.read(header);

        fileName = fileName;

        fileSize = IBT.byteArray2Int(header[5], header[4],header[3],header[2]);

        bmpOffset = IBT.byteArray2Int(header[0x0d], header[0x0c],header[0x0b],header[0x0a]);

        // 读取 bmp 数据
        bmpSize = fileSize - bmpOffset;

        bmpData = new byte[bmpSize];
        bis.read(bmpData);

        bis.close();
    }

    // AES 加密
    private void encrypt()
    {
        AES aes = new AES();

        key = new byte[]
            {
                0x01, 0x02, 0x03, 0x04,
                0x05, 0x06, 0x07, 0x08,
                0x09, 0x0a, 0x0b, 0x0c,
                0x0d, 0x0e, 0x0f, 0x10
            };

        bmpCipherData = aes.encrypt(key, bmpData);
    }


    // 将加密后的数据，写到新的 bmp 文件
    private void writeCipher2NewBmpFile() throws java.io.FileNotFoundException, java.io.IOException
    {
        String s = fileName.substring(0, (fileName.length() - 4));
        s += "_test.bmp";

        FileOutputStream fos = new FileOutputStream(s);

        fos.write(header);

        fos.write(bmpCipherData, 0, bmpSize);

        fos.close();
    }


}
