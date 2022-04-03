package org.example;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

/**
 * Boti loomis objekt, loob boti selle tokeniga.
 * Siin saab edasi muuta seda
 */
public class Noel {
  private JDA jda;

  /**
   * Boti konstruktor
   * @param token
   */
  public Noel(String token) {
    this.jda = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
  }

}