package fur.pong.networking;

import fur.pong.network.msg.Input;
import fur.pong.network.msg.State;

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

    private Queue<Input> outQueue = new ConcurrentLinkedQueue<>();
    private final Thread outThread;

    public NetworkManager(String ip, String port) throws SocketException, UnknownHostException, ParseException {
        this.networkingService = new Networking(port, Integer.parseInt(ip));

        inThread = new Thread(() -> {
            while (true) {
                if (Thread.interrupted())
                    return;
                inQueue.add(networkingService.recieveObject());
            }
        });

        outThread = new Thread(() -> {
            while (true) {
                try {
                    Input input = outQueue.poll();
                    if (input != null)
                        networkingService.sendObject(input);
                    outQueue.wait(100);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
    }

    public NetworkManager start() {
        inThread.start();
        outThread.start();
        return this;
    }

    public List<State> getStates() {
        List result = new ArrayList<>(inQueue.size());
        inQueue.forEach((state) -> result.add(state));
        return result;
    }

    public void sendInput(Input input) {
        outQueue.add(input);
        outQueue.notifyAll();
    }

    @Override
    public void close() throws IOException {
        inThread.interrupt();
        outThread.interrupt();
        networkingService.close();
    }
}
