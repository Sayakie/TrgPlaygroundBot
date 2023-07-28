package dareharu.triggerreactor.command;

import dareharu.triggerreactor.util.AnsiColorUtils;
import dareharu.triggerreactor.util.ExceptionUtils;
import dareharu.triggerreactor.util.PlaygroundInterpreterUtils;
import dareharu.triggerreactor.util.PlaygroundTaskSupervisor;
import io.github.wysohn.triggerreactor.core.manager.trigger.share.CommonFunctions;
import io.github.wysohn.triggerreactor.core.script.interpreter.Interpreter;
import io.github.wysohn.triggerreactor.core.script.interpreter.InterpreterException;
import io.github.wysohn.triggerreactor.core.script.lexer.Lexer;
import io.github.wysohn.triggerreactor.core.script.lexer.LexerException;
import io.github.wysohn.triggerreactor.core.script.parser.Node;
import io.github.wysohn.triggerreactor.core.script.parser.Parser;
import io.github.wysohn.triggerreactor.core.script.parser.ParserException;
import io.github.wysohn.triggerreactor.tools.timings.Timings;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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
        try (final var t = Timings.LIMBO) {
            final var lexer = new Lexer(content, StandardCharsets.UTF_8);
            final var parser = new Parser(lexer);

            final var root = parser.parse(false);
            final var interpreter = new Interpreter(root);
            interpreter.setExecutorMap(PlaygroundInterpreterUtils.getExecutors());
            interpreter.setPlaceholderMap(PlaygroundInterpreterUtils.getPlaceholders());
            interpreter.setSelfReference(new CommonFunctions());
            interpreter.setVars(new HashMap<>());
            interpreter.setTaskSupervisor(PlaygroundTaskSupervisor.INSTANCE);

            final var sb = new StringBuilder();
            interpreter.startWithContext(sb, t);

            final var output$raw = sb.isEmpty() ? "No output" : sb.toString();
            final var output = AnsiColorUtils.bukkitColorToAnsiColor(output$raw);

            event.reply("""
                            # Input:
                            ```js
                            {content}
                            ```

                            # Output:
                            ```ansi
                            {output}
                            ```
                            """.replace("{content}", content).replace("{output}", output)).queue();
        } catch (final InterpreterException | ParserException | LexerException | IOException e) {
            event.reply("""
                            # Input:
                            ```js
                            {content}
                            ```

                            # Error
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
