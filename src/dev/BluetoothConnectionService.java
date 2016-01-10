package dev;

import java.io.IOException;
import java.util.List;

import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 * 
 * {@link BluetoothConnectionService}
 *
 * @author ujhrkzy
 *
 */
public class BluetoothConnectionService {
    /**
     * UUIDは独自プロトコルのサービスの場合は固有に生成する。
     */
    // TBD ID修正
    static final String serverUUID = "11111111111111111111111111111123";

    private final StreamConnectionNotifier server;

    /**
     * 
     * @throws IOException
     */
    public BluetoothConnectionService() throws IOException {
        // RFCOMMベースのサーバの開始。
        // - btspp:は PRCOMM 用なのでベースプロトコルによって変わる。
        server = (StreamConnectionNotifier) Connector.open("btspp://localhost:"
                + serverUUID, Connector.READ_WRITE, true);
        // ローカルデバイスにサービスを登録。必須ではない。
        ServiceRecord record = LocalDevice.getLocalDevice().getRecord(server);
        LocalDevice.getLocalDevice().updateRecord(record);
    }

    /**
     * クライアントからの接続待ち。
     * 
     * @return 接続されたたセッションを返す。
     */
    public Session accept(List<Connecter> connecters) throws IOException {
        StreamConnection channel = server.acceptAndOpen();
        return new Session(channel, connecters);
    }

    public void dispose() {
        if (server != null)
            try {
                server.close();
            } catch (Exception e) {/* ignore */
            }
    }

    /**
     * セッション。 並列にセッションを晴れるかは試していない。 - 基本的に Socket と同じ。
     */
    static class Session implements Runnable {
        private final StreamConnection channel;
        private final List<Connecter> connecters;

        public Session(StreamConnection channel, List<Connecter> connecters)
                throws IOException {
            this.channel = channel;
            this.connecters = connecters;
        }

        public void run() {
            connecters.stream()
                    .forEach(connecter -> connecter.connect(channel));
            close();
        }

        private void close() {
            if (channel != null)
                try {
                    channel.close();
                } catch (IOException e) {
                }
        }
    }
}