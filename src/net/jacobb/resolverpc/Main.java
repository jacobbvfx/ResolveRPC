package net.jacobb.resolverpc;

import com.jagrosh.discordipc.exceptions.NoDiscordClientException;

import java.io.IOException;

import static net.jacobb.resolverpc.ProcessListFunc.ProcessList;

public class Main extends DiscordRpc {

    public static void main(String[] args) throws IOException, InterruptedException, NoDiscordClientException {

        do {
            Thread.sleep(5000);
            while(ProcessList() == true) {
                Thread.sleep(5000);
                if(!client.getStatus().toString().equals("CONNECTED")) {
                    DiscordIntegration();
                    System.out.println("Discord RPC Started");
                    Thread.sleep(5000);
                }

                if(ProcessList() == false){
                    client.close();
                    System.out.println("Discord RPC Stopped");
                    Thread.sleep(10000);

                }
            }
            Thread.sleep(5000);
        }
        while (true);

    }

}