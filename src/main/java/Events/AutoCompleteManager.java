package Events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Bot.DiscordBot;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

public class AutoCompleteManager extends ListenerAdapter {
    private final static String FEAT_OPTION_PATH = DiscordBot.ROOTDIR + "/database/FeatList.txt";
    private final static String SPELL_OPTION_PATH = DiscordBot.ROOTDIR + "/database/SpellList.txt";
    private final static String LINEAGE_OPTION_PATH = DiscordBot.ROOTDIR + "/database/LineageList.txt";
    private final static String SUBCLASS_OPTION_PATH = DiscordBot.ROOTDIR + "/database/SubclassList.txt";
    //private final static String classOptionPath = "database/ClassList.txt";

    //private static String[] classOptions;
    private static String[] featOptions;
    private static String[] spellOptions;
    private static String[] lineageOptions;
    private static HashMap<String, ArrayList<String>> subclassOptions;


    //Store each item in databse list as an option to query, and then call helper for subclasses
    public AutoCompleteManager() throws FileNotFoundException {
        //classOptions = initializeOptions(classOptionPath);
        featOptions = initializeOptions(FEAT_OPTION_PATH);
        spellOptions = initializeOptions(SPELL_OPTION_PATH);
        lineageOptions = initializeOptions(LINEAGE_OPTION_PATH);
        initializeSubclassOptions(SUBCLASS_OPTION_PATH);
    }

    //Add options to autocomplete manager
    private String[] initializeOptions(String path) throws FileNotFoundException {
        ArrayList<String> optionsArrayList = new ArrayList<>();
        File optionFile = new File(path);
        try (Scanner optionScanner = new Scanner(optionFile)) {
            while(optionScanner.hasNextLine()){
                String nextLine = optionScanner.nextLine();
                optionsArrayList.add(nextLine);    //Add each line (item) in database list file to the specified arrayList
            }
        }
        String[] optionList = new String[optionsArrayList.size()];
        optionsArrayList.toArray(optionList);
        return optionList;          //Return array list as array
    }

    private void initializeSubclassOptions(String path) throws FileNotFoundException {
        subclassOptions = new HashMap<>();      //Key will be parent class name, item will be array of subclass strings

        File optionFile = new File(path);
        try (Scanner optionScanner = new Scanner(optionFile)) {
            while(optionScanner.hasNextLine()){
                String nextLine = optionScanner.nextLine();
                if(nextLine.contains(":")){ //Barbarian: Path of the zealot etc etc
                    String key = nextLine.substring(0, nextLine.indexOf(":")).trim()
                            .toLowerCase().replace(" ", "-");
                    String optionToAdd = nextLine.substring(nextLine.indexOf(":")+1).trim();
                    if(subclassOptions.containsKey(key)){           //Existing class in hashmap
                        subclassOptions.get(key).add(optionToAdd);
                    }
                    else{                                           //Class isn't in hashmap
                        subclassOptions.put(key, new ArrayList<>());
                        subclassOptions.get(key).add(optionToAdd);
                    }
                }
            }
        }
    }

    private void mapOptions(CommandAutoCompleteInteractionEvent event, String[] commandOptions){
        //Get stream from the array, filtered by users current input
        Stream<String> s = Stream.of(commandOptions).filter(word -> word.toLowerCase().contains(
                event.getFocusedOption().getValue().toLowerCase()));
        //Map the first 25 (Discord caps number of options at 25) to options
        List<Command.Choice> options = s.map(word -> new Command.Choice(word, word)) // map the words to choices
                .limit(25).collect(Collectors.toList());
        event.replyChoices(options).queue();
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        //Depending on command, pull each autocomplete option suggestions
        if (event.getName().equals("spell") && event.getFocusedOption().getName().equals("lookup")) {
            mapOptions(event, spellOptions);
        } else if (event.getName().equals("feat") && event.getFocusedOption().getName().equals("lookup")) {
            mapOptions(event, featOptions);
        } else if (event.getName().equals("lineage") && event.getFocusedOption().getName().equals("lookup")) {
            mapOptions(event, lineageOptions);
        } else if (event.getName().equals("subclass") && event.getFocusedOption().getName().equals("lookup")) {
            ArrayList<String> subclassOptionArrayList;
            subclassOptionArrayList = subclassOptions.get(Objects.requireNonNull(event.getOption("parent-class"))
                    .getAsString().toLowerCase());
            String[] subclassOptionsArray = new String[subclassOptionArrayList.size()];
            subclassOptionArrayList.toArray(subclassOptionsArray);
            mapOptions(event, subclassOptionsArray);
        }
    }
}
