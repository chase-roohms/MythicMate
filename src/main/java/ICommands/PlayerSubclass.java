package ICommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Events.ICommand;
import ICommandsHelpers.QueryCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class PlayerSubclass implements ICommand {
    private final String name = "subclass";
    private final String description = "Look up a subclass by parent class and name.";


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
        List<OptionData> optionData = new ArrayList<>();
        optionData.add(new OptionData(OptionType.STRING, "parent-class",
                "The parent class of the subclass you want to look up", true)
                .addChoice("Artificer", "artificer")
                .addChoice("Barbarian", "barbarian")
                .addChoice("Bard", "bard")
                .addChoice("Blood Hunter", "blood-hunter")
                .addChoice("Cleric", "cleric")
                .addChoice("Druid", "druid")
                .addChoice("Fighter", "fighter")
                .addChoice("Monk", "monk")
                .addChoice("Paladin", "paladin")
                .addChoice("Ranger", "ranger")
                .addChoice("Rogue", "rogue")
                .addChoice("Sorcerer", "sorcerer")
                .addChoice("Warlock", "warlock")
                .addChoice("Wizard", "wizard"));
        optionData.add(new OptionData(OptionType.STRING, "lookup",
                "The subclass you would like to lookup", true, true));
        return optionData;
    }

    @Override
    public Runnable execute(SlashCommandInteractionEvent event) {
        String parentClass = Objects.requireNonNull(event.getOption("parent-class")).getAsString();
        String subClass = Objects.requireNonNull(event.getOption("lookup")).getAsString();
        String lookup = parentClass + " " +subClass;
        QueryCommand.query(event, lookup);
        return null;
    }
}
