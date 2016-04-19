package fur.pong.networking;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.net.*;

public class Networking implements Closeable {
    private final DatagramSocket socket = new DatagramSocket();
    private final InetAddress ipAddress;
    private final int port;

    public Networking(String ip, int port) throws SocketException, UnknownHostException {
        ipAddress = Inet6Address.getByName(ip);
        this.port = port;
    }

    public void sendObject(Serializable obj) throws IOException {
        byte[] data = SerializationUtils.serialize(obj);
        socket.send(new DatagramPacket(data, data.length, ipAddress, port));
    }

    public <T> T recieveObject() {
        byte[] recieve = new byte[50000];
        Object recObj = null;
        DatagramPacket recievedObject = new DatagramPacket(recieve, recieve.length);
        try {
            socket.receive(recievedObject);
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
