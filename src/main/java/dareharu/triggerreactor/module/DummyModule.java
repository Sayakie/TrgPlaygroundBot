package dareharu.triggerreactor.module;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import dareharu.triggerreactor.integration.*;
import dareharu.triggerreactor.integration.bukkit.DummyServer;
import io.github.wysohn.triggerreactor.bukkit.manager.trigger.share.CommonFunctions;
import io.github.wysohn.triggerreactor.core.bridge.ICommandSender;
import io.github.wysohn.triggerreactor.core.config.source.GsonConfigSource;
import io.github.wysohn.triggerreactor.core.config.source.IConfigSource;
import io.github.wysohn.triggerreactor.core.config.source.IConfigSourceFactory;
import io.github.wysohn.triggerreactor.core.main.*;
import io.github.wysohn.triggerreactor.core.main.command.ICommandHandler;
import io.github.wysohn.triggerreactor.core.manager.IGlobalVariableManager;
import io.github.wysohn.triggerreactor.core.manager.js.IBackedMapProvider;
import io.github.wysohn.triggerreactor.core.manager.trigger.ITriggerDependencyFacade;
import io.github.wysohn.triggerreactor.core.manager.trigger.TriggerDependencyFacade;
import io.github.wysohn.triggerreactor.core.module.APISupportModule;
import io.github.wysohn.triggerreactor.core.script.interpreter.Executor;
import io.github.wysohn.triggerreactor.core.script.interpreter.Placeholder;
import io.github.wysohn.triggerreactor.core.script.interpreter.TaskSupervisor;
import io.github.wysohn.triggerreactor.core.script.wrapper.SelfReference;
import org.bukkit.Server;

public final class DummyModule extends AbstractModule {

    @Override
    protected void configure() {
        MockBukkit.mock(new DummyServer());
        bind(Server.class).to(DummyServer.class);

        bind(ICommandSender.class).to(DummyCommandSender.class);
        bind(ICommandHandler.class).to(DummyCommandHandler.class);
        bind(IEventManagement.class).to(DummyEventManagement.class);
        bind(IEventRegistry.class).to(DummyEventRegistry.class);
        bind(IGameManagement.class).to(DummyGameManagement.class);
        bind(IInventoryHandle.class).to(DummyInventoryHandle.class);
        bind(IPluginManagement.class).to(DummyPluginManagement.class);
        bind(IGlobalVariableManager.class).to(DummyGlobalVariableManager.class);
        bind(TaskSupervisor.class).to(DummyTaskSupervisor.class);
        bind(SelfReference.class).to(CommonFunctions.class);

        bind(IExceptionHandle.class).to(ExceptionHandle.class);

        // AreaTriggerManager start
        install(new APISupportModule());
        install(new DummyAreaTriggerModule());
        install((new FactoryModuleBuilder()).implement(IConfigSource.class, GsonConfigSource.class).build(IConfigSourceFactory.class));
        bind(ITriggerDependencyFacade.class).to(TriggerDependencyFacade.class);
        // AreaTriggerManager end

        bind(Key.get(new TypeLiteral<IBackedMapProvider<Executor>>() {})).to(DummyExecutorManager.class);
        bind(Key.get(new TypeLiteral<IBackedMapProvider<Placeholder>>() {})).to(DummyPlaceholderManager.class);
    }

}
