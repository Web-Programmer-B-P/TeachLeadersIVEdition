package my.teach.server;

import my.teach.request.Request;
import my.teach.request.RequestReader;
import my.teach.response.HttpHandler;
import my.teach.response.Response;
import my.teach.response.ResponseWriter;
import my.teach.session.Session;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final String SERVER_STARTED_MESSAGE = "Server started!";
    private static final String CLIENT_CONNECTED_MESSAGE = "Client connected!";
    private static final String CLIENT_DISCONNECTED_MESSAGE = "Client disconnected!";
    private final int port;
    private final int bufferSize;
    private final String resource;
    private final Session sessionIdStorage;

    public Server(int port, int bufferSize, String resource) {
        this.port = port;
        this.bufferSize = bufferSize;
        this.resource = resource;
        sessionIdStorage = new Session();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.log(Level.INFO, SERVER_STARTED_MESSAGE);
            int sizeOfPool = Runtime.getRuntime().availableProcessors();
            ExecutorService threadPool = Executors.newFixedThreadPool(sizeOfPool);
            for (int index = 0; index < sizeOfPool; index++) {
                threadPool.execute(new Thread(() -> {
                    while (true) {
                        try (Socket socket = serverSocket.accept();
                             OutputStream socketOutputStream = socket.getOutputStream();
                             InputStream socketInputStream = socket.getInputStream()) {
                            LOGGER.log(Level.INFO, CLIENT_CONNECTED_MESSAGE);
                            Request request = new RequestReader().handleRequest(socketInputStream, bufferSize, resource);
                            Response response = new Response();
                            HttpHandler httpHandler = new HttpHandler();
                            httpHandler.handle(request, response, sessionIdStorage);
                            ResponseWriter responseWriter = new ResponseWriter();
                            responseWriter.write(response, socketOutputStream);
                            LOGGER.log(Level.INFO, CLIENT_DISCONNECTED_MESSAGE);
                        } catch (IOException io) {
                            LOGGER.log(Level.SEVERE, "Старт таска не удался :(", io);
                        }
                    }
                }));
            }
            Thread.currentThread().join();
        } catch (IOException | InterruptedException io) {
            LOGGER.log(Level.SEVERE, "Ошбка при запуске сервера", io);
        }
    }
}