package github.javaguide;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class HelloClient {
    public Object send(Message message, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        Message message = (Message) helloClient.send(new Message("content from client"), "127.0.0.1", 6666);
        System.out.println("client receive message:" + message.getContent());
    }
}
