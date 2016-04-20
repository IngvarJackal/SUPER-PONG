package fur.pong.server.networking;

import fur.pong.common.msg.State;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Networking implements Closeable {
    private final DatagramSocket socket;

    public Networking(int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
    }

    public void sendObject(State input, IpPort ipPort) throws IOException {
        byte[] data = SerializationUtils.serialize(input);
        socket.send(new DatagramPacket(data, data.length, ipPort.address, ipPort.port));
    }

    @SuppressWarnings("unchecked")
    public <T> WrappedMsg<T> recieveObject() {
        byte[] recieve = new byte[50000];
        Object recObj = null;
        DatagramPacket recievedObject = new DatagramPacket(recieve, recieve.length);
        try {
            socket.receive(recievedObject);
            recObj = SerializationUtils.deserialize(recievedObject.getData());
        } finally {
            return new WrappedMsg<>((T)recObj, recievedObject.getAddress(), recievedObject.getPort());
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
