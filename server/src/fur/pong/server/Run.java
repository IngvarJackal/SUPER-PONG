package fur.pong.server;

import fur.pong.common.msg.Input;
import fur.pong.server.networking.IpPort;
import fur.pong.server.networking.NetworkManager;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

public class Run {
    public static void main (String[] arg) throws ParseException, SocketException, UnknownHostException, InterruptedException {
        NetworkManager manager = new NetworkManager("12345").start();
        PhysEngine physEngine = new PhysEngine();

        long i = 0;
        while (true) {
            i++;
            System.out.println(i);
            IpPort[] players = manager.getPlayers();
            if (players.length < 1) // TODO: should be 2 after testing
                Thread.sleep(100);
            else {
                List<Input> in = manager.getInputs(players[0]);
                physEngine.setInputs(in, true);
                if (i % 1000 == 0) {
                    manager.sendState(physEngine.compute(physEngine.getStartTime()), players[0]);
                }
                System.out.println(physEngine.toStringState());
                Thread.sleep(5);
            }
        }
    }
}
