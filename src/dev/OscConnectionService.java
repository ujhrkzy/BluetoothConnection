package dev;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.microedition.io.StreamConnection;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

/**
 * 
 * {@link OscConnectionService}
 *
 * @author ujhrkzy
 *
 */
public class OscConnectionService implements Connecter {

    private final MessageGenerator messageGenerator;
    private final OSCPortOut oscPortOut;

    public OscConnectionService(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
        try {
            oscPortOut = new OSCPortOut(InetAddress.getByName("localhost"),
                    12345);
        } catch (SocketException | UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void connect(StreamConnection connection) {
        try {
            InputStream inputStream = connection.openInputStream();
            Charset charset = Charset.forName("UTF-8");
            while (true) {
                byte[] inputData = readAll(inputStream);
                String data = new String(inputData, charset);
                OSCMessage oscMessage = messageGenerator.generate(data);
                if (oscMessage != null) {
                    oscPortOut.send(oscMessage);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
