package dareharu.triggerreactor.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.Map;

public final class LocalVariableUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Event> T requireEvent(final Map<?, ?> vars) {
        return (T) vars.get("event");
    }

    public static Player requirePlayer(final Map<?, ?> vars) {
        return (Player) vars.get("player");
    }

}
