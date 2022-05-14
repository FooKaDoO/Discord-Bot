package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class onReactionMessageReceieved extends ListenerAdapter {


    public void onReactionMessageReceieved(@NotNull GenericMessageReactionEvent event){
        System.out.println("ei tuvastanud veel 1");
    switch (event.getReactionEmote().getEmoji() ){
        case "1️⃣":
        event.getChannel().sendMessage("Tuvastasin 1");
        System.out.println("jõudis siia");
        break;
    }


}
}
