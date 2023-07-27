package dareharu.triggerreactor.command;

import dareharu.triggerreactor.util.ExceptionUtils;
import io.github.wysohn.triggerreactor.core.script.Token;
import io.github.wysohn.triggerreactor.core.script.lexer.Lexer;
import io.github.wysohn.triggerreactor.core.script.lexer.LexerException;
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

public final class LexerCommand extends ListenerAdapter {

    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        if (!"lexer".equalsIgnoreCase(event.getName())) {
            return;
        }

        final var body = TextInput.create("content", "Content", TextInputStyle.PARAGRAPH)
            .setPlaceholder("Type TriggerReactor codes here...")
            .build();

        final var modal = Modal.create("lexer", "Lexer Playground")
            .addComponents(ActionRow.of(body))
            .build();

        event.replyModal(modal).queue();
    }

    public void onModalInteraction(final ModalInteractionEvent event) {
        if (!"lexer".equalsIgnoreCase(event.getModalId())) {
            return;
        }

        final var content = event.getValue("content").getAsString();
        try (final var t = Timings.LIMBO) {
            final var lexer = new Lexer(content, StandardCharsets.UTF_8);

            final var sb = new StringBuilder();
            {
                Token token = null;
                while ((token = lexer.getToken()) != null) {
                    sb.append("[").append(token.getType()).append("] ").append(token.getValue()).append("\n");
                }
            }

            event.reply("""
# Input:
```js
{content}
```

# Output:
```ml
{output}
```
""".replace("{content}", content).replace("{output}", sb.toString())).queue();
        } catch (final LexerException | IOException e) {
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

}
