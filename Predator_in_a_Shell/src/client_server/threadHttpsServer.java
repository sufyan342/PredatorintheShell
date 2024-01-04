package client_server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import javax.net.ssl.SSLServerSocket;

public class threadHttpsServer extends Thread {

    Socket server;
    private SSLServerSocket serverSocket;
    private String predator;
    private String prey;
    private HashMap<String, String> predatorPrey = new HashMap<>();
    private threadHttpsServer thread;
    private volatile boolean run = true;
    
    // handles server logic.... get predator from client, check against hashmap and send back to client depending on conditions
    public void run() { 
        while (run) {
            
            try {
                Socket client = serverSocket.accept(); // after SSL handshake, a regular socket is used for communication
                System.out.println("Client connected.");
                System.out.println(" Connected to Client's: Port: " + client.getPort() + "\n Address: " + client.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);

            predator = in.readLine(); // receive predator from Client

            if (predator.equals("Close")) {
                System.out.println("Closing....");
                thread.stopThread();
                client.close();
            }

            // check if predator received from client is in the hashmap.... send response to client
            if (predatorPrey.containsKey(predator)) {
                prey = predatorPrey.get(predator);
                System.out.println("Sending response: " + prey);
                out.println(prey);
            } else {
                System.out.println("Invalid predator or no response available.");
                
                
            }

            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

    public void stopThread() { // method to terminate thread safely
        run = false;
    }
             

    // constructor for threadHttpsServer
    public threadHttpsServer(SSLServerSocket serverSocket, Socket server, HashMap<String, String> predatorPrey, String predator, String prey) {
        this.serverSocket = serverSocket;
        this.server = server;
        this.predatorPrey = predatorPrey;
        this.predator = predator;
        this.prey = prey;

    }
}
