package ICommands;

import java.util.ArrayList;
import java.util.List;

import Database.RulesDatabase;
import Events.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Damages implements ICommand {
    private final String name = "damages";
    private final String description = "View the different damages of DND5e, and how many creatures resist them!";
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
        event.replyEmbeds(RulesDatabase.getDamageEmbed().build()).setEphemeral(true).queue();
        return null;
    }
}
