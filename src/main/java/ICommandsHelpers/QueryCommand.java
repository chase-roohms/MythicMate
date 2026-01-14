package ICommandsHelpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Bot.DiscordBot;
import Functions.MessageSender;
import Functions.SpellEmbedSender;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class QueryCommand {
    public static void query(SlashCommandInteractionEvent event, String lookup) {
        event.deferReply(true).queue();

        String commandName = event.getName();
        StringBuilder toSend = new StringBuilder();
        MessageChannelUnion chan = event.getChannel();

        //Remove any symbols, convert to lowercase, and add .txt extension
        String filename = lookup.replace(" ", "").replace("'", "")
                .replace(":", "").replace("-", "")
                .replace("/", "").toLowerCase() + ".txt";

        //Try to find and open the file
        try (Scanner details = new Scanner(new File(DiscordBot.ROOTDIR + "/database/" + commandName + "/" + filename))) {
            if(commandName.equalsIgnoreCase("spell")){
                event.getHook().sendMessageEmbeds(SpellEmbedSender.getSpellEmbed(details).build()).setEphemeral(true).queue();
            } else {
                event.getHook().sendMessage("Found it!").queue();
                MessageSender.sendMessage(chan, toSend, details);       //Send the scanner to message sender
            }
        } catch (FileNotFoundException e) {
            event.getHook().sendMessage("Hmm... I'm not seeing any " + commandName +" named: " +
                    lookup + ", check for spelling errors!").setEphemeral(true).queue();
        }
    }
}
