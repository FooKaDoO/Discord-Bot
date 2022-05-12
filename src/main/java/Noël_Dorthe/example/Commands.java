package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Commands extends ListenerAdapter {

    private JDA jda;
    private String prefix = "-";

    public Commands(JDA jda) {
        this.jda = jda;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");

        if (args[0].equalsIgnoreCase("-" + "info")) {
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Try commands:\n" +
                    "-GuessMeRadio\n" +
                    "-maif\n" +
                    "-mia").queue();
        }
        // TODO: Tehke event listenerid nagu mina. Vaadake mu YahtzeeListeneri.
        else if (args[0].equalsIgnoreCase(prefix+"mia")) {
            if (args.length < 2) {
                connect4Rules(event); // TODO: Oma reeglid.
            }
            else if (args[1].equalsIgnoreCase("connect4")) {
                jda.addEventListener(new Connect4(jda));
            }
        }
        // TODO: Tehke event listenerid nagu mina. Vaadake mu YahtzeeListeneri.
        else if (args[0].equalsIgnoreCase(prefix+"GuessMeRadio")) {
            if (args.length < 2) {
                GuessMeRadioRules(event); // TODO: Oma reeglid.
            }
            else if (args[1].equalsIgnoreCase("start")) {
                jda.addEventListener(new GuessMeRadio(jda));
            }
        }
        else if (args[0].equalsIgnoreCase(prefix+"maif")) {
            if (args.length < 2) {
                yahtzeeRules(event);
            }
            else if (args[1].equalsIgnoreCase("yahtzee")) {
                yahtzeeGame(event);
            }
        }


    }


    /**
     * Starts a new event listener for the game based on the command parameters.
     *
     * @param event Message event with or without parameters.
     */
    private void yahtzeeGame(MessageReceivedEvent event) {
        List<User> mangijad = new ArrayList<>();
        mangijad.add(event.getAuthor());
        mangijad.addAll(event.getMessage().getMentionedUsers());
        event.getChannel().sendMessage("New Yahtzee game initiated.").queue();
        jda.addEventListener(new YahtzeeListener(mangijad, jda));
    }


    /**
     * Sends the Yahtzee rules of the game to the player.
     *
     * @param event The message event of the command.
     */
    private void yahtzeeRules(MessageReceivedEvent event) {
        event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(
                "  I am a Yahtzee Clone, with fake rules. \n" +
                        " To start a game, give the command [-maif yahtzee <other member> <other member> (etc.)]\n" +
                        " The game lasts 13 turns unless someone gives command -maif end. \n" +
                        "  Possible commands after -maif prefix are: \n" +
                        " roll <parameters> - without parameters rolls all dice, otherwise rolls the dice of which number is given, \n" +
                        "so 3 rolls the 3. dice and 2 5 rolls the 2. and 5. dice. \n" +
                        "Dice can be rolled up to 3 times. \n" +
                        " next - ends your turn and adds your points.\n" +
                        " leave - makes you leave the game.\n" +
                        " end -  ends the game and gives the final results.\n" +
                        "Game will also end, when all players leave."
        )).queue();
        event.getChannel().sendMessage("I sent the rules to you in your private messages.").queue();
    }

    /**
     * Sends the Connect4 rules of the game to the player.
     *
     * @param event The message event of the command.
     */
    private void connect4Rules(MessageReceivedEvent event) {
        event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(
                "  I am a Yahtzee Clone, with fake rules. \n" +
                        " To start a game, give the command [-maif yahtzee <other member> <other member> (etc.)]\n" +
                        " The game lasts 13 turns unless someone gives command -maif end. \n" +
                        "  Possible commands after -maif prefix are: \n" +
                        " roll <parameters> - without parameters rolls all dice, otherwise rolls the dice of which number is given, \n" +
                        "so 3 rolls the 3. dice and 2 5 rolls the 2. and 5. dice. \n" +
                        "Dice can be rolled up to 3 times. \n" +
                        " next - ends your turn and adds your points.\n" +
                        " leave - makes you leave the game.\n" +
                        " end -  ends the game and gives the final results.\n" +
                        "Game will also end, when all players leave."
        )).queue();
        event.getChannel().sendMessage("I sent the rules to you in your private messages.").queue();
    }

    /**
     * Sends the GuessMeRadio rules of the game to the player.
     *
     * @param event The message event of the command.
     */
    private void GuessMeRadioRules(MessageReceivedEvent event) {
        event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(
                "  I am a Yahtzee Clone, with fake rules. \n" +
                        " To start a game, give the command [-maif yahtzee <other member> <other member> (etc.)]\n" +
                        " The game lasts 13 turns unless someone gives command -maif end. \n" +
                        "  Possible commands after -maif prefix are: \n" +
                        " roll <parameters> - without parameters rolls all dice, otherwise rolls the dice of which number is given, \n" +
                        "so 3 rolls the 3. dice and 2 5 rolls the 2. and 5. dice. \n" +
                        "Dice can be rolled up to 3 times. \n" +
                        " next - ends your turn and adds your points.\n" +
                        " leave - makes you leave the game.\n" +
                        " end -  ends the game and gives the final results.\n" +
                        "Game will also end, when all players leave."
        )).queue();
        event.getChannel().sendMessage("I sent the rules to you in your private messages.").queue();
    }


}
