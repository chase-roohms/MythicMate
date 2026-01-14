package ICommandsHelpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Events.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class MultiThreader extends Thread{
    private static final Logger logger = LoggerFactory.getLogger(MultiThreader.class);
    private static ICommand command;
    private static SlashCommandInteractionEvent event;

    public MultiThreader(ICommand uCommand, SlashCommandInteractionEvent uEvent){
        command = uCommand;
        event = uEvent;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        String commandName = command.getName();
        logger.info("Executing command: {}", commandName);
        
        try {
            command.execute(event);
            long duration = System.currentTimeMillis() - startTime;
            logger.info("Command '{}' completed successfully in {}ms", commandName, duration);
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logger.error("Command '{}' failed after {}ms with error: {}", commandName, duration, e.getMessage(), e);
        }
    }
}
