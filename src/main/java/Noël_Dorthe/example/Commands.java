package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
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
                    "-connect4").queue();
        }

        else if (args[0].equalsIgnoreCase(prefix+"connect4")) {
            if (args.length < 2) {
                connect4Rules(event);
            }
            else if (args[1].equalsIgnoreCase("new")) {
                connect4Game(event,0);
            }
            else if (args[1].equalsIgnoreCase("next")) {
                connect4Game(event,2);
            }
        }

        else if (args[0].equalsIgnoreCase(prefix+"GuessMeRadio")) {
            if (args.length < 2) {
                GuessMeRadioRules(event);
            }
            else if (args[1].equalsIgnoreCase("start")) {
                try {
                    GuessMeRadioGame(event);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
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
                " connect4Rules:\n " +
                        "Connect4 is usally played by two players.\n" +
                        "In oder to win you have to get 4 same color:diagonal win,vertical win,horizontal win\n "+
                        "Some commands{" +"\n" +"Starts new came :-connect4 new @Other player/or blank\n"+
                        "next move: -connect4 next}\n" + "In oder to choose where the ball will fall" +
                        " select the appropriate column number and then use NEXT COMMAND (-connect4 next)"

        )).queue();
        event.getChannel().sendMessage("I sent the rules to you in your private messages.").queue();
    }
    private void connect4Game(MessageReceivedEvent event,int count) {
        List<User> mangijad = new ArrayList<>();
        mangijad.add(event.getAuthor());
        mangijad.addAll(event.getMessage().getMentionedUsers());
        if(count==0){
            Connect4help.setTurns(0);
            Connect4help.setPlayer1(mangijad.get(0).getId());
            if(mangijad.size()==2)
            Connect4help.setPlayer2(mangijad.get(1).getId());
            else {event.getChannel().sendMessage("Playing alone or with too many members").queue();
                Connect4help.setPlayer2("--");
            }
        }
        if(Connect4help.getTurns()==0)
            event.getChannel().sendMessage("\uD83D\uDD34 - turn").queue();
        else if (Connect4help.getTurns()==1)
            event.getChannel().sendMessage("🟡- turn").queue();
        jda.addEventListener(new Connect4( jda,count));
    }

    /**
     * Sends the GuessMeRadio rules of the game to the player.
     *
     * @param event The message event of the command.
     */
    private void GuessMeRadioRules(MessageReceivedEvent event) {
        event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(
                "\n"+
                        "I am GuessMeRadio, a music bot with an integrated game function that has you guessing the names of the songs from your favourite playlist! \n" +
                        "\n"+
                        "To launch me, type '-GuessMeRadio start <link>' into the chat with a link of the playlist/song that you want to play. \n"+
                        "After that music starts playing! \n"+
                        "\n"+
                        "If you would also like to use the gaming function, then here are the commands that I understand: \n" +
                        "1) '-GuessMeRadio guess <guess>'. This is to guess the name of the song. I will also give you feedback on your answer :)\n" +
                        "2) '-GuessMeRadio stop'. Pretty self-explanatory. You stop the playing track.\n"+
                        "3) '-GuessMeRadio resume'. This is to resume the track that you previously stopped.\n"+
                        "4) '-GuessMeRadio play <link>'. This is in case you have had enough of the old playlist/song and want to play a new one.\n"+
                        "5) '-GuessMeRadio skip'. When you don't know or like the song, you can always skip it!\n"+
                        "6) '-GuessMeRadio leave'. Command in case you want to stop the music (and the game).\n"+
                        "\n"+
                        "Hope we are gonna have a great time together! :)"
        )).queue();
        event.getChannel().sendMessage("I sent the rules to you in your private messages.").queue();
    }

    private void GuessMeRadioGame(MessageReceivedEvent event) throws InterruptedException {
        GuessMeRadio GMR = new GuessMeRadio(jda);
        String[] args=event.getMessage().getContentRaw().split(" ", 3);
        if (GMR.musicGuessing(event, args)) {
            event.getChannel().sendMessage("New GuessMeRadio game initiated!").queue();
            jda.addEventListener(GMR);
        }
    }
}
