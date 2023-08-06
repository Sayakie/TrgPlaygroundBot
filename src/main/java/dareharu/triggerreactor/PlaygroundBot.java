package dareharu.triggerreactor;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dareharu.triggerreactor.command.InterpreterCommand;
import dareharu.triggerreactor.command.LexerCommand;
import dareharu.triggerreactor.command.ParserCommand;
import dareharu.triggerreactor.integration.bukkit.DummyServer;
import io.github.wysohn.triggerreactor.core.main.IGameManagement;
import io.github.wysohn.triggerreactor.core.main.IPluginManagement;
import io.github.wysohn.triggerreactor.core.script.interpreter.InterpreterGlobalContext;
import io.github.wysohn.triggerreactor.core.script.interpreter.TaskSupervisor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import java.time.Duration;

@Singleton
public final class PlaygroundBot {

    @Inject
    private Server server;

    @Inject
    private TaskSupervisor taskSupervisor;

    @Inject
    private IPluginManagement pluginManagement;

    @Inject
    private IGameManagement gameManagement;

    @Inject
    private InterpreterGlobalContext globalContext;

    private Plugin plugin;

    private JDA jda;

    public void initBukkit() {
        final DummyServer server = (DummyServer) server();

        plugin = MockBukkit.createMockPlugin("TriggerReactor");

        // # World
        server.addSimpleWorld("world");
        server.addSimpleWorld("world_the_nether");
        server.addSimpleWorld("world_the_end");

        // # Player
        server.addPlayer("wysohn");
        server.addPlayer("Sayakie");
    }

    public void initJda(final String token) throws InterruptedException {
        if (this.jda != null) {
            throw new IllegalStateException("JDA is already initialized");
        }

        this.jda = JDABuilder.createLight(token)
            .enableIntents(GatewayIntent.GUILD_MESSAGES)
            .addEventListeners(
                new LexerCommand(),
                new ParserCommand(),
                new InterpreterCommand()
            )
            .setAutoReconnect(true)
            .build();

        this.jda.awaitReady();
    }

    public Server server() {
        return server;
    }

    public TaskSupervisor taskSupervisor() {
        return taskSupervisor;
    }

    public InterpreterGlobalContext globalContext() {
        return globalContext;
    }

    public JDA jda() {
        return jda;
    }

    public Plugin plugin() {
        return plugin;
    }

    public void release() throws InterruptedException {
        this.jda.shutdown();
        if (!this.jda.awaitShutdown(Duration.ofSeconds(10))) {
            this.jda.shutdownNow();
            this.jda.awaitShutdown();
        }
    }

    @Override
    public String toString() {
        return "Playground";
    }

}
