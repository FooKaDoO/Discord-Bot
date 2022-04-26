package Noël_Dorthe.example;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MaifBott extends ListenerAdapter {
    public final String prefix = "maif";

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    public void onMessageReceived(MessageReceivedEvent event) {
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");

        // Command prefix.
        if (args[0].equalsIgnoreCase(prefix)) {

            // If yahtzee command.
            if(args[1].equalsIgnoreCase("yahtzee")){
                // Start a new event listener for the game.
                List<Member> mangijad = event.getMessage().getMentionedMembers();
                mangijad.add(event.getMember());
                event.getJDA().addEventListener(new YahtzeeListener(mangijad));
            }
            // If no command.
            else if(args.length < 2)event.getChannel().sendMessage("Olen maifi yahtzee koopia.").queue();
        }
    }


}
