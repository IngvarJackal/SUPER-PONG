package fur.pong.networking;

import fur.pong.common.msg.Input;
import fur.pong.common.msg.State;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NetworkManager implements Closeable {
    private final Networking networkingService;

    private Queue<State> inQueue = new ConcurrentLinkedQueue<>();
    private final Thread inThread;

    public NetworkManager(String ip, String port) throws SocketException, UnknownHostException, ParseException {
        this.networkingService = new Networking(ip, Integer.parseInt(port));

        inThread = new Thread(() -> {
            while (true) {
                if (Thread.interrupted())
                    return;
                inQueue.add(networkingService.recieveObject());
            }
        });
    }

    public NetworkManager start() {
        inThread.start();
        return this;
    }

    public List<State> getStates() {
        List<State> result = new ArrayList<>(inQueue.size());
        while (!inQueue.isEmpty())
            result.add(inQueue.poll());
        return result;
    }

    public void sendInput(Input input) {
        try {
            networkingService.sendObject(input);
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