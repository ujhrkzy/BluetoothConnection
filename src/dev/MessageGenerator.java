package dev;

import com.illposed.osc.OSCMessage;

/**
 * 
 * {@link MessageGenerator}
 *
 * @author ujhrkzy
 *
 */
@FunctionalInterface
public interface MessageGenerator {

    /**
     * {@link OSCMessage} を作成します。
     * 
     * @param str
     *            文字列
     * @return {@link OSCMessage}
     */
    OSCMessage generate(String str);
}
