import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";  // localhost (change to server IP if remote)
        int port = 6789;

        try (Socket clientSocket = new Socket(serverAddress, port)) {
            // Set up output and input streams
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // User input from console
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter message: ");
            String sentence = userInput.readLine();

            // Send message to server
            outToServer.writeBytes(sentence + '\n');

            // Read server response
            String modifiedSentence = inFromServer.readLine();
            System.out.println("FROM SERVER: " + modifiedSentence);

        } catch (IOException e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
