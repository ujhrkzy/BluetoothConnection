package tem;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class OscSenderSample {

    public static void main(String[] args) {
        System.out.println("start");
        OSCPortOut oscPort = null;
        try {
            oscPort = new OSCPortOut(InetAddress.getByName("localhost"), 12345);
            // if the oscPort variable fails to be instantiated then sent the
            // error message
        } catch (Exception e) {
            System.err.println("Couldn't set address");
        }
        List<Object> list = new ArrayList<>();
        list.add("x:1.0000,y:0.9,z:0.0");
        OSCMessage msg = new OSCMessage("/sayhello", list);
        try {
            oscPort.send(msg);
        } catch (Exception e) {
            System.err.println("Couldn't send");
        }
        System.out.println("end");
    }
}
