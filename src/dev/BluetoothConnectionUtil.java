package dev;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BluetoothConnectionUtil {

    private BluetoothConnectionUtil() {
    }

    public static byte[] readAll(InputStream inputStream) throws IOException {
        try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            // while (true) {
            int len = inputStream.read(buffer);
            if (len < 0) {
                // break;
                return buffer;
            }
            bout.write(buffer, 0, len);
            // }
            return bout.toByteArray();
        }
    }
}
