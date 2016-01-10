package dev;

import java.util.ArrayList;
import java.util.List;

import com.illposed.osc.OSCMessage;

/**
 * 
 * {@link PositionMessageGenerator}
 *
 * @author ujhrkzy
 *
 */
public class PositionMessageGenerator implements MessageGenerator {

    @Override
    public OSCMessage generate(String str) {
        List<Object> list = new ArrayList<>();
        OSCMessage msg = null;
        if (str.contains("reset")) {
            list.add(str);
            msg = new OSCMessage("/position/reset", list);
        } else {
            float[] arr = getValue(str);
            list.add(arr[0]);
            list.add(arr[1]);
            msg = new OSCMessage("/position/add", list);
        }
        return msg;
    }

    private float[] getValue(String data) {
        float[] arr = new float[3];
        if (!data.startsWith("x")) {
            arr[0] = 0;
            arr[1] = 0;
            arr[3] = 0;
            return arr;
        }
        int indexY = data.indexOf(",y");
        int indexZ = data.indexOf(",z");
        arr[0] = Float.parseFloat(data.substring(2, indexY));
        arr[1] = Float.parseFloat(data.substring(indexY + 3, indexZ));
        return arr;
    }
}