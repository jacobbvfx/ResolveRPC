package net.jacobb.resolverpc;

import com.jagrosh.discordipc.entities.pipe.PipeStatus;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import static net.jacobb.resolverpc.DiscordRpc.*;
import static net.jacobb.resolverpc.WindowNameFunc.WindowName;

public class Main {

  public static void main(String[] args) {
    if (!SystemTray.isSupported()) {
      System.err.println("System tray is not supported.");
      return;
    }

    SystemTray tray = SystemTray.getSystemTray();
    URL imageURL = Main.class.getClassLoader().getResource("icon.png");
    Image image;
    if (imageURL != null) {
        image = Toolkit.getDefaultToolkit().getImage(imageURL);
    } else {
        System.err.println("Resource not found: icon.png");
        image = Toolkit.getDefaultToolkit().createImage(new byte[0]);
    }


    PopupMenu popup = new PopupMenu();
    MenuItem exitItem = new MenuItem("Stop");
    exitItem.addActionListener(e -> {
        client.close();
        System.exit(0);
    });
    popup.add(exitItem);

    MenuItem uninstallItem = new MenuItem("Uninstall");
    uninstallItem.addActionListener(e -> {
        try {
            new ProcessBuilder("unins000.exe").start();
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    });
    popup.add(uninstallItem);

    TrayIcon trayIcon = new TrayIcon(image, "ResolveRPC", popup);
    trayIcon.setImageAutoSize(true);

    try {
        tray.add(trayIcon);
    } catch (AWTException e) {
        System.err.println("TrayIcon could not be added.");
        return;
    }

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