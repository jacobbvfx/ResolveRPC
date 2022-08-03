package net.jacobb.resolverpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.time.OffsetDateTime;

public class DiscordRpc {

    public static IPCClient client = new IPCClient(1004088618857549844L);
    public static void DiscordIntegration() {

        client.setListener(new IPCListener(){
            @Override
            public void onReady(IPCClient client)
            {
                RichPresence.Builder builder = new RichPresence.Builder();
                builder.setState("Editing...")
                        .setStartTimestamp(OffsetDateTime.now())
                        .setLargeImage("resolve", "DaVinci Resolve");
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
