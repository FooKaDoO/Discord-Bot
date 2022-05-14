package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main extends ListenerAdapter {
    /**
     * Main method which starts the bot and adds command listeners. This is what is supposed to be run.
     *
     * @param args The app arguments, currently not in use, so these are to be ignored.
     * @throws LoginException Throws LoginException if token is invalid.
     */
    public static void main(String[] args) throws LoginException, FileNotFoundException {
        // Pidin muutma main file et uuesti pushida
        // Bot token inside of text file.
        Scanner sc = new Scanner(new File("token.txt"));
        String token = sc.nextLine().strip();
        // This is where the bot object is created.
        JDA jda = JDABuilder.createDefault(token).build();

        // Pidin midagi lisama
        // Bot is set to Idle and watching TV.
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching("TV"));

        jda.addEventListener(new EventJoin());
        // The base commands listener.
        jda.addEventListener(new Main());
        jda.addEventListener(new Commands(jda));
    }

@Override
   public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event){
    System.out.println(event.getReactionEmote().getEmoji());
    if(event.getReactionEmote().getEmoji().equals("1️⃣")){

        System.out.println( event.getMember());
        System.out.println("sai kätte");}

   }

}