package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Connect4help{
    private static String[][] board;
    private static String player1;
    private static String player2;
    private static int turns;

    public Connect4help(String[][] board) {
        this.board = board;
    }

    public static String[][] getBoard() {
        return board;
    }


    //Setterid
    public static void setPlayer1(String player1) {
        Connect4help.player1 = player1;
    }

    public static void setPlayer2(String player2) {
        Connect4help.player2 = player2;
    }

    public static void setTurns(int turns) {
        Connect4help.turns = turns;
    }

    //Getters

    public static String getPlayer1() {
        return player1;
    }

    public static String getPlayer2() {
        return player2;
    }

    public static int getTurns() {
        return turns;
    }
}
