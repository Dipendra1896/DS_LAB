import java.net.*;
import java.io.*;

public class UDPClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 9876;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            byte[] sendData;
            byte[] receiveData = new byte[1024];

            System.out.print("Enter message: ");
            String sentence = userInput.readLine();

            sendData = sentence.getBytes();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("FROM SERVER: " + modifiedSentence);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}