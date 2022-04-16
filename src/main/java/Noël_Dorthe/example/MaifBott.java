package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MaifBott extends ListenerAdapter {
    public final String prefix = "-";

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    public void onMessageReceived(MessageReceivedEvent event) {
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");
        if (args[0].equalsIgnoreCase(prefix + "maif")) {
            event.getChannel().sendMessage("Olen maifi bot").queue();
        }
        if (args[0].equalsIgnoreCase(prefix + "delete")) {
            event.getChannel().deleteMessageById(event.getMessageId()).queue();
        }
    }
}
