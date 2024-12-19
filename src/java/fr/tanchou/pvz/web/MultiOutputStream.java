package fr.tanchou.pvz.web;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

import org.java_websocket.WebSocket;

public class MultiOutputStream extends OutputStream {
    private final OutputStream consoleStream;
    public static List<WebSocket> webSocket = new LinkedList<>();

    private boolean consoleOutput = true;
    private boolean webSocketOutput = true;

    public MultiOutputStream(OutputStream consoleStream) {
        this.consoleStream = consoleStream;
    }

    public void addWebSocket(WebSocket webSocket) {
        MultiOutputStream.webSocket.add(webSocket);
    }

    public void removeWebSocket(WebSocket webSocket) {
        MultiOutputStream.webSocket.remove(webSocket);
    }

    @Override
    public void write(int b) {
        try {
            if (consoleOutput)
                consoleStream.write(b);  // Écrire dans la console

            if (webSocketOutput)
                for (WebSocket ws : MultiOutputStream.webSocket) {
                    ws.send(String.valueOf((char) b));  // Envoyer dans le WebSocket
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        try {
            if (consoleOutput)
                consoleStream.write(b, off, len);  // Écrire dans la console

            if (webSocketOutput)
                for (WebSocket ws : MultiOutputStream.webSocket) {
                    ws.send(new String(b, off, len));  // Envoyer dans le WebSocket
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deactivateConsoleOutput() {
        this.consoleOutput = false;
    }

    public void deactivateWebSocketOutput() {
        this.webSocketOutput = false;
    }

    public void activateConsoleOutput() {
        this.consoleOutput = true;
    }

    public void activateWebSocketOutput() {
        this.webSocketOutput = true;
    }
}
