package dareharu.triggerreactor.integration;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.main.command.ICommand;
import io.github.wysohn.triggerreactor.core.main.command.ICommandHandler;

@Singleton
public final class DummyCommandHandler implements ICommandHandler {

    @Inject
    private static final ICommand DUMMY_COMMAND = null;

    @Override
    public ICommand register(final String name, final String[] aliases) {
        return DUMMY_COMMAND;
    }

    @Override
    public boolean unregister(final String name) {
        return false;
    }

    @Override
    public void sync() {}
}
