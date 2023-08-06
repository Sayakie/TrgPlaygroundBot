package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.bridge.ICommandSender;
import io.github.wysohn.triggerreactor.core.bridge.IWorld;
import io.github.wysohn.triggerreactor.core.bridge.entity.IPlayer;
import io.github.wysohn.triggerreactor.core.main.IGameManagement;
import io.github.wysohn.triggerreactor.core.manager.location.SimpleLocation;
import io.github.wysohn.triggerreactor.core.manager.trigger.Trigger;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Singleton
@SuppressWarnings("unchecked")
public final class DummyGameManagement implements IGameManagement {

    @Override
    public Iterable<IPlayer> getOnlinePlayers() {
        return (Iterable<IPlayer>) Collections.emptyIterator();
    }

    @Override
    public Iterable<IWorld> getWorlds() {
        return (Iterable<IWorld>) Collections.emptyIterator();
    }

    @Override
    public IWorld getWorld(final String worldName) {
        return null;
    }

    @Override
    public IPlayer getPlayer(final String playerName) {
        return null;
    }

    @Override
    public void showGlowStones(final ICommandSender sender, Set<Map.Entry<SimpleLocation, Trigger>> locationAssociatedTriggers) {}

    @Override
    public String toString() {
        return "PlaygroundGameManagement";
    }

}
