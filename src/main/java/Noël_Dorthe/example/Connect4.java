package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;


public class Connect4 extends ListenerAdapter {
    public final String prefix = "-";

    private char white ='⚪';
    //private Emoji white = Emoji.fromUnicode("U+26AA");
    //🔴 🔴 '\uD83D\uDD34'


    /**
     *Makes the orginal all white gameboard
     */
    private char[][] createGameBoard() {
        char[][] board = new char[6][7];
        for (char[] row : board) {
            Arrays.fill(row,white);
        }
        return board;
    }
    private char[][] fill(char[][] board,char player) {

        for (char[] row : board) {
            //Arrays.fill(row,white);
        }
        return board;
    }

    private void printGameBoard(char[][] gameBoard,MessageReceivedEvent event) {
        String row = "";
        for (int i = 0; i < gameBoard.length; i++) {
            for (char c : gameBoard[i]) {
                row += c;
            }
            event.getChannel().sendMessage(row).queue();
            row ="";
        }
    }

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    public void onMessageReceived(MessageReceivedEvent event) {
        char[][] gameBoard = createGameBoard();
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");
        if (args[0].equalsIgnoreCase(prefix + "mia")) {
            //event.getChannel().sendMessage("Olen mia bot").queue();
            printGameBoard(gameBoard,event);
        }



    }




}
