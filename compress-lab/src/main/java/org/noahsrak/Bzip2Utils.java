package org.noahsrak;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *  依赖 commons-compress 包
 * @author: zhangxt
 * @version:
 * @date: 2021/11/2
 */
public class Bzip2Utils {

    public static byte[] compress(byte srcBytes[]) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BZip2CompressorOutputStream bcos = new BZip2CompressorOutputStream(out);
        bcos.write(srcBytes);
        bcos.close();
        return out.toByteArray();
    }

    public static byte[] uncompress(byte[] bytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            BZip2CompressorInputStream ungzip = new BZip2CompressorInputStream(
                    in);
            byte[] buffer = new byte[2048];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

}
