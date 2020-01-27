package my.teach.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import my.teach.request.*;
import my.teach.response.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpServer {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    private IHttpRequestHandler requestHandler;
    private IHttpResponseHandler responseHandler;
    private BufferedReader clientRequest;
    private Socket clientSocket;
    private PrintWriter clientResponse;
    private int port;

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOG.info("Server started!");
            while (true) {
                clientSocket = serverSocket.accept();
                clientRequest = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                clientResponse = new PrintWriter(clientSocket.getOutputStream(), true);
                requestHandler = new HttpRequestHandler(clientRequest);
                responseHandler = new HttpResponseHandler(clientResponse, requestHandler.getFilePath());
                responseHandler.sendHttpResponse();
            }
        } catch (IOException io) {
            LOG.warn(Arrays.toString(io.getStackTrace()));
        }
    }
}
