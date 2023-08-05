package dareharu.triggerreactor.command;

import dareharu.triggerreactor.util.AnsiColorUtils;
import dareharu.triggerreactor.util.ExceptionUtils;
import dareharu.triggerreactor.util.Main;
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

import java.nio.charset.StandardCharsets;

public final class InterpreterCommand extends ListenerAdapter {

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
        try (final var timing = Timings.LIMBO) {
            final var lexer = new Lexer(content, StandardCharsets.UTF_8);
            final var parser = new Parser(lexer);

            final var root = parser.parse(false);
            final var interpreter = InterpreterBuilder.start(Main.current().globalContext(), root).build();

            final var sb = new StringBuilder();
            final var localContext = new InterpreterLocalContext(timing);
            localContext.setTriggerCause(sb);
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
        }
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
