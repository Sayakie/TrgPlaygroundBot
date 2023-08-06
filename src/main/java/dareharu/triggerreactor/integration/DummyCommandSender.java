package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.bridge.ICommandSender;

@Singleton
public final class DummyCommandSender implements ICommandSender {

    @Override
    public DummyCommandSender get() {
        return this;
    }

    @Override
    public String getName() {
        return "Console";
    }

    @Override
    public boolean hasPermission(final String node) {
        return true;
    }

    @Override
    public void sendMessage(final String message) {
        System.out.println(message);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "PlaygroundCommandSender";
    }

}
