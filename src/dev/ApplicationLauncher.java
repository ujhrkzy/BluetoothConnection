package dev;

import java.util.ArrayList;
import java.util.List;

import dev.BluetoothConnectionService.Session;

/**
 * 
 * @author ujhrkzy
 *
 */
public class ApplicationLauncher {

    public static void main(String[] args) throws Exception {
        BluetoothConnectionService bluetoothConnectionService = new BluetoothConnectionService();
        OscConnectionService oscConnectionService = new OscConnectionService();
        List<Connecter> connecters = new ArrayList<>();
        connecters.add(oscConnectionService.createConnecter());
        while (true) {
            Session session = bluetoothConnectionService.accept(connecters);
            new Thread(session).start();
        }
    }
}
