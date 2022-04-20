package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;


public class Commands extends ListenerAdapter {

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
   public void onMessageReceived(MessageReceivedEvent event){
   String[] args = event.getMessage().getContentRaw().split(" ");

    if(args[0].equalsIgnoreCase(Main.prefix +"info")){
        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage("Hi, I'm alive").queue();
    }
   }
}
