package my.teach.server;

import my.teach.request.Request;
import my.teach.request.RequestReader;
import my.teach.response.HttpHandler;
import my.teach.response.Response;
import my.teach.response.ResponseWriter;
import my.teach.session.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.*;

public class Server {
    private static final Logger LOG = LogManager.getLogger(Server.class.getName());
    private static final String SERVER_STARTED_MESSAGE = "Server started!";
    private static final String CLIENT_CONNECTED_MESSAGE = "Client connected!";
    private static final String CLIENT_DISCONNECTED_MESSAGE = "Client disconnected!";
    private final int port;
    private final int bufferSize;
    private final String resource;
    private Session sessionIdStorage;

    public Server(int port, int bufferSize, String resource) {
        this.port = port;
        this.bufferSize = bufferSize;
        this.resource = resource;
        sessionIdStorage = new Session();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOG.info(SERVER_STARTED_MESSAGE);
            while (true) {
                try (Socket socket = serverSocket.accept(); OutputStream socketOutputStream = socket.getOutputStream();
                     InputStream socketInputStream = socket.getInputStream()) {
                    LOG.info(CLIENT_CONNECTED_MESSAGE);
                    Request request = new RequestReader().handleRequest(socketInputStream, bufferSize, resource);
                    Response response = new Response();
                    HttpHandler httpHandler = new HttpHandler();
                    httpHandler.handle(request, response, sessionIdStorage);
                    ResponseWriter responseWriter = new ResponseWriter();
                    responseWriter.write(response, socketOutputStream);
                    LOG.info(CLIENT_DISCONNECTED_MESSAGE);
                }
            }
        } catch (IOException io) {
            LOG.error(io);
        }
    }
}