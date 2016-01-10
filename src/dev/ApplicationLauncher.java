package dev;

import java.util.ArrayList;
import java.util.List;

import dev.BluetoothConnectionService.Session;

/**
 * 
 * {@link ApplicationLauncher}
 *
 * @author ujhrkzy
 *
 */
public class ApplicationLauncher {

    public static void main(String[] args) throws Exception {
        BluetoothConnectionService bluetoothConnectionService = new BluetoothConnectionService();
        MessageGenerator messageGenerator = new PositionMessageGenerator();
        OscConnectionService oscConnectionService = new OscConnectionService(
                messageGenerator);
        List<Connecter> connecters = new ArrayList<>();
        connecters.add(oscConnectionService);
        while (true) {
            Session session = bluetoothConnectionService.accept(connecters);
            new Thread(session).start();
        }
    }
}
