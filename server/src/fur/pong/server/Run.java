package fur.pong.server;

import fur.pong.common.msg.State;
import fur.pong.server.networking.NetworkManager;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;

public class Run {
    public static void main (String[] arg) throws ParseException, SocketException, UnknownHostException, InterruptedException {
        System.out.println("hello, world!");
        NetworkManager manager = new NetworkManager("12345").start();
        while (true) {
            System.out.println("sending response...");
            System.out.println(manager.getInputs());
            manager.sendState(new State(ms, plApos, plBpos, state, balls));
            Thread.sleep(1000);
        }
    }
}
