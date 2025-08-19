package net.jacobb.resolverpc;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class WindowNameFunc {

  public static AtomicReference<String> WindowName() throws InterruptedException, IOException {

    AtomicReference<String> current = new AtomicReference<>("");

    Process process = new ProcessBuilder("tasklist", "/v", "/fo", "csv").start();
    new Thread(() -> {
      try (Scanner sc = new Scanner(process.getInputStream())) {
        if (sc.hasNextLine()) sc.nextLine();
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
          String[] parts = line.split(",");
          String unq = parts[8].substring(1).replaceFirst(".$", "");
          unq = unq.replace("N/A", "");
          if (unq.contains("Project Manager")) {
            current.set("Inside: Project Manager");
          }
          else if (unq.contains("DaVinci Resolve - ") || unq.contains("DaVinci Resolve Studio - ")) {
            unq = unq.replace("DaVinci Resolve - ", "")
                .replace("DaVinci Resolve Studio - ", "");
            current.set("Editing: " + unq);
          }
        }
      }
    }).start();
    process.waitFor();
    return current;
  }
}
