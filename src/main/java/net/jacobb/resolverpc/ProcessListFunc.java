package net.jacobb.resolverpc;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessListFunc {

  public static boolean processList() throws IOException, InterruptedException {

    AtomicBoolean found = new AtomicBoolean(false);

    Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
    new Thread(() -> {
      try (Scanner sc = new Scanner(process.getInputStream())) {
        if (sc.hasNextLine()) sc.nextLine();
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          String[] parts = line.split(",");
          String unq = parts[0].substring(1).replaceFirst(".$", "");
          if (unq.equals("Resolve.exe")) {
            found.set(true);
          }
        }
      }
    }).start();
    process.waitFor();
    if (found.get() == false) {}
    return found.get();
  }

  public static boolean discordProcessList() throws IOException, InterruptedException {

    AtomicBoolean found = new AtomicBoolean(false);

    Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
    new Thread(() -> {
      try (Scanner sc = new Scanner(process.getInputStream())) {
        if (sc.hasNextLine()) sc.nextLine();
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          String[] parts = line.split(",");
          String unq = parts[0].substring(1).replaceFirst(".$", "");
          if (unq.equals("Discord.exe") || unq.equals("DiscordCanary.exe") || unq.equals("DiscordPTB.exe")) {
            found.set(true);
          }
        }
      }
    }).start();
    process.waitFor();
    if (found.get() == false) {}
    return found.get();
  }
}
