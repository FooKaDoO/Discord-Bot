package Noël_Dorthe.example;


import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


import javax.security.auth.login.LoginException;

public class Main  {
  public static JDA jda;
  public static  String prefix="-";
  //Main method
  public static void main(String[] args) throws LoginException {
    //ehitab uue Boti -- Token tuleb ise lisada
        JDA jda= JDABuilder.createDefault("").build();
    //
    //
    //    //boti staatus on eemal (IDLE)
      jda.getPresence().setStatus(OnlineStatus.ONLINE);
    //
    //  //kuvab, et eemal vatab telekat, võiv äravõtta
  jda.getPresence().setActivity(Activity.watching("TV"));

  jda.addEventListener(new Commands());
  jda.addEventListener(new GuessMeRadio());
  }
}