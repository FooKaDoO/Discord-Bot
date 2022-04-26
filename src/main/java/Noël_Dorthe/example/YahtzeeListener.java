package Noël_Dorthe.example;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class YahtzeeListener extends ListenerAdapter {

    private List<Member> mangijad;
    private List<Integer> punktid;
    private int mangijateArv;

    private int currentMove;
    private int currentMember;
    private int currentRoll;

    private Random rd;
    private int[] taringud;

    private String prefix = "-maif";

    /**
     * Constructor for the YahtzeeListener object which creates the yahtzee game based on the players.
     *
     * @param mangijad Given players.
     */
    public YahtzeeListener(List<Member> mangijad) {
        this.mangijad = mangijad;
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

        // If the user who sent message is making the move
        if (event.getMember() == mangijad.get(currentMember)) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args[0].equalsIgnoreCase(prefix)) {

                if (args.length > 1 && args[1].equalsIgnoreCase("roll")) {
                    veeretamine(event, args);
                } else if (args.length > 1 && args[1].equalsIgnoreCase("stop")) {
                    stopVeeretamine();
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
        event.getChannel().sendMessage("Lahkusite mängust" + mangijad.get(currentMember).getNickname()).queue();
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
     */
    private void stopVeeretamine() {
        currentRoll = 3;
        punktid.set(currentMember, punktid.get(currentMember) + punkt(taringud));
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
        for (int i : taringud) {
            event.getChannel().sendMessage(i + "").queue();
        }
        currentRoll++;
    }

    /**
     * If each condition is met, they are reset and something else is incremented, read the code for further documentation.
     *
     * @param event The message event so the bot can reply.
     */
    public void endCases(MessageReceivedEvent event) {
        // In case the max cap of rolls is reached, the rolls will be reset and the turn will be given over to the next player.
        if (currentRoll >= 3) {
            currentRoll = 0;
            event.getChannel().sendMessage(mangijad.get(currentMember).getNickname() + " sai " + punktid.get(currentMember) + " punkti").queue();
            currentMember++;
            event.getChannel().sendMessage("Ootan " + mangijad.get(currentMember).getNickname() + " käiku").queue();
        }
        // In case the last player is reached, the program goes back to the first player and increments the current move.
        if (currentMember >= mangijateArv) {
            currentMember = 0;
            currentMove++;
            event.getChannel().sendMessage(currentMove + ". käik on läbi").queue();
            event.getChannel().sendMessage("Ootan " + mangijad.get(currentMember).getNickname() + " käiku").queue();
        }
        // If the 13th move is finished the game ends.
        if (currentMove >= 13) {
            int max = 0;
            for (int i = 0; i < punktid.size(); i++) {
                if (punktid.get(i) > punktid.get(max))
                    max = i;
            }
            event.getChannel().sendMessage("Mäng on läbi, võitja on " + mangijad.get(max).getNickname() + " " + punktid.get(max) + " punktiga!").queue();
            // Removes this event listener
            event.getJDA().removeEventListener(this);
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
                    rd.nextInt(),
                    rd.nextInt(),
                    rd.nextInt(),
                    rd.nextInt(),
                    rd.nextInt()
            };
        }
        // Else if not last roll, roll chosen dice, unless not specified.
        else if (currentRoll < 3) {
            if (args.length < 3)
                taringud = new int[]{
                        rd.nextInt(),
                        rd.nextInt(),
                        rd.nextInt(),
                        rd.nextInt(),
                        rd.nextInt()
                };
            else {
                List<Integer> whatToRoll = new ArrayList<>();
                for (int i = 2; i < args.length; i++) {
                    // Try-catch method for illegal arguments.
                    try {
                        whatToRoll.add(Integer.parseInt(args[i]));
                    } catch (NumberFormatException e) {
                    }
                }

                for (Integer integer : whatToRoll) {
                    taringud[integer] = rd.nextInt();
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
