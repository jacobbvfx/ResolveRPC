package net.jacobb.resolverpc;

import com.jagrosh.discordipc.*;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.io.IOException;
import java.time.OffsetDateTime;

import static net.jacobb.resolverpc.WindowNameFunc.WindowName;

public class DiscordRpc {

  public static IPCClient client = new IPCClient(1004088618857549844L);
  public static RichPresence.Builder builder = new RichPresence.Builder();
  public static void discordIntegration() {
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
      discordCheck();
    } catch (NoDiscordClientException | IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static void discordCheck() throws NoDiscordClientException, IOException, InterruptedException {
    if (!ProcessListFunc.discordProcessList()) {
      System.out.println("[Error] Couldn't find Discord app. Looking for it...");
      return;
    }
    if (client.getStatus() == PipeStatus.UNINITIALIZED || client.getStatus() == PipeStatus.DISCONNECTED) {
      System.out.println("[Log] Found Discord applications.");
      System.out.println("[Log] Connecting to Discord servers.");
      client.connect();
      System.out.println("[Log] Connection established.");
    } else if (client.getStatus() == PipeStatus.CONNECTED) {
      System.out.println("[Log] The connection is stable.");
    } else {
      System.out.println(client.getStatus());
      System.out.println("[Error] All in all I don't know what's going on rally so I close the program and elo, Ride with the ESSA whores");
      System.exit(0);
    }
  }
}
