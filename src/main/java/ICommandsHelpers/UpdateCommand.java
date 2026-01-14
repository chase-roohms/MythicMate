package ICommandsHelpers;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Authenticate.Authenticate;
import Scrapers.WikiScraper;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/*WARNING: These commands cause heavy load on the wiki website, and on your own internet.   */
/*DO NOT USE if you do not know what you are doing. Password is kept in seperate file.      */
/*==========================================================================================*/

public class UpdateCommand {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCommand.class);
    public static void update(SlashCommandInteractionEvent event) throws IOException {
        String database = Objects.requireNonNull(event.getOption("database")).getAsString();
        String user = event.getUser().getName();
        String userId = event.getUser().getId();
        
        logger.info("Update command initiated by user: {} (ID: {}) for database: {}", user, userId, database);

        //Incorrect password
        String userPassword = Objects.requireNonNull(event.getOption("password")).getAsString();
        Authenticate auth = new Authenticate();
        if (!auth.login(userPassword)) {
            logger.warn("Failed authentication attempt for update command by user: {} (ID: {}) for database: {}",
                    user, userId, database);
            event.reply(
                    "INCORRECT PASSWORD. Please contact admin to request a manual database update")
                    .queue();
            return;
        }
        
        logger.info("Authentication successful for user: {} (ID: {}). Starting database update for: {}",
                user, userId, database);
        event.deferReply().queue();
        
        try {
            WikiScraper.scrape(event);
            logger.info("Database update completed successfully for: {}", database);
        } catch (IOException e) {
            logger.error("Database update failed for {} with error: {}", database, e.getMessage(), e);
            throw e;
        }
    }
}
