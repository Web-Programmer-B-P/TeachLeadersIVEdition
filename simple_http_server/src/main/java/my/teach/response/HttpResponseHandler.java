package my.teach.response;

import my.teach.server.HttpServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;

public class HttpResponseHandler implements IHttpResponseHandler {
    private static final Logger LOG = LogManager.getLogger(HttpServer.class.getName());
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private StringBuilder headStatusOk = new StringBuilder()
            .append("HTTP/1.1 200 OK")
            .append(LINE_SEPARATOR)
            .append("Content-Type: text/html; charset=utf-8")
            .append(LINE_SEPARATOR)
            .append(LINE_SEPARATOR);
    private StringBuilder headStatusNotFound = new StringBuilder()
            .append("HTTP/1.1 404 Not Found")
            .append(LINE_SEPARATOR)
            .append("Content-Type: text/html; charset=utf-8")
            .append(LINE_SEPARATOR)
            .append(LINE_SEPARATOR)
            .append("<h1>Not Found</h1>");
    private final String filePath;
    private PrintWriter senderOfHttpRequests;

    public HttpResponseHandler(PrintWriter senderOfHttpRequests, String filePath) {
        this.filePath = filePath;
        this.senderOfHttpRequests = senderOfHttpRequests;
    }

    @Override
    public void sendHttpResponse() {
        if (checkFilePath()) {
            senderOfHttpRequests.println(headStatusOk.toString());
            fileReader(senderOfHttpRequests);
        } else {
            senderOfHttpRequests.println(headStatusNotFound.toString());
        }
        senderOfHttpRequests.flush();
    }

    private void fileReader(PrintWriter senderOfHttpRequests) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String singleLine = null;
            while ((singleLine = bufferedReader.readLine()) != null) {
                senderOfHttpRequests.println(singleLine);
            }
        } catch (IOException io) {
            LOG.warn(io.getStackTrace());
        }
    }

    private boolean checkFilePath() {
        return new File(filePath).exists();
    }
}
