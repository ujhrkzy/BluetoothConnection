package dev;

import javax.microedition.io.StreamConnection;

/**
 * 
 * {@link Connecter}
 *
 * @author ujhrkzy
 *
 */
@FunctionalInterface
public interface Connecter {

    void connect(StreamConnection connection);
}
