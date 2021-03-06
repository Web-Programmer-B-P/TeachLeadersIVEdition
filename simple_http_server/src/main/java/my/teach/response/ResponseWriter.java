package my.teach.response;

import my.teach.utils.Utils;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class ResponseWriter {
    public ResponseWriter() {

    }

    public void write(Response response, OutputStream outputStream) throws IOException {
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, response.getBufferSize())) {
            bufferedOutputStream.write(response.getResponse().getBytes(StandardCharsets.UTF_8));
            if (response.isStatus()) {
                Utils.readResource(response, bufferedOutputStream);
            }
            bufferedOutputStream.flush();
        }
    }
}
