package my.teach.request;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {
    private static final char END_LINE_SYMBOL = '\n';
    private static final String SEPARATOR_BETWEEN_HEAD_AND_BODY = "\r\n";
    private static final String SPLIT_SPACE = " ";
    private static final String SPLIT_SEPARATOR_SYMBOL = ":";
    private final Map<String, String> headerList;
    private final InputStream clientRequestData;
    private String firstHeaderLine;

    public HttpRequestHandler(final InputStream inputStreamFromClient) throws IOException {
        this.clientRequestData = inputStreamFromClient;
        headerList = new HashMap<>();
        readAndSetClientRequest();
    }

    private String readLine(InputStream inputStream) throws IOException {
        StringBuilder readLine = new StringBuilder();
        int oneSymbolFromInputStream;
        do {
            oneSymbolFromInputStream = inputStream.read();
            readLine.append((char) oneSymbolFromInputStream);
        } while ((char) oneSymbolFromInputStream != END_LINE_SYMBOL);
        return readLine.toString();
    }

    private void readAndSetClientRequest() throws IOException {
        String nextHeaderLine = readLine(clientRequestData);
        firstHeaderLine = nextHeaderLine;
        while (!nextHeaderLine.equals(SEPARATOR_BETWEEN_HEAD_AND_BODY)) {
            if (nextHeaderLine.contains(SPLIT_SEPARATOR_SYMBOL)) {
                fillHeaderMap(nextHeaderLine);
            }
            nextHeaderLine = readLine(clientRequestData);
        }
    }

    public String getResource() {
        return firstHeaderLine.split(SPLIT_SPACE)[1].substring(1);
    }

    private void fillHeaderMap(String line) {
        String[] separateLine = line.split(SPLIT_SEPARATOR_SYMBOL, 2);
        headerList.put(separateLine[0], separateLine[1]);
    }

    public Map<String, String> getHeaderMap() {
        return headerList;
    }
}
