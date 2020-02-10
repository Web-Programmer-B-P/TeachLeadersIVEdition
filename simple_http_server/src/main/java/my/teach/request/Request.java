package my.teach.request;

import java.util.Map;

public class Request {
    private static final String SPLIT_SPACE = " ";
    private static final int SECOND_ELEMENT = 1;
    private static final int BEGIN_INDEX = 1;
    private final Map<String, String> headers;
    private final String firstLine;
    private final String resource;
    private final int bufferSize;

    public Request(Map<String, String> headers, String firstLine, String resource, int bufferSize) {
        this.headers = headers;
        this.firstLine = firstLine;
        this.resource = resource;
        this.bufferSize = bufferSize;
    }

    public String getResource() {
        return resource + firstLine.split(SPLIT_SPACE)[SECOND_ELEMENT].substring(BEGIN_INDEX);
    }

    public String getHeaderRow(String key) {
        return key != null ? headers.get(key) : null;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public String getRootPath() {
        return resource;
    }
}
