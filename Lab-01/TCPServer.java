import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 6789;  // Port the server listens on

        try (ServerSocket welcomeSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);

            while (true) {
                // Wait for client connection
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Client connected: " + connectionSocket.getInetAddress().getHostAddress());

                // Set up input and output streams
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                // Read message from client
                String clientSentence = inFromClient.readLine();
                System.out.println("Received: " + clientSentence);

                // Process and send response
                String capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);

                // Close connection
                connectionSocket.close();
                System.out.println("Connection closed.");
            }

        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
