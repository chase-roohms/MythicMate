package ICommands;

import java.util.ArrayList;
import java.util.List;

import Database.CommandsDatabase;
import Events.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class CommandList implements ICommand {
    private final String name = "commands";
    private final String description = "List all available commands, and their descriptions!";
    private final List<OptionData> optionData = new ArrayList<>();


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public List<OptionData> getOptions() {
        return optionData;
    }

    @Override
    public Runnable execute(SlashCommandInteractionEvent event) {
        event.replyEmbeds(CommandsDatabase.getCommandEmbed().build()).queue();
        return null;
    }
}
