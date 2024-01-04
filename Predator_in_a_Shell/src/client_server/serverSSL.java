package client_server;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class serverSSL {

    public static void main(String[] args) throws IOException {
        int portNumber = 443;
        HashMap<String, String> predatorPrey = new HashMap<>(); // hashmap for predator and prey
        Socket server = new Socket(); // socket used after SSL handshake is completed
        String predator = "";
        String prey = "";
        
        // creating the hashmap
        predatorPrey.put("Cat", "Mouse");
        predatorPrey.put("Fox", "Rabbit");
        predatorPrey.put("Lion", "Zebra");
        predatorPrey.put("Seal", "Penguin");
        predatorPrey.put("Bear", "Seal");

		System.setProperty("javax.net.ssl.keyStore", "C:\\snslab\\PiaS.jks"); // Used to authenticate remote client
        System.setProperty("javax.net.ssl.keyStorePassword", "assignment"); // Password needed to view certificate
		SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(portNumber); // create SSLServerSocket
        serverSocket.setEnabledProtocols(new String[]{"TLSv1.3", "TLSv1.2"}); // the Transport Layer Security protocol versions allowed by the server

        System.out.println("Waiting for connections...");
        

        while (true) { 
            // new thread for each client connection 
            threadHttpsServer thread = new threadHttpsServer(serverSocket, server, predatorPrey, predator, prey);
            thread.start(); 
            
        }
        
    }
}
