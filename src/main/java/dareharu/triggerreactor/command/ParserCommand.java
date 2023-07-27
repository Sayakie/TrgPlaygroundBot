package dareharu.triggerreactor.command;

import dareharu.triggerreactor.util.ExceptionUtils;
import io.github.wysohn.triggerreactor.core.script.Token;
import io.github.wysohn.triggerreactor.core.script.lexer.Lexer;
import io.github.wysohn.triggerreactor.core.script.lexer.LexerException;
import io.github.wysohn.triggerreactor.core.script.parser.Node;
import io.github.wysohn.triggerreactor.core.script.parser.Parser;
import io.github.wysohn.triggerreactor.core.script.parser.ParserException;
import io.github.wysohn.triggerreactor.tools.timings.Timings;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ParserCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        if (!"parser".equalsIgnoreCase(event.getName())) {
            return;
        }

        final var showColRow = event.getOption("show-col-row", OptionMapping::getAsBoolean);
        final var body = TextInput.create("content", "Content", TextInputStyle.PARAGRAPH)
            .setPlaceholder("Type TriggerReactor codes here...")
            .build();

        final var modalId = Boolean.TRUE.equals(showColRow) ? "parser-showColRow" : "parser";

        final var modal = Modal.create(modalId, "Parser Playground")
            .addComponents(ActionRow.of(body))
            .build();

        event.replyModal(modal).queue();
    }

    public void onModalInteraction(final ModalInteractionEvent event) {
        final boolean showColRow = "parser-showColRow".equalsIgnoreCase(event.getModalId());
        if (!(showColRow || "parser".equalsIgnoreCase(event.getModalId()))) {
            return;
        }

        final var content = event.getValue("content").getAsString();
        try (final var t = Timings.LIMBO) {
            final var lexer = new Lexer(content, StandardCharsets.UTF_8);
            final var parser = new Parser(lexer);

            final var root = parser.parse(false);
            final var serializedContent = serializeNode(root, showColRow);

            event.reply("""
# Input:
```js
{content}
```

# Output:
```q
{output}
```
""".replace("{content}", content).replace("{output}", serializedContent)).queue();
        } catch (final ParserException | LexerException | IOException e) {
            event.reply("""
# Input:
```js
{content}
```

# Error
```ahk
{cause}
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
