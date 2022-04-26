package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class GuessMeRadio extends ListenerAdapter {

    String prefix = "-";

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix+"GuessMeRadio")){
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage("Hi, GuessMeRadio is alive!").queue();
            musicGuessing(event);
        }
    }

    public void musicGuessing(MessageReceivedEvent event){
        sleepSek(2);
        event.getChannel().sendMessage("I will play you a serie of songs and you will have to guess the names of them.").queue();
        sleepSek(2);
        event.getChannel().sendMessage("If you want to guess the name of the song be sure to add '?' in front of Your guess.").queue();
        sleepSek(2);
        event.getChannel().sendMessage("Here we go!").queue();

    }

    //Meetod, et oleks lihtsam ja kiirem rakendada "sleep" funktiooni sekundites.
    public static void sleepSek(int n){
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
