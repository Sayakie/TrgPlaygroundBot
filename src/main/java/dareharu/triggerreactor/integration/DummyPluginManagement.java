package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.bridge.ICommandSender;
import io.github.wysohn.triggerreactor.core.bridge.IInventory;
import io.github.wysohn.triggerreactor.core.bridge.entity.IPlayer;
import io.github.wysohn.triggerreactor.core.main.IPluginManagement;
import io.github.wysohn.triggerreactor.core.manager.trigger.inventory.InventoryTrigger;
import io.github.wysohn.triggerreactor.core.script.interpreter.interrupt.ProcessInterrupter;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class DummyPluginManagement implements IPluginManagement {

    private static final String PLUGIN_DESCRIPTION = "Blazing fast TriggerReactor interpreter with Discord interactions.";
    private static final String VERSION = "@@version@@";
    private static final String AUTHOR = "Sayakie";

    @Override
    public String getPluginDescription() {
        return PLUGIN_DESCRIPTION;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public String getAuthor() {
        return AUTHOR;
    }

    @Override
    public ICommandSender getConsoleSender() {
        return null;
    }

    @Override
    public void runCommandAsConsole(final String commandLine) {}

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isDebugging() {
        return false;
    }

    @Override
    public void setDebugging(final boolean state) {}

    @Override
    public void disablePlugin() {}

    @Override
    public <T> T getMain() {
        return null;
    }

    @Override
    public ProcessInterrupter createInterrupter(final Map<UUID, Long> cooldowns) {
        return null;
    }

    @Override
    public ProcessInterrupter createInterrupterForInv(
        final Map<UUID, Long> cooldowns,
        final Map<IInventory, InventoryTrigger> inventoryMap
    ) {
        return null;
    }

    @Override
    public IPlayer extractPlayerFromContext(final Object context) {
        return null;
    }

    @Override
    public Map<String, Object> getCustomVarsForTrigger(final Object context) {
        return Collections.emptyMap();
    }

    @Override
    public String toString() {
        return "PlaygroundPluginManagement";
    }

}
