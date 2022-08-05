package net.jacobb.resolverpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static net.jacobb.resolverpc.ProcessListFunc.ProcessList;
import static net.jacobb.resolverpc.WindowNameFunc.WindowName;

public class Main extends DiscordRpc {

    public static void main(String[] args) throws IOException, InterruptedException {

        do {
            Thread.sleep(5000);
            while(ProcessList()) {
                Thread.sleep(5000);
                if(!client.getStatus().toString().equals("CONNECTED")) {
                    DiscordIntegration();
                    System.out.println("Discord RPC Started");

                    runTask(client, builder);

                    Thread.sleep(5000);
                }

                if(!ProcessList()){
                    client.close();
                    System.out.println("Discord RPC Stopped");
                    Thread.sleep(10000);

                }
            }
            Thread.sleep(5000);
        }
        while (true);

    }

    private static void runTask(IPCClient ipcClient, RichPresence.Builder builder) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!ProcessList()) {
                        timer.cancel();
                        return;
                    }

                    builder.setState(WindowName().toString());

                    ipcClient.sendRichPresence(builder.build());
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.schedule(timerTask, 0, 10000);
    }

}