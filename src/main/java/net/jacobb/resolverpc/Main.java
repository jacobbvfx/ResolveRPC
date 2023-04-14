package net.jacobb.resolverpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static net.jacobb.resolverpc.DiscordRpc.*;
import static net.jacobb.resolverpc.WindowNameFunc.WindowName;

public class Main {

  public static void main(String[] args) {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
          {
            DiscordRpc.discordIntegration();
          }
          if (client.getStatus() == PipeStatus.CONNECTED ) {
            try {
              if (ProcessListFunc.processList()) {
                builder.setState(WindowName().toString());
                client.sendRichPresence(builder.build());
              }
              else {
                System.out.println("[Error] Couldn't find Davinci application. Looking for it...");
              }
            } catch (IOException | InterruptedException e) {
              throw new RuntimeException(e);
            }
          }
      }
    };
    timer.schedule(timerTask, 0, 20*1000);
  }
}