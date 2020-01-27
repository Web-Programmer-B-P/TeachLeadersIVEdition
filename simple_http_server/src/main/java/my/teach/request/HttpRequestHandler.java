package my.teach.request;

import my.teach.server.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class HttpRequestHandler implements IHttpRequestHandler {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    private final List<String> requestData;
    private final BufferedReader clientRequestData;

    public HttpRequestHandler(final BufferedReader clientRequestData) throws IOException {
        this.clientRequestData = clientRequestData;
        requestData = new LinkedList<>();
        clientRequestReader();
    }

    @Override
    public String getFilePath() {
        String firstLine = requestData.get(0);
        int startPathIndex = 0;
        int lastPathIndex = 0;
        for (int index = 0; index < firstLine.length(); index++) {
            if (firstLine.charAt(index) == '/' && startPathIndex == 0) {
                startPathIndex = index + 1;
            }
            if (firstLine.charAt(index) == ' ') {
                lastPathIndex = index;
            }
        }
        return firstLine.substring(startPathIndex, lastPathIndex);
    }

    private void clientRequestReader() throws IOException {
        LOG.info("Client connected!");
        do {
            String line = clientRequestData.readLine();
            LOG.info(line);
            requestData.add(line);
        } while (clientRequestData.ready());
        LOG.info("Client disconnected!");
    }
}
