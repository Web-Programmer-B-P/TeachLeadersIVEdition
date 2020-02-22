package my.teach.request;

import java.util.Map;

public class Request {
    private static final String SPLIT_SPACE = " ";
    private static final int SECOND_ELEMENT = 1;
    private final Map<String, String> headers;
    private final String firstLine;
    private final String rootPath;
    private final int bufferSize;

    public Request(Map<String, String> headers, String firstLine, String resource, int bufferSize) {
        this.headers = headers;
        this.firstLine = firstLine;
        this.rootPath = resource;
        this.bufferSize = bufferSize;
    }

    public String getResource() {
        String result = null;
        if (firstLine != null) {
            result = rootPath + firstLine.split(SPLIT_SPACE)[SECOND_ELEMENT];
        }
        return result;
    }

    public String getHeaderRow(String key) {
        return key != null ? headers.get(key) : null;
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
