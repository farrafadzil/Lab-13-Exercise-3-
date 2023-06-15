// Client.java
import java.io.*;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        System.out.println("Connected to server");
    }

    public void sendRequest(String text) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println(text);
        System.out.println("Request sent to server");
    }

    public int receiveResponse() throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String response = reader.readLine();
        return Integer.parseInt(response);
    }

    public void closeConnection() throws IOException {
        socket.close();
        System.out.println("Connection closed");
    }

    public static void main(String[] args) {
        String serverAddress = "localhost"; // Replace with the server IP address or hostname
        int serverPort = 8080; // Replace with the server port

        Client client = new Client(serverAddress, serverPort);

        try {
            client.connectToServer();

            // Sample text to send for word count
            String text = "Hello, world! This is a sample text.";

            client.sendRequest(text);
            int wordCount = client.receiveResponse();

            System.out.println("Word count received from server: " + wordCount);

            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
