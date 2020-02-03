package my.teach.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;

public class HttpResponseHandler {
    private static final Logger LOG = LogManager.getLogger(HttpResponseHandler.class.getName());
    private static final int OK = 200;
    private final OutputStream writeHttpResponse;
    private final int status;
    private final String resource;
    private final String responseHeader;

    public HttpResponseHandler(OutputStream writeHttpResponse, int status, String resource, String responseHeader) {
        this.writeHttpResponse = writeHttpResponse;
        this.status = status;
        this.resource = resource;
        this.responseHeader = responseHeader;
    }

    public void sendHttpResponse() throws IOException {
        if (status == OK) {
            writeHttpResponse.write(responseHeader.getBytes());
            readAndSetResource(writeHttpResponse);
        } else {
            writeHttpResponse.write(responseHeader.getBytes());
        }
        writeHttpResponse.flush();
    }

    private void readAndSetResource(OutputStream senderOfHttpRequests) {
        try (FileInputStream inputStreamResource = new FileInputStream(new File(resource))) {
            int oneSymbol = inputStreamResource.read();
            while (oneSymbol != -1) {
                senderOfHttpRequests.write(oneSymbol);
                oneSymbol = inputStreamResource.read();
            }
        } catch (IOException io) {
            LOG.warn(io);
        }
    }
}
