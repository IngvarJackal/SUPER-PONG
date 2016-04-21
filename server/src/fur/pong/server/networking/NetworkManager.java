package fur.pong.server.networking;

import fur.pong.common.msg.Input;
import fur.pong.common.msg.State;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkManager implements Closeable {
    private final Networking networkingService;

    private final HashMap<IpPort, ConcurrentLinkedQueue<Input>> clientQueues = new HashMap<>();
    private final Thread inThread;

    public NetworkManager(String port) throws SocketException, UnknownHostException, ParseException {
        this.networkingService = new Networking(Integer.parseInt(port));

        inThread = new Thread(() -> {
            while (true) {
                if (Thread.interrupted())
                    return;
                WrappedMsg<Input> msg = networkingService.recieveObject();
                if (clientQueues.size() < 2) // only first 2 clients allowed
                    clientQueues.computeIfAbsent(msg.ipPort, (dontcare) -> new ConcurrentLinkedQueue<>());
                if (clientQueues.get(msg.ipPort) != null)
                    clientQueues.get(msg.ipPort).add(msg.payload);
            }
        });
    }

    public NetworkManager start() {
        inThread.start();
        return this;
    }

    public IpPort[] getPlayers() {
        return clientQueues.keySet().toArray(new IpPort[clientQueues.size()]);
    }

    public List<Input> getInputs(IpPort ipPort) {
        ConcurrentLinkedQueue<Input> inQueue = clientQueues.get(ipPort);
        List<Input> result = new ArrayList<>();
        if (inQueue != null) {
            while (!inQueue.isEmpty())
                result.add(inQueue.poll());
        }
        return result;
    }

    public void sendState(State state, IpPort ipPort) {
        try {
            networkingService.sendObject(state, ipPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        inThread.interrupt();
        networkingService.close();
    }
}