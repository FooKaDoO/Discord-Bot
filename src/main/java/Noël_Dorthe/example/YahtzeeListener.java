package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class YahtzeeListener extends ListenerAdapter {

    private List<User> mangijad = new ArrayList<>();
    private List<Integer> punktid;
    private int mangijateArv;

    private int currentMove;
    private int currentMember;
    private int currentRoll;

    private Random rd;
    private int[] taringud;

    private String prefix = "-maif";
    private JDA jda;

    /**
     * Constructor for the YahtzeeListener object which creates the yahtzee game based on the players.
     *
     * @param mangijad Given players.
     */
    public YahtzeeListener(List<User> mangijad, JDA jda) {
        this.jda = jda;
        this.mangijad.addAll(mangijad);
        this.punktid = new ArrayList<>();
        this.mangijateArv = mangijad.size();
        this.currentMove = 0;
        this.currentMember = 0;
        this.currentRoll = 0;
        this.rd = new Random();
        for (int i = 0; i < mangijateArv; i++)
            this.punktid.add(0);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().equals(mangijad.get(currentMember))) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args[0].equalsIgnoreCase(prefix)) {

                if (args.length > 1 && args[1].equalsIgnoreCase("roll")) {
                    veeretamine(event, args);
                } else if (args.length > 1 && args[1].equalsIgnoreCase("next")) {
                    stopVeeretamine(event);
                } else if (args.length > 1 && args[1].equalsIgnoreCase("leave")) {
                    leaveGame(event);
                } else if (args.length > 1 && args[1].equalsIgnoreCase("end")) {
                    currentMove = 13;
                }

            }

        }

        endCases(event);
    }

    /**
     * Makes player leave the game and removes the matching info.
     *
     * @param event The leaving message event, so the bot can reply and let the player know they left the game.
     */
    private void leaveGame(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Lahkusite mängust" + mangijad.get(currentMember).getAsMention()).queue();
        currentRoll = 0;
        punktid.remove(currentMember);
        mangijad.remove(currentMember);
        mangijateArv--;
    }

    /**
     * If player wants the turn to stop then this function is called.
     * <br>
     * This is done by making the current roll the final roll so no more rolls can be called.
     * <br>
     * Also points of the player are added based on the punkt(taringud) method.
     * @param event The message event.
     */
    private void stopVeeretamine(MessageReceivedEvent event) {
        currentRoll = 3;
        Integer punkts = punkt(taringud);
        event.getChannel().sendMessage("Saadi "+ punkts +" punkti").queue();
        punktid.set(currentMember, punktid.get(currentMember) + punkts);
        taringud = veereta(new String[] {} ,taringud, 0);
    }

    /**
     * Function which is called when the player chooses to roll a certain amount of dice.
     *
     * @param event The message event.
     * @param args  Event message content in string arguments format.
     */
    public void veeretamine(MessageReceivedEvent event, String[] args) {
        taringud = veereta(args, taringud, currentRoll);
        event.getChannel().sendMessage("Veeretasite:").queue();
        String taringudStr = "";
        for (int i : taringud) {
            taringudStr += " " + i;
        }
        event.getChannel().sendMessage(taringudStr).queue();
        currentRoll++;
    }

    /**
     * If each condition is met, they are reset and something else is incremented, read the code for further documentation.
     *
     * @param event The message event so the bot can reply.
     */
    public void endCases(MessageReceivedEvent event) {
        // If the 13th move is finished the game ends.
        if (currentMove >= 13) {
            int max = 0;
            for (int i = 0; i < punktid.size(); i++) {
                if (punktid.get(i) > punktid.get(max))
                    max = i;
            }
            event.getChannel().sendMessage("Mäng on läbi, võitja on " + mangijad.get(max).getAsMention() + " " + punktid.get(max) + " punktiga!").queue();
            // Removes this event listener
            jda.removeEventListener(this);
        }
        // In case the last player is reached, the program goes back to the first player and increments the current move.
        if (currentMember >= mangijateArv) {
            currentMember = 0;
            currentMove++;
            event.getChannel().sendMessage(currentMove + ". käik on läbi").queue();
            event.getChannel().sendMessage("Ootan " + mangijad.get(currentMember).getAsMention() + " käiku").queue();
            if (currentMove >= 13) {
                int max = 0;
                for (int i = 0; i < punktid.size(); i++) {
                    if (punktid.get(i) > punktid.get(max))
                        max = i;
                }
                event.getChannel().sendMessage("Mäng on läbi, võitja on " + mangijad.get(max).getAsMention() + " " + punktid.get(max) + " punktiga!").queue();
                // Removes this event listener
                jda.removeEventListener(this);
            }
        }
        // In case the max cap of rolls is reached, the rolls will be reset and the turn will be given over to the next player.
        if (currentRoll >= 3) {
            stopVeeretamine(event);
            currentRoll = 0;
            event.getChannel().sendMessage(mangijad.get(currentMember).getAsMention() + " on " + punktid.get(currentMember) + " punkti").queue();
            currentMember++;
            event.getChannel().sendMessage("Ootan " + mangijad.get(currentMember).getAsMention() + " käiku").queue();

            if (currentMember >= mangijateArv) {
                currentMember = 0;
                currentMove++;
                event.getChannel().sendMessage(currentMove + ". käik on läbi").queue();
                event.getChannel().sendMessage("Ootan " + mangijad.get(currentMember).getAsMention() + " käiku").queue();
                if (currentMove >= 13) {
                    int max = 0;
                    for (int i = 0; i < punktid.size(); i++) {
                        if (punktid.get(i) > punktid.get(max))
                            max = i;
                    }
                    event.getChannel().sendMessage("Mäng on läbi, võitja on " + mangijad.get(max).getAsMention() + " " + punktid.get(max) + " punktiga!").queue();
                    // Removes this event listener
                    jda.removeEventListener(this);
                }
            }
        }
    }

    /**
     * Rolls the dice for the player based on certain conditions, like is it first roll and what dice are wanted to be rolled.
     *
     * @param args        The command arguments.
     * @param taringud    The dice to be rolled.
     * @param currentRoll The roll (1.-3.) that currently is thrown.
     * @return The newly rolled dice.
     */
    public int[] veereta(String[] args, int[] taringud, int currentRoll) {
        // If first roll, roll all dice.
        if (currentRoll == 0) {
            taringud = new int[]{
                    rd.nextInt(1, 7),
                    rd.nextInt(1, 7),
                    rd.nextInt(1, 7),
                    rd.nextInt(1, 7),
                    rd.nextInt(1, 7)
            };
        }
        // Else if not last roll, roll chosen dice, unless not specified.
        else if (currentRoll < 3) {
            if (args.length < 3)
                taringud = new int[]{
                        rd.nextInt(1, 7),
                        rd.nextInt(1, 7),
                        rd.nextInt(1, 7),
                        rd.nextInt(1, 7),
                        rd.nextInt(1, 7)
                };
            else {
                List<Integer> whatToRoll = new ArrayList<>();
                for (int i = 2; i < args.length; i++) {
                    // Try-catch method for illegal arguments.
                    try {
                        whatToRoll.add(Integer.parseInt(args[i]));
                        System.out.println(args[i]);
                    } catch (NumberFormatException e) {
                    }
                }

                for (Integer integer : whatToRoll) {
                    taringud[integer-1] = rd.nextInt(1, 7);
                }
            }
        }
        return taringud;
    }

    /**
     * Function to calculate the points gotten from the given dice.
     *
     * @param taringud
     * @return The points.
     */
    public Integer punkt(int[] taringud) {

        List<Integer> taring = new ArrayList<>();
        for (int i : taringud) {
            taring.add(i);
        }

        // If there is a row of concurrent dice.
        if (
                taring.contains(6) &&
                        taring.contains(5) &&
                        taring.contains(4) &&
                        taring.contains(3) &&
                        taring.contains(2)
        )
            return 60;

        // If there is a row of concurrent dice.
        if (
                taring.contains(5) &&
                        taring.contains(4) &&
                        taring.contains(3) &&
                        taring.contains(2) &&
                        taring.contains(1)
        )
            return 60;

        // If there is 5 of some dice.
        for (int i = 1; i <= 6; i++) {
            if (Collections.frequency(taring, i) == 5)
                return 50;
        }
        // If there is 4 of some dice.
        for (int i = 1; i <= 6; i++) {
            if (Collections.frequency(taring, i) == 4)
                return 40;
        }
        // If there is 3 of some dice.
        for (int i = 1; i <= 6; i++) {
            if (Collections.frequency(taring, i) == 3)
                return 30;
        }

        // Else just return the sum of the dice.
        return Arrays.stream(taringud).sum();
    }

}
