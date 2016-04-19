package fur.pong.server.networking;

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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NetworkManager implements Closeable {
    private final Networking networkingService;

    private Queue<Input> inQueue = new ConcurrentLinkedQueue<>();
    private final Thread inThread;

    public NetworkManager(String port) throws SocketException, UnknownHostException, ParseException {
        this.networkingService = new Networking(Integer.parseInt(port));

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

    public List<Input> getInputs() {
        List<Input> result = new ArrayList<>(inQueue.size());
        inQueue.forEach(result::add);
        inQueue.clear();
        return result;
    }

    public void sendState(State state) {
        try {
            networkingService.sendObject(state);
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