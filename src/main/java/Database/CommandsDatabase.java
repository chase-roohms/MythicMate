package Database;

import net.dv8tion.jda.api.EmbedBuilder;

public class CommandsDatabase {
    /*Full CommandPackage List                                                                         */
    /*==========================================================================================*/
    public final static String RULES = """
            - /conditions: View a list of DND 5e conditions, and their various effects.
            - /cover: Learn what constitutes as cover, and cover's various effects.
            - /damages: What damage types are there, and how often are they resisted?
            - /spell (spell name): Display info about a specific spell.
            - /feat (feat name): Display info about a specific feat.
            - /lineage (lineage name): Display info about a specific playable race.
            - /class (class name): Display info about a specific class.
            - /subclass (class name) (subclass name): Display info about a specific subclass.
            """;
    public final static String ROLLS = """
            Dice input example: d6 + 4d8 + 10
            - /roll (dice): Roll some dice, and add some modifiers.
            - /rolladv (dice): Roll with advantadge.
            - /rolldisadv (dice): Roll with disadvantadge.
            - /rollgwf (dice): Automatically drop 1's and 2's from your roll.
            - /rollempower (number) (dice): Automatically drop dice less than or equal to number.
            """;

    public static EmbedBuilder getCommandEmbed(){
        EmbedBuilder commandEmbed = new EmbedBuilder();
        commandEmbed.setTitle("Full Command List");

        commandEmbed.addField("DND5e Rule Check", RULES, false);
        commandEmbed.addField("Roll Commands", ROLLS, false);

        return commandEmbed;
    }

}
