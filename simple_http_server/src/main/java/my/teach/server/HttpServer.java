package my.teach.server;

import my.teach.header.HttpHeaderHandler;
import my.teach.request.HttpRequestHandler;
import my.teach.response.HttpResponseHandler;
import my.teach.session.IdentificationClientId;
import my.teach.status.HttpStatusResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpServer {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    public static final String SERVER_STARTED_MESSAGE = "Server started!";
    private int port;
    private static final String CLIENT_CONNECTED_MESSAGE = "Client connected!";
    public static final String CLIENT_DISCONNECTED_MESSAGE = "Client disconnected!";
    private Map<UUID, Date> sessionIdStorage;
    private UUID userId;

    public HttpServer(int port) {
        this.port = port;
        sessionIdStorage = new HashMap<>();
    }

    public void start() {
        InputStream requestReader;
        Socket waitForClientConnection;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOG.info(SERVER_STARTED_MESSAGE);
            while (true) {
                userId = UUID.randomUUID();
                waitForClientConnection = serverSocket.accept();
                LOG.info(CLIENT_CONNECTED_MESSAGE);
                requestReader = waitForClientConnection.getInputStream();
                sendResponseToClient(requestReader, waitForClientConnection);
                LOG.info(CLIENT_DISCONNECTED_MESSAGE);
            }
        } catch (IOException io) {
            LOG.warn(io);
        }
    }

    private void sendResponseToClient(InputStream requestReader, Socket waitForClientConnection) throws IOException {
        HttpRequestHandler requestHandler = new HttpRequestHandler(requestReader);
        HttpStatusResponse status = new HttpStatusResponse(requestHandler.getResource());
        setAndWriteUserId(waitForClientConnection, requestHandler, status);
    }

    private void setAndWriteUserId(Socket waitForClientConnection, HttpRequestHandler requestHandler, HttpStatusResponse status) throws IOException {
        IdentificationClientId identificationClientId = new IdentificationClientId(sessionIdStorage, requestHandler.getHeaderMap());
        UUID oldUserId = identificationClientId.getOldUserId();
        if (oldUserId != null) {
            LOG.info(String.format("The user with userId: %s was create %s", oldUserId, sessionIdStorage.get(oldUserId)));
            sendReadyResponse(requestHandler, waitForClientConnection, status, null);
        } else {
            identificationClientId.setNewUserIdToStorage(userId);
            LOG.info(String.format("Welcome dear client your private userId: %s", userId));
            sendReadyResponse(requestHandler, waitForClientConnection, status, userId);
        }
    }

    private void sendReadyResponse(HttpRequestHandler requestHandler, Socket waitForClientConnection, HttpStatusResponse status, UUID userIdForCookieHeader) throws IOException {
        HttpHeaderHandler httpHeaderResponse = new HttpHeaderHandler(status.getStatus(), userIdForCookieHeader);
        HttpResponseHandler responseHandler = new HttpResponseHandler(
                waitForClientConnection.getOutputStream(), status.getStatus(), requestHandler.getResource(),
                httpHeaderResponse.getResponseHeader());
        responseHandler.sendHttpResponse();
    }
}
