package dareharu.triggerreactor.util;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.moandjiezana.toml.Toml;
import dareharu.triggerreactor.PlaygroundBot;
import dareharu.triggerreactor.config.Config;
import dareharu.triggerreactor.module.DummyModule;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main {

    public static final String TRIGGER_REACTOR_CHANNEL_ID = "400530500856578048";
    public static final String TEST_CHANNEL_ID = "740965554713657414";

    private static PlaygroundBot bot;

    public static PlaygroundBot current() {
        return bot;
    }

    public static void main(final String[] args) throws InterruptedException {
        final var file = new File("config.toml");
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new RuntimeException("Could not create config.toml file.");
                }
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }

        final var config = new Toml().read(file).to(Config.class);
        if (config.token == null || config.token.isEmpty()) {
            throw new IllegalStateException("Token must be specified.");
        }

        final var modules = new Module[]{ new DummyModule() };
        final var injector = Guice.createInjector(modules);

        bot = injector.getInstance(PlaygroundBot.class);
        bot.initJda(config.token);

        registerCommands();
        if (args.length == 1 && "--register-commands".equals(args[0])) {
            registerCommands();
            bot.release();
        }
    }

    private static void registerCommands() {

        final var commands = new ArrayList<SlashCommandData>();
        commands.add(Commands.slash("lexer", "Open a lexer playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED));
        commands.add(Commands.slash("parser", "Open a parser playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                         .addOption(OptionType.BOOLEAN, "show-col-row", "Whether to show column and row"));
        commands.add(Commands.slash("interpreter", "Open a interpreter playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED));

        {
            final var guild = bot.jda().getGuildById(TRIGGER_REACTOR_CHANNEL_ID);
            if (guild == null) {
                return;
            }
            guild.updateCommands().addCommands(commands).queue();
        }
        {
            final var guild = bot.jda().getGuildById(TEST_CHANNEL_ID);
            if (guild == null) {
                return;
            }
            guild.updateCommands().addCommands(commands).queue();
        }
    }

}
