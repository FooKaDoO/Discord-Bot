package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MaifBott extends ListenerAdapter {
    public final String prefix = "maif";

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    public void onMessageReceived(MessageReceivedEvent event) {
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");
        if (args[0].equalsIgnoreCase(prefix)) {
            if(args[1].equalsIgnoreCase("kes")) {
                event.getChannel().sendMessage("Olen maifi bot").queue();
            }
            else if (args[1].equalsIgnoreCase(prefix + "delete")) {
                event.getChannel().deleteMessageById(event.getMessageId()).queue();
            }
        }
    }
}
