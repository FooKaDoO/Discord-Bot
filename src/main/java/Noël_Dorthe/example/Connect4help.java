package Noël_Dorthe.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class Connect4help{
    private static String[][] board;

    public Connect4help(String[][] board) {
        this.board = board;
    }

    public static String[][] getBoard() {
        return board;
    }
}
