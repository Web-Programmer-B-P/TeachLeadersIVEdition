package my.teach.session;

import java.util.*;

public class Session {

    private final Map<UUID, Date> listUUID;

    public Session() {
        listUUID = new HashMap<>();
    }

    public synchronized UUID setUserIdAndReturn(UUID userId) {
        listUUID.put(userId, new Date());
        return userId;
    }

    public synchronized UUID findUuid(List<UUID> listExistsUuid) {
        UUID oldUserId = null;
        if (listExistsUuid != null) {
            for (UUID userIdFromCookie : listExistsUuid) {
                if (listUUID.containsKey(userIdFromCookie)) {
                    oldUserId = userIdFromCookie;
                    break;
                }
            }
        }
        return oldUserId;
    }

    public UUID getNewUserId() {
        return UUID.randomUUID();
    }

    public synchronized Date getDateCreateUserId(UUID existsUserId) {
        Date date = null;
        if (listUUID.containsKey(existsUserId)) {
            date = listUUID.get(existsUserId);
        }
        return date;
    }

}
