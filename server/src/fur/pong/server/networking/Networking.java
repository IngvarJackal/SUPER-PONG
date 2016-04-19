package fur.pong.server.networking;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class Networking implements Closeable {
    private final DatagramSocket socket;
    private InetAddress returnAddress;
    private int returnPort;

    public Networking(int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
    }

    public void sendObject(Serializable obj) throws IOException {
        byte[] data = SerializationUtils.serialize(obj);
        socket.send(new DatagramPacket(data, data.length, returnAddress, returnPort));
    }

    public <T> T recieveObject() {
        byte[] recieve = new byte[50000];
        Object recObj = null;
        DatagramPacket recievedObject = new DatagramPacket(recieve, recieve.length);
        try {
            socket.receive(recievedObject);
            returnAddress = recievedObject.getAddress();
            returnPort = recievedObject.getPort();
            recObj = SerializationUtils.deserialize(recievedObject.getData());
        } finally {
            return (T)recObj;
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
