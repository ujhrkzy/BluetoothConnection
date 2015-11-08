package dev;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.io.StreamConnection;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class OscConnectionService {

    private final OSCPortOut oscPortOut;

    public OscConnectionService() {
        try {
            oscPortOut = new OSCPortOut(InetAddress.getByName("localhost"),
                    12345);
        } catch (SocketException | UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    public Connecter createConnecter() {
        return new OscConnecter(oscPortOut);
    }

    private static class OscConnecter implements Connecter {

        private final OSCPortOut oscPortOut;

        private OscConnecter(OSCPortOut oscPortOut) {
            this.oscPortOut = oscPortOut;
        }

        @Override
        public void connect(StreamConnection connection) {
            try {
                InputStream inputStream = connection.openInputStream();
                Charset charset = Charset.forName("UTF-8");
                while (true) {
                    byte[] inputData = readAll(inputStream);
                    String data = new String(inputData, charset);
                    List<Object> list = new ArrayList<>();
                    OSCMessage msg = null;
                    if (data.contains("reset")) {
                        list.add(data);
                        msg = new OSCMessage("/mouse/position", list);
                    } else {
                        float[] arr = getValue(data);
                        list.add((int) (arr[0] * 100));
                        list.add((int) (arr[1] * 100));
                        msg = new OSCMessage("/sayhello", list);
                    }
                    oscPortOut.send(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private float[] getValue(String data) {
            int index = data.indexOf(",y");
            int indexZ = data.indexOf(",z");
            float[] arr = new float[2];
            arr[0] = Float.parseFloat(data.substring(2, index));
            arr[1] = Float.parseFloat(data.substring(index + 3, indexZ));
            return arr;
        }

        private byte[] readAll(InputStream inputStream) throws IOException {
            try (ByteArrayOutputStream bout = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                if (len < 0) {
                    return buffer;
                }
                bout.write(buffer, 0, len);
                return bout.toByteArray();
            }
        }
    }
}
