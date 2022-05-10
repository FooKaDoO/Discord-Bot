package Noël_Dorthe.example;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;

import java.util.Arrays;


public class Connect4 extends ListenerAdapter {
    public final String prefix = "-";
    private String[][] board = new String[6][7];
    private  String white ="⚪";
    private String yellow = "🟡";
    private String red = "\uD83D\uDD34";//ei tea kas see töötab??
    //private Emoji white = Emoji.fromUnicode("U+26AA");
    //🔴 🔴 '\uD83D\uDD34'


    /**
     *Makes the orginal all white gameboard
     */
    private String[][] createGameBoard() {
        //String[][] board = new char[6][7];
        for (String[] row : board) {
            Arrays.fill(row,white);
        }
        return board;
    }

    /**
     * createGameBoardAddColor:
     *
     * takes previous board and adds a color
     *
     * @param board
     * @param event
     * @param person
     * @return filled board
     */

    private String[][] createGameBoardAddColor(String[][] board, MessageReceivedEvent event, int index,int player) { // there should also be which player reacted, and then the color
        for (int i = board.length-1; i >= 0 ; i--) {
            if(board[i][index]==(white)&&player==1){
                if(player==1){
                    board[i][index] = yellow;///char at ei pruugi ka töötada
                    break;
                }
                board[i][index] = red;//see vigane
                break;
            }


        }

        return board;
    }




    private void printGameBoard(String[][] gameBoard, MessageReceivedEvent event) {
        Message msg = null;
        String row = "";
        for (int i = 0; i < gameBoard.length; i++) {
            for (String c : gameBoard[i]) {
                row += c;
            }
            msg = event.getChannel().sendMessage(row).complete();

            row ="";
        }
        msg.addReaction("1️⃣").queue();
        msg.addReaction("2️⃣").queue();
        msg.addReaction("3️⃣").queue();
        msg.addReaction("4️⃣").queue();
        msg.addReaction("5️⃣").queue();
        msg.addReaction("6️⃣").queue();
        msg.addReaction("7️⃣").queue();

    }

    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    public void onMessageReceived(MessageReceivedEvent event) {
        String[][] gameBoard = createGameBoard();
        String e = event.getMessage().getContentRaw();
        String[] args = e.split(" ");
        if (args[0].equalsIgnoreCase(prefix + "mia")) {
            //event.getChannel().sendMessage("Olen mia bot").queue();
            printGameBoard(gameBoard,event);
        }



    }




}
