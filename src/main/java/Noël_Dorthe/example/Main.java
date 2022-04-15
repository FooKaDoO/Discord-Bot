package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


import javax.security.auth.login.LoginException;

public class Main  {
  public static void main(String[] args) throws LoginException {
    //ehitab uue Boti -- Token tuleb ise lisada
      JDA jda= JDABuilder.createDefault("token").build();

      jda.getPresence().setStatus(OnlineStatus.IDLE);
      jda.getPresence().setActivity(Activity.watching("TV"));
      jda.addEventListener(new Commands());
      jda.addEventListener(new MaifBott());
      jda.addEventListener(new MiaBot());
      jda.addEventListener(new OttBot());
  }
}