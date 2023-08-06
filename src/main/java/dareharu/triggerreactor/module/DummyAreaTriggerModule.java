package dareharu.triggerreactor.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import dareharu.triggerreactor.util.Main;
import io.github.wysohn.triggerreactor.core.manager.trigger.area.AreaTrigger;
import io.github.wysohn.triggerreactor.core.manager.trigger.area.IAreaTriggerFactory;
import io.github.wysohn.triggerreactor.core.module.manager.trigger.AreaTriggerModule;

import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public final class DummyAreaTriggerModule extends AreaTriggerModule {

    private final File dataFolder;
    {
        final var rand = new Random();
        try {
            dataFolder = Files.createTempDirectory("TrgPlayground-" + rand.nextInt(999999)).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Provides
    @Named("DataFolder")
    public File provideDataFolder() {
        return dataFolder;
    }

}
