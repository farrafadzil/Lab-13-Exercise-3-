// Server.java
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started and listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connection accepted");

            Thread clientThread = new ClientHandler(clientSocket);
            clientThread.start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        System.out.println("Server stopped");
    }

    public static void main(String[] args) {
        int serverPort = 8080; // Replace with the desired server port

        Server server = new Server(serverPort);

        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Read the text sent by the client
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String text = reader.readLine();
            System.out.println("Text received from client: " + text);

            // Process the text to count the words
            int wordCount = TextProcessor.countWords(text);

            // Send the word count back to the client
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.println(wordCount);
            System.out.println("Word count sent to client: " + wordCount);

            clientSocket.close();
            System.out.println("Client connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TextProcessor {
    public static int countWords(String text) {
        String[] words = text.trim().split("\\s+");
        return words.length;
    }
}
