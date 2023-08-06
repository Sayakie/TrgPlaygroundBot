package dareharu.triggerreactor.command;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.inventory.InventoryViewMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryViewMock;
import dareharu.triggerreactor.integration.bukkit.DummyServer;
import dareharu.triggerreactor.util.*;
import io.github.wysohn.triggerreactor.core.manager.location.SimpleLocation;
import io.github.wysohn.triggerreactor.core.script.interpreter.InterpreterBuilder;
import io.github.wysohn.triggerreactor.core.script.interpreter.InterpreterLocalContext;
import io.github.wysohn.triggerreactor.core.script.lexer.Lexer;
import io.github.wysohn.triggerreactor.core.script.parser.Node;
import io.github.wysohn.triggerreactor.core.script.parser.Parser;
import io.github.wysohn.triggerreactor.tools.timings.Timings;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class InterpreterCommand extends ListenerAdapter {

    private static final Random RANDOM = new Random();

    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        if (!"interpreter".equalsIgnoreCase(event.getName())) {
            return;
        }

        final var body = TextInput.create("content", "Content", TextInputStyle.PARAGRAPH)
            .setPlaceholder("Type TriggerReactor codes here...")
            .build();

        final var modal = Modal.create("interpreter", "Interpreter Playground")
            .addComponents(ActionRow.of(body))
            .build();

        event.replyModal(modal).queue();
    }

    public void onModalInteraction(final ModalInteractionEvent event) {
        if (!"interpreter".equalsIgnoreCase(event.getModalId())) {
            return;
        }

        final var content = event.getValue("content").getAsString();
        @Nullable Runnable cleanupTask = null;
        try (final var timing = Timings.LIMBO) {
            final var lexer = new Lexer(content, StandardCharsets.UTF_8);
            final var parser = new Parser(lexer);

            final var root = parser.parse(false);
            final var interpreter = InterpreterBuilder.start(Main.current().globalContext(), root).build();

            final var sb = new StringBuilder();
            final var localContext = new InterpreterLocalContext(timing);
            cleanupTask = initLocalContext(localContext, sb);
            interpreter.start(null, localContext);

            final var result = interpreter.result(localContext);
            final var output = sb.isEmpty()
                ? result != null
                    ? AnsiColorUtils.bukkitColorToAnsiColor(result.toString())
                    : "No output"
                : AnsiColorUtils.bukkitColorToAnsiColor(sb.substring(0, sb.length() - 1));

            event.reply("""
                            ## :inbox_tray: Input:
                            ```js
                            {content}
                            ```
                            ## :outbox_tray: Output:
                            ```ansi
                            {output}
                            ```
                            """.replace("{content}", content).replace("{output}", output)).queue();
        } catch (final Throwable e) {
            e.printStackTrace();

            event.reply("""
                            ## :inbox_tray: Input:
                            ```js
                            {content}
                            ```
                            ## :outbox_tray: Error:
                            ```ansi
                            \u001B[0;31m឵឵{cause}
                            ```
                            """.replace("{content}", content).replace("{cause}", ExceptionUtils.handleException(e))).queue();
        } finally {
            if (cleanupTask != null) {
                cleanupTask.run();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static Runnable initLocalContext(final InterpreterLocalContext localContext, final Object cause) {
        localContext.setTriggerCause(cause);

        final DummyServer server = (DummyServer) Main.current().server();
        final var player = new PlayerMock(server, "TriggerReactor", Constants.uniqueId());

        final var inventory = Bukkit.createInventory(null, 54);
        for (int i = 1; i < 54; i++) {
            inventory.setItem(i, new ItemStack(CachedConstants.materials[i], i));
        }

        player.openInventory(inventory);

        final var itemStack = new ItemStack(Material.PAPER);
        player.getInventory().setItemInMainHand(itemStack);
        server.addPlayer(player);

        final var event = new PlayerCommandPreprocessEvent(player, "/interpreter run-as triggerreacter");
        localContext.setVar("event", event);
        localContext.setVar("player", player);
        localContext.setVar("item", itemStack);
        localContext.setVar("inventory", inventory);
        localContext.setVar("trigger", "click");
        localContext.setVar("slot", RANDOM.nextInt(1, 55));
        localContext.setVar("from", new SimpleLocation("world", 0, 0, 0));
        localContext.setVar("to", new SimpleLocation("world", 256, 64, 256));
        localContext.setVar("command", "interpreter");
        localContext.setVar("args", new String[] {"run-as", "triggerreactor"});
        localContext.setVar("argslength", 2);

        return player::kick;
    }

    private static String serializeNode(final Node node, final boolean showColRow) {
        return serializeNode(node, showColRow, 0);
    }

    private static String serializeNode(final Node node, final boolean showColRow, final int depth) {
        final var s = showColRow
            ? node.toString()
            : "[type: " + node.getToken().getType().name() + ", value: '" + node.getToken().getValue() + "']";
        final var sb = new StringBuilder("  ".repeat(depth)).append(s).append("\n");

        for (final var child : node.getChildren()) {
            sb.append(serializeNode(child, showColRow, depth + 1));
        }

        return sb.toString();
    }

}
