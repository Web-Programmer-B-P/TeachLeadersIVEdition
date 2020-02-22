package my.teach.utils;

import my.teach.response.Response;
import my.teach.session.Session;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    private static final String SPLIT_REGEX_COOKIES = "([\\s;|=]+)";
    private static final int LENGTH_OF_UUID = 36;
    private static final char END_LINE_SYMBOL = '\n';
    private static final int NUMBER_OF_FINISH_INPUT_STREAM = -1;
    private static final String MESSAGE_USER_EXISTS = "The user with userId: %s was create %s";
    private static final String MESSAGE_WELCOME_NEW_USER = "Welcome dear client your private userId: %s";

    private Utils() {
    }

    public static boolean isResourceExists(String resource) {
        boolean result = false;
        if (resource != null) {
            File checkResource = new File(resource);
            if (checkResource.exists() && checkResource.isFile()) {
                result = true;
            }
        }
        return result;
    }

    public static List<UUID> getUserIdList(String cookieRow) {
        List<UUID> res = null;
        if (cookieRow != null) {
            res = new ArrayList<>(Arrays.asList(cookieRow.split(SPLIT_REGEX_COOKIES)))
                    .stream().filter(e -> e.length() == LENGTH_OF_UUID)
                    .map(UUID::fromString)
                    .collect(Collectors.toList());
        }
        return res;
    }

    public static String readLine(InputStream inputStream) throws IOException {
        StringBuilder oneLine = new StringBuilder();
        int oneSymbolFromInputStream;
        do {
            oneSymbolFromInputStream = inputStream.read();
            if (oneSymbolFromInputStream == NUMBER_OF_FINISH_INPUT_STREAM) {
                break;
            }
            oneLine.append((char) oneSymbolFromInputStream);
        } while ((char) oneSymbolFromInputStream != END_LINE_SYMBOL);
        return oneLine.toString();
    }

    public static void readResource(Response response, BufferedOutputStream bufferedOutputStream) throws IOException {
        int lineFromResource;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(response.getResource()), response.getBufferSize())) {
            while ((lineFromResource = bufferedInputStream.read()) != NUMBER_OF_FINISH_INPUT_STREAM) {
                bufferedOutputStream.write(lineFromResource);
            }
        }
    }

    public static void writeInfoAboutUserId(UUID oldUserId, UUID newUserId, Session sessionIdStorage) {
        if (oldUserId != null) {
            LOGGER.log(Level.INFO, String.format(MESSAGE_USER_EXISTS, oldUserId,
                    sessionIdStorage.getDateCreateUserId(oldUserId)));
        } else {
            LOGGER.log(Level.INFO, String.format(MESSAGE_WELCOME_NEW_USER, newUserId));
        }
    }
}
