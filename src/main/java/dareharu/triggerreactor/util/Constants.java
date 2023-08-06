package dareharu.triggerreactor.util;

import java.util.UUID;

public final class Constants {

    private static final UUID NIL_UUID = new UUID(0, 0);
    private static final UUID UID = java.util.UUID.randomUUID();

    public static UUID nil() {
        return NIL_UUID;
    }

    public static UUID uniqueId() {
        return UID;
    }

    private Constants() {}

}
