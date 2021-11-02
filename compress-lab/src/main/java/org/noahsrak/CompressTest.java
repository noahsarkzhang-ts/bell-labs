package org.noahsrak;

import org.noahsrak.rsa.RSAUtils;

import java.util.Base64;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/2
 */
public class CompressTest {

    public static void main(String[] args) throws Exception {
       /* FileInputStream fis = new FileInputStream(new File("player.dat"));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);*/
        String cipher = RSAUtils.privateEncrypt("123456789", RSAUtils.getPrivateKey(RSAUtils.PRIVATE_KEY));
        System.out.println("cipher = " + cipher);

        byte[] beforeBytes = cipher.getBytes();

        int times = 1;
        System.out.println("压缩前大小：" + beforeBytes.length + " bytes");
        long startTime1 = System.currentTimeMillis();
        byte[] afterBytes = null;

        for (int i = 0; i < times; i++) {
            afterBytes = GzipUtils.compress(beforeBytes);
        }
        System.out.println("Compress:" + Base64.getEncoder().encodeToString(afterBytes));
        long endTime1 = System.currentTimeMillis();
        System.out.println("压缩后大小：" + afterBytes.length + " bytes");
        System.out.println("压缩次数：" + times + "，时间：" + (endTime1 - startTime1)
                + "ms");

        byte[] resultBytes = null;
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            resultBytes = GzipUtils.uncompress(afterBytes);
        }

        System.out.println("resultBytes:" + Base64.getEncoder().encodeToString(resultBytes));
        System.out.println("解压缩后大小：" + resultBytes.length + " bytes");

        long endTime2 = System.currentTimeMillis();
        System.out.println("解压缩次数：" + times + "，时间：" + (endTime2 - startTime2)
                + "ms");
    }

}
