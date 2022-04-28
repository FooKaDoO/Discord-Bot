package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


import javax.security.auth.login.LoginException;

public class Main {
    /**
     * Main method which starts the bot and adds command listeners. This is what is supposed to be run.
     *
     * @param args The app arguments, currently not in use, so these are to be ignored.
     * @throws LoginException Throws LoginException if token is invalid.
     */
    public static void main(String[] args) throws LoginException {
        // This is where the bot object is created. Into createDefault() goes the bot token.
        JDA jda = JDABuilder.createDefault("").build();

        // Bot is set to Idle and watching TV.
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching("TV"));
        // The info command listener.
        jda.addEventListener(new Commands());
        // Each of our commands' listeners.
        jda.addEventListener(new MaifBott(jda));
        jda.addEventListener(new MiaBot());
        jda.addEventListener(new GuessMeRadio());
    }
}