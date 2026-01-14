package Events;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ICommandsHelpers.MultiThreader;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandManager extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        super();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Bot is ready! Registering commands...");
        //For every guild bot is in, add commands from bot to the guild
        for(Guild guild : event.getJDA().getGuilds()){
            logger.info("Registering commands for guild: {} (ID: {})", guild.getName(), guild.getId());
            for(ICommand command : commands){
                guild.upsertCommand(command.getName(), command.getDescription())
                        .addOptions(command.getOptions()).queue();
                logger.debug("Registered command: {}", command.getName());
            }
        }
        logger.info("Command registration complete. Total commands: {}", commands.size());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // Log incoming command with sanitized parameters
        String commandName = event.getName();
        String user = event.getUser().getName();
        String userId = event.getUser().getId();
        String guild = event.getGuild() != null ? event.getGuild().getName() : "DM";
        String guildId = event.getGuild() != null ? event.getGuild().getId() : "N/A";
        
        // Collect and sanitize options (mask passwords)
        String options = event.getOptions().stream()
                .map(option -> {
                    String optionName = option.getName();
                    String value;
                    if (optionName.equalsIgnoreCase("password")) {
                        value = "[REDACTED]"; // Mask password for security
                    } else {
                        value = option.getAsString();
                    }
                    return optionName + "=" + value;
                })
                .collect(Collectors.joining(", "));
        
        logger.info("Command received: '{}' from user: {} (ID: {}) in guild: {} (ID: {}) with options: {}",
                commandName, user, userId, guild, guildId, options.isEmpty() ? "none" : options);
        
        for(ICommand command : commands){
            if(command.getName().equalsIgnoreCase(event.getName())){
                MultiThreader thread = new MultiThreader(command, event);
                thread.start();
                return;
            }
        }
        
        logger.warn("Unknown command received: {}", commandName);
    }




    public void add(ICommand command){
        commands.add(command);
        logger.debug("Added command to manager: {}", command.getName());
    }
}
