package net.jacobb.resolverpc;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProcessListFunc {

    public static boolean ProcessList() throws IOException, InterruptedException {

        AtomicBoolean found = new AtomicBoolean(false);

        Process process = new ProcessBuilder("tasklist.exe", "/fo", "csv", "/nh").start();
        new Thread(() -> {
            Scanner sc = new Scanner(process.getInputStream());
            if (sc.hasNextLine()) sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                String unq = parts[0].substring(1).replaceFirst(".$", "");
//                System.out.println(unq);

                if (unq.equals("Resolve.exe")) {
//                    System.out.println("Found Resolve.exe");
                    found.set(true);
                }
            }
        }).start();
        process.waitFor();

        if (found.get() == false) {
//            System.out.println("Didn't found Resolve.exe");
        }

        return found.get();
    }
}
