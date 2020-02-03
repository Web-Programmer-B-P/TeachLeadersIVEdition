package my.teach.status;

import java.io.File;

public class HttpStatusResponse {
    private final String resource;
    private static final int STATUS_RESPONSE_OK = 200;
    private static final int STATUS_RESPONSE_NOT_FOUND = 404;

    public HttpStatusResponse(String resource) {
        this.resource = resource;
    }

    public int getStatus() {
        return isResourceExist(resource) ? STATUS_RESPONSE_OK : STATUS_RESPONSE_NOT_FOUND;
    }

    private boolean isResourceExist(String resource) {
        return new File(resource).exists();
    }
}
