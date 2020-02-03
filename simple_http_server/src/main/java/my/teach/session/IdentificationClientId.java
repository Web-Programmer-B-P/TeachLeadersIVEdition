package my.teach.session;

import java.util.*;
import java.util.stream.Collectors;

public class IdentificationClientId {
    public static final String SPLIT_REGEX_COOKIES = "([\\s;|=]+)";
    public static final int LENGTH_OF_UUID = 36;
    public static final String COOKIE_ROW_KEY = "Cookie";
    private final Map<UUID, Date> sessionIdStorage;
    private final Map<String, String> listCookieHeaders;

    public IdentificationClientId(Map<UUID, Date> sessionIdStorage, Map<String, String> listCookieHeaders) {
        this.sessionIdStorage = sessionIdStorage;
        this.listCookieHeaders = listCookieHeaders;
    }

    public void setNewUserIdToStorage(UUID newUserId) {
        if (!sessionIdStorage.containsKey(newUserId)) {
            sessionIdStorage.put(newUserId, new Date());
        }
    }

    private List<UUID> getUserIdList() {
        return new ArrayList<>(Arrays.asList(getCookieRow().split(SPLIT_REGEX_COOKIES)))
                .stream().filter(e -> e.length() == LENGTH_OF_UUID)
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    private String getCookieRow() {
        return listCookieHeaders.get(COOKIE_ROW_KEY);
    }

    public UUID getOldUserId() {
        UUID oldUserId = null;
        for (UUID userIdFromCookie : getUserIdList()) {
            if (sessionIdStorage.containsKey(userIdFromCookie)) {
                oldUserId = userIdFromCookie;
                break;
            }
        }
        return oldUserId;
    }
}
