package my.teach.header;

import java.util.Date;
import java.util.UUID;

public class HttpHeaderHandler {
    private static final String OK = "HTTP/1.1 200 OK";
    private static final String NOT_FOUND = "HTTP/1.1 404 Not Found";
    private static final String CONTENT_TYPE = "Content-Type: ";
    private static final String TEXT_HTML = "text/html;";
    private static final String CHARSET_UTF_8 = "charset=utf-8";
    private static final String DATE = "date: " + new Date().toString();
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String SET_COOKIE = "Set-Cookie: ";
    public static final String SESSION_ID = "sessionId=";
    private final int status;
    private final UUID userId;

    public HttpHeaderHandler(int status, UUID userId) {
        this.status = status;
        this.userId = userId;
    }

    public String getResponseHeader() {
        String firstLine = status == 200 ? OK : NOT_FOUND;
        return firstLine
                + LINE_SEPARATOR
                + CONTENT_TYPE + TEXT_HTML + CHARSET_UTF_8
                + LINE_SEPARATOR
                + DATE
                + LINE_SEPARATOR
                + prepareCookieRow()
                + LINE_SEPARATOR
                + LINE_SEPARATOR;
    }

    private String prepareCookieRow() {
        return userId != null ? SET_COOKIE + SESSION_ID + userId : SET_COOKIE;
    }
}
