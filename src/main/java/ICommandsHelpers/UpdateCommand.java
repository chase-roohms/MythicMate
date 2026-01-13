package ICommandsHelpers;

import java.io.IOException;
import java.util.Objects;

import Authenticate.Authenticate;
import Scrapers.WikiScraper;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/*WARNING: These commands cause heavy load on the wiki website, and on your own internet.   */
/*DO NOT USE if you do not know what you are doing. Password is kept in seperate file.      */
/*==========================================================================================*/

public class UpdateCommand {
    public static void update(SlashCommandInteractionEvent event) throws IOException {

        //Incorrect password
        String userPassword = Objects.requireNonNull(event.getOption("password")).getAsString();
        Authenticate auth = new Authenticate();
        if (!auth.login(userPassword)) {
            event.reply(
                    "INCORRECT PASSWORD. Please contact admin to request a manual database update")
                    .queue();
            return;
        }
        event.deferReply().queue();
        WikiScraper.scrape(event);
    }
}
