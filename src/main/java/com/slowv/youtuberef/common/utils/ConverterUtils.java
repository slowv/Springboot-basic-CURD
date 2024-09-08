package com.slowv.youtuberef.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterUtils {

    public static byte[] readBytesFromInputStream(InputStream inputStream, int length) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[length];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
