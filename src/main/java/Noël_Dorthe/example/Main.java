package Noël_Dorthe.example;


import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;


import javax.security.auth.login.LoginException;

public class Main  {
  public static JDA jda;

  //Main method
  public static void main(String[] args) throws LoginException {
    //ehitab uue Boti -- Token tuleb ise lisada
    JDA jda= JDABuilder.createDefault("").build();


  jda.getPresence().setStatus(OnlineStatus.IDLE);
  //jda.getPresence().setGame(Game.watching("hi!"));
  }
}