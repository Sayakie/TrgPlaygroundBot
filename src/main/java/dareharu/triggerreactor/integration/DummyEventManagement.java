package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.bridge.ICommandSender;
import io.github.wysohn.triggerreactor.core.bridge.event.IEvent;
import io.github.wysohn.triggerreactor.core.main.IEventManagement;

@Singleton
public final class DummyEventManagement implements IEventManagement {

    @Override
    public Object createPlayerCommandEvent(final ICommandSender sender, final String label, final String[] args) {
        return null;
    }

    @Override
    public Object createEmptyPlayerEvent(final ICommandSender sender) {
        return null;
    }

    @Override
    public void callEvent(final IEvent event) {}

}
