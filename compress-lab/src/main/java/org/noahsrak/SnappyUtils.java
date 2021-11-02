package org.noahsrak;

import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * @author: zhangxt
 * @version:
 * @date: 2021/11/2
 */
public class SnappyUtils {

    public static byte[] compress(byte srcBytes[]) throws IOException {
        return  Snappy.compress(srcBytes);
    }

    public static byte[] uncompress(byte[] bytes) throws IOException {
        return Snappy.uncompress(bytes);
    }

}
