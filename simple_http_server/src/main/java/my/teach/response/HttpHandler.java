package my.teach.response;

import my.teach.request.Request;
import my.teach.session.Session;
import my.teach.utils.Utils;
import java.util.Date;
import java.util.UUID;

public class HttpHandler {
    private static final String TEXT_HTML = "text/html;";
    private static final String CHARSET_UTF_8 = "charset=utf-8";

    public HttpHandler() {

    }

    public void handle(Request request, Response response, Session sessionIdStorage) {
        UUID newUserId = sessionIdStorage.getNewUserId();
        UUID oldUserID = sessionIdStorage.findUuid(Utils.getUserIdList(request.getHeaderRow("Cookie")));
        response.setRootPath(request.getRootPath());
        response.setBufferSize(request.getBufferSize());
        response.setStatus(Utils.isResourceExists(request.getResource()));
        response.setContextType(TEXT_HTML);
        response.setCharset(CHARSET_UTF_8);
        response.setDate(new Date());
        response.setUserId(oldUserID != null ? null : sessionIdStorage.setUserIdAndReturn(newUserId));
        Utils.writeInfoAboutUserId(oldUserID, newUserId, sessionIdStorage);
        response.setResource(request.getResource());
        response.buildResponse();
    }
}
