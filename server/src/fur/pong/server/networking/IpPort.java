package fur.pong.server.networking;

import java.net.InetAddress;
import java.util.Objects;

public class IpPort {
    public final InetAddress address;
    public final int port;

    public IpPort(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IpPort ipPort = (IpPort) o;
        return port == ipPort.port &&
                Objects.equals(address, ipPort.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }
}
