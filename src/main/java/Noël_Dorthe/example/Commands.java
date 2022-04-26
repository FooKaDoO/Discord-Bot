package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;


public class Commands extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event){
   String[] args = event.getMessage().getContentRaw().split(" ");

    if(args[0].equalsIgnoreCase("-" +"info")){
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("Try commands:\n"+
                "-GuessMeRadio\n" +
                "-maif\n"+
                "-mia").queue();
    }
   }
}
