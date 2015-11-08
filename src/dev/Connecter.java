package dev;

import javax.microedition.io.StreamConnection;

/**
 * 
 * @author ujhrkzy
 *
 */
@FunctionalInterface
public interface Connecter {

    void connect(StreamConnection connection);
}
