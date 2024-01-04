package client_server;
import java.io.*;
import java.net.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class client {
    public static void main(String[] args) throws IOException, UnknownHostException, SocketException {
        
        int port = 443; // Standard port for HTTPS traffic
        String hostName = "127.0.0.1"; // localhost
        

        System.out.println("Connecting... Please wait.");
        System.setProperty("javax.net.ssl.trustStore", "C:\\snslab\\PiaS.jks"); // Location of the Java Keystore file, used to authenticate remote servers
        System.setProperty("javax.net.ssl.trustStorePassword", "assignment"); // Password needed to access the file
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket s = (SSLSocket) factory.createSocket(hostName, port); // Creates SSLSocket
        s.setEnabledProtocols(new String[]{"TLSv1.3", "TLSv1.2"});
        s.startHandshake(); // Handshake between client and server

        if (s.isConnected()) {
            System.out.println("Connected!");
            System.out.println(" Connected to Server's: Port: " + s.getPort() + "\n Address: " + s.getRemoteSocketAddress());

        }

        // Input and output streams for the socket...
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        String predator = "";
        String prey = "";

        while (true) { 
            System.out.print("Please enter a predator: ");
            predator = input.readLine(); 

            if (predator.equals("Close")) {
                break;
            }

            out.println(predator); // predator sent to the server
            
            try {
                prey = in.readLine(); // prey received from the server
            } catch (SocketException e) {
                System.out.println("error here");
                prey = in.readLine();
                continue;
            }
            
       
            if (prey != null) {
                System.out.println("Prey: " + prey); 
            } else {
                System.out.println("No response from server.");
                
            }
            
        }
        //safely close the socket and thread
        in.close();
        out.close();
        s.close();
        System.exit(0);
            

        
    }
}
