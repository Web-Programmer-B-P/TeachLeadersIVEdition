package my.teach.utils;

import my.teach.response.Response;
import my.teach.session.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public final class Utils {
    private static final Logger LOG = LogManager.getLogger(Utils.class.getName());
    private static final String SPLIT_REGEX_COOKIES = "([\\s;|=]+)";
    private static final int LENGTH_OF_UUID = 36;
    private static final char END_LINE_SYMBOL = '\n';
    private static final int NUMBER_OF_FINISH_INPUT_STREAM = -1;
    private static final String FILE_404_HTML = "file404.html";

    private Utils() {
    }

    public static boolean isResourceExists(String resource) {
        boolean result = false;
        File checkResource = new File(resource);
        if (checkResource.exists() && checkResource.isFile()) {
            result = true;
        }
        return result;
    }

    public static List<UUID> getUserIdList(String cookieRow) {
        return new ArrayList<>(Arrays.asList(cookieRow.split(SPLIT_REGEX_COOKIES)))
                .stream().filter(e -> e.length() == LENGTH_OF_UUID)
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder oneLine = new StringBuilder();
        int oneSymbolFromInputStream;
        do {
            oneSymbolFromInputStream = inputStream.read();
            oneLine.append((char) oneSymbolFromInputStream);
            if (oneSymbolFromInputStream == NUMBER_OF_FINISH_INPUT_STREAM) {
                break;
            }
        } while ((char) oneSymbolFromInputStream != END_LINE_SYMBOL);
        return oneLine.toString();
    }

    public static void readResource(Response response, BufferedOutputStream bufferedOutputStream) throws IOException {
        int lineFromResource;
        String checkedResource = response.getResource();
        if (!response.isStatus()) {
            checkedResource = response.getRootPath() + FILE_404_HTML;
        }
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(checkedResource), response.getBufferSize())) {
            while ((lineFromResource = bufferedInputStream.read()) != NUMBER_OF_FINISH_INPUT_STREAM) {
                bufferedOutputStream.write(lineFromResource);
            }
        }
    }

    public static void writeInfoAboutUserId(UUID oldUserId, UUID newUserId, Session sessionIdStorage) {
        if (oldUserId != null) {
            LOG.info(String.format("The user with userId: %s was create %s", oldUserId, sessionIdStorage.getDateCreateUserId(oldUserId)));
        } else {
            LOG.info(String.format("Welcome dear client your private userId: %s", newUserId));
        }
    }
}
