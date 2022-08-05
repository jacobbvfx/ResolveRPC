package net.jacobb.resolverpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static net.jacobb.resolverpc.WindowNameFunc.WindowName;

public class DiscordRpc {
    public static IPCClient client = new IPCClient(1004088618857549844L);
    public static RichPresence.Builder builder = new RichPresence.Builder();
    public static void DiscordIntegration() {
        client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client) {
                try {
                    builder.setState(WindowName().toString())
                            .setStartTimestamp(OffsetDateTime.now())
                            .setLargeImage("resolve", "DaVinci Resolve");
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
                client.sendRichPresence(builder.build());
            }
        });
        try {
            client.connect();
        } catch (NoDiscordClientException e) {
            //Dodać exeption który wraca do początku programu po crashu
            throw new RuntimeException(e);
        }

    }
}
