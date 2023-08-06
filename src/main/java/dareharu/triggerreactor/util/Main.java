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
import java.util.Arrays;

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
        } else if (config.channels == null || config.channels.isEmpty()) {
            throw new IllegalStateException("Channels must be specified. Each channel can be split by ','.");
        }

        final var modules = new Module[]{ new DummyModule() };
        final var injector = Guice.createInjector(modules);

        bot = injector.getInstance(PlaygroundBot.class);
        bot.initBukkit();
        bot.initJda(config.token);

        if (args.length > 0) {
            System.out.println("Accepted arguments: " + Arrays.toString(args));

            switch (args[0]) {
                case "--register-commands" -> registerCommands(config);
                case "--register-commands-only" -> {
                    registerCommands(config);
                    bot.release();
                }
                case "--unregister-commands" -> unregisterCommands(config);
            }
        }
    }

    private static void registerCommands(final Config config) {
        final var commands = new ArrayList<SlashCommandData>();
        commands.add(Commands.slash("lexer", "Open a lexer playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED));
        commands.add(Commands.slash("parser", "Open a parser playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED)
                         .addOption(OptionType.BOOLEAN, "show-col-row", "Whether to show column and row"));
        commands.add(Commands.slash("interpreter", "Open a interpreter playground")
                         .setDefaultPermissions(DefaultMemberPermissions.DISABLED));

        final String[] channels = config.channels.split(",");

        for (final var channel$raw : channels) {
            final var channel = channel$raw.trim();

            final var guild = bot.jda().getGuildById(channel);
            if (guild == null) {
                System.out.println(String.format("Cannot find any guild by id %s", channel));
                return;
            }

            System.out.println(String.format("Adding all commands into guild %s", guild.getName()));
            guild.updateCommands().addCommands(commands).queue();
        }
    }

    private static void unregisterCommands(final Config config) {
        final String[] channels = config.channels.split(",");

        for (final var channel$raw : channels) {
            final var channel = channel$raw.trim();

            final var guild = bot.jda().getGuildById(channel);
            if (guild == null) {
                System.out.println(String.format("Cannot find any guild by id %s", channel));
                return;
            }

            System.out.println(String.format("Deleting all commands from guild %s", guild.getName()));
            final var commands = guild.retrieveCommands().complete();
            commands.forEach(command -> {
                guild.deleteCommandById(command.getId()).queue();

                System.out.println(String.format("Deleted command: %s", command.getFullCommandName()));
            });
        }
    }

}
