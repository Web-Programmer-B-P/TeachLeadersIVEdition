package my.teach.request;

import my.teach.utils.Utils;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    private static final String EMPTY_LINE = "\r\n";
    private static final String SPLIT_SEPARATOR_SYMBOL = ":";
    private static final int LIMIT_OF_ELEMENTS_GET = 2;
    private static final int KEY = 0;
    private static final int VALUE = 1;
    private static final int EMPTY_LENGTH = 0;
    private final Map<String, String> headers;

    public RequestReader() {
        headers = new HashMap<>();
    }

    public Request handleRequest(InputStream inputStream, int bufferSize, String resource) throws IOException {
        String firstLine = null;
        String oneLine = Utils.readLine(inputStream);
        while (!(oneLine).equals(EMPTY_LINE) && oneLine.length() != EMPTY_LENGTH) {
            firstLine = firstLine == null ? oneLine : firstLine;
            fillHeadersMap(oneLine);
            oneLine = Utils.readLine(inputStream);
        }
        return new Request(headers, firstLine, resource, bufferSize);
    }

    private void fillHeadersMap(String row) {
        if (row.contains(SPLIT_SEPARATOR_SYMBOL)) {
            String[] separateLine = row.split(SPLIT_SEPARATOR_SYMBOL, LIMIT_OF_ELEMENTS_GET);
            headers.put(separateLine[KEY], separateLine[VALUE]);
        }
    }
}
