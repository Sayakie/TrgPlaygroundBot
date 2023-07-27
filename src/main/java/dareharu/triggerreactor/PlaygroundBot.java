package dareharu.triggerreactor;

import dareharu.triggerreactor.command.InterpreterCommand;
import dareharu.triggerreactor.command.LexerCommand;
import dareharu.triggerreactor.command.ParserCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.time.Duration;

public final class PlaygroundBot {

    private final JDA jda;

    public PlaygroundBot(final String token) throws InterruptedException {
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

    public JDA jda() {
        return this.jda;
    }

    public void release() throws InterruptedException {
        this.jda.shutdown();
        if (!this.jda.awaitShutdown(Duration.ofSeconds(10))) {
            this.jda.shutdownNow();
            this.jda.awaitShutdown();
        }
    }

}
