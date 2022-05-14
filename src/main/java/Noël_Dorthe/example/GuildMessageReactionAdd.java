package Noël_Dorthe.example;
import discord4j.core.object.entity.Message;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class GuildMessageReactionAdd extends ListenerAdapter {
    public GuildMessageReactionAdd() {
    }
    public void onGuildMessageReactionAdd(MessageReactionAddEvent event) {
        System.out.println("kas kuulis");
        if (event.getReactionEmote().getName().equals("❌") ) {
            System.out.println("yey");

        }

    }
}

