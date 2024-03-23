

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


@Slf4j
public class HelloServer {
    public void start(int port) {
        String host = "localhost";
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                log.info("client connected");
                try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
                    Message message = (Message) inputStream.readObject();
                    log.info("server receive message:" + message.getContent());
                    message.setContent("new content");
                    outputStream.writeObject(message);
                    outputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ExecutorService threadPool = new ThreadPoolExecutor(10, 100, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100), threadFactory);
        threadPool.execute(() -> {
            HelloServer server = new HelloServer();
            server.start(6666);
        });
    }

}
