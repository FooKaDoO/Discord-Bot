package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;


public class MiaBot extends ListenerAdapter {
    public final String prefix = "-";
    private String[][] board = new String[7][6];

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");
        if (args[0].equalsIgnoreCase(prefix + "mia")) {
            event.getChannel().sendMessage("Olen mia bot").queue();
        }
    }
}
