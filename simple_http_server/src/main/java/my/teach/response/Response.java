package my.teach.response;

import java.util.Date;
import java.util.UUID;

public class Response {
    private static final String OK = "HTTP/1.1 200 OK";
    private static final String NOT_FOUND = "HTTP/1.1 404 Not Found";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String CHARSET = "charset=";
    private static final String DATE = "date: ";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SET_COOKIE = "Set-Cookie: ";
    private static final String USER_ID = "userId=";
    private static final String NOT_FOUND_BODY = "<h1 style='text-align:center'>Resource not found</h1>";
    private boolean status;
    private int bufferSize;
    private UUID userId;
    private String contextType;
    private String charset;
    private Date date;
    private String resource;

    public Response() {

    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getResponse() {
        return prepareResponse();
    }

    public void buildResponse() {
        prepareResponse();
    }

    private String getCookieRow() {
        return userId != null ? SET_COOKIE + USER_ID + userId : SET_COOKIE;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return this.resource;
    }

    public boolean isStatus() {
        return status;
    }

    private String prepareResponse() {
        StringBuilder response = new StringBuilder();
        response.append(status ? OK : NOT_FOUND).append(LINE_SEPARATOR)
                .append(CONTENT_TYPE).append(contextType).append(LINE_SEPARATOR)
                .append(CHARSET).append(charset).append(LINE_SEPARATOR)
                .append(CHARSET).append(charset).append(LINE_SEPARATOR)
                .append(DATE).append(date.toString()).append(LINE_SEPARATOR)
                .append(getCookieRow()).append(LINE_SEPARATOR)
                .append(LINE_SEPARATOR).append(!status ? NOT_FOUND_BODY : "");
        return response.toString();
    }
}
