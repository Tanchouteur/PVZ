package fr.tanchou.pvz.web;

import java.io.OutputStream;
import org.java_websocket.WebSocket;

public class DualOutputStream extends OutputStream {
    private OutputStream consoleStream;
    private WebSocket webSocket;

    public DualOutputStream(OutputStream consoleStream, WebSocket webSocket) {
        this.consoleStream = consoleStream;
        this.webSocket = webSocket;
    }

    @Override
    public void write(int b) {
        try {
            consoleStream.write(b);  // Écrire dans la console
            webSocket.send(String.valueOf((char) b));  // Envoyer dans le WebSocket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        try {
            consoleStream.write(b, off, len);  // Écrire dans la console
            webSocket.send(new String(b, off, len));  // Envoyer dans le WebSocket
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
