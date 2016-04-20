package fur.pong.server.networking;

import java.net.InetAddress;

public class WrappedMsg<T> {
    public final T payload;
    public final IpPort ipPort;

    public WrappedMsg(T payload, InetAddress addres, int port) {
        this.payload = payload;
        this.ipPort = new IpPort(addres, port);
    }
}
