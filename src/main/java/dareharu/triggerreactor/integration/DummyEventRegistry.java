package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.IEventHook;
import io.github.wysohn.triggerreactor.core.main.IEventRegistry;

import java.util.Collection;
import java.util.Collections;

@Singleton
public final class DummyEventRegistry implements IEventRegistry {

    @Override
    public boolean eventExist(final String maybeEventClassPath) {
        return false;
    }

    @Override
    public Class<?> getEvent(final String maybeEventClassPath) throws ClassNotFoundException {
        return null;
    }

    @Override
    public void registerEvent(final Class<?> clazz, final IEventHook hook) {}

    @Override
    public void unregisterEvent(final IEventHook hook) {}

    @Override
    public void unregisterAll() {}

    @Override
    public Collection<String> getAbbreviations() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "PlaygroundEventRegistry";
    }

}
