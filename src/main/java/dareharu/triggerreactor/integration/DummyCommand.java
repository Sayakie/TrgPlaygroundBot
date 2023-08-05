package dareharu.triggerreactor.integration;

import com.google.inject.Singleton;
import io.github.wysohn.triggerreactor.core.main.command.ICommand;
import io.github.wysohn.triggerreactor.core.main.command.ICommandExecutor;
import io.github.wysohn.triggerreactor.core.manager.trigger.command.ITabCompleter;

import java.util.Map;
import java.util.Set;

@Singleton
public final class DummyCommand implements ICommand {

    @Override
    public void setTabCompleterMap(final Map<Integer, Set<ITabCompleter>> tabCompleters) {}

    @Override
    public void setExecutor(final ICommandExecutor executor) {}

}
