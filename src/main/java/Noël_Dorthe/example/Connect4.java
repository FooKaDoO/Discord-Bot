package Noël_Dorthe.example;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class Connect4 extends ListenerAdapter {
    private List<User> mangijad = new ArrayList<>();



    public final String prefix = "-";
    private String[][] board = new String[6][7];
    private boolean gameOver = false;

    private int count;
    private int index;
    private int player = 0;

    private  String white ="⚪";
    private String yellow = "🟡";
    private String red = "\uD83D\uDD34";//ei tea kas see töötab??
    //private Emoji white = Emoji.fromUnicode("U+26AA");
    //🔴 🔴 '\uD83D\uDD34'
    private JDA jda;

    public Connect4(List<User> mangijad,JDA jda) {
        this.jda = jda;

    } // TODO: Pane 'jda.removeEventListener(this);' sinna kus tahad mängu lõpetada.

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

    private String[][] createGameBoardAddColor(String[][] board, int index,int player) { // there should also be which player reacted, and then the color
        for (int i = board.length-1; i >= 0 ; i--) {
            if(board[i][index]==(white)){
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


    private static EmbedBuilder boardInServer(Guild guild,String[][] gameBoard){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#f1c232"));
        builder.setTitle("Connect4 game:");

        String row = "";
        for (int i = 0; i < gameBoard.length; i++) {
            for (String c : gameBoard[i]) {
                row += c;
            }
            row+= "\n";
        }
        builder.setDescription(row);

        return builder;
    }


     private void sendBoard(MessageReceivedEvent event, String[][] gameBoard){
         event.getChannel().sendMessageEmbeds(boardInServer(event.getGuild(), gameBoard).build()).queue(message -> {
             message.addReaction("1️⃣").queue();
             message.addReaction("2️⃣").queue();
             message.addReaction("3️⃣").queue();
             message.addReaction("4️⃣").queue();
             message.addReaction("5️⃣").queue();
             message.addReaction("6️⃣").queue();
             message.addReaction("7️⃣").queue();
         });
     }

     //soruce:https://github.com/JasonDykstra/Discord-Bot
    public String checkWinner() {

        //Check for horizontal win
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j += 2) {
                if((!board[i][j+1].equals(white))
                        && (!board[i][j+3].equals(white))
                        && (!board[i][j+5].equals(white))
                        && (!board[i][j+7].equals(white))
                        && ((board[i][j+1] == board[i][j+3])
                        && (board[i][j+3] == board[i][j+5])
                        && (board[i][j+5] == board[i][j+7]))) {
                    return board[i][j+1];
                }
            }
        }

        //Check for vertical win
        for(int i = 1; i < 15; i += 2) {
            for(int j = 0; j < 3; j++) {
                if((!board[j][i].equals(white))
                        && (!board[j+1][i].equals(white))
                        && (!board[j+2][i].equals(white))
                        && (!board[j+3][i].equals(white))
                        && ((board[j][i] == board[j+1][i])
                        && (board[j+1][i] == board[j+2][i])
                        && (board[j+2][i] == board[j+3][i]))) {
                    return board[j][i];
                }
            }
        }

        //Check for \ diagonal win
        for(int i = 0; i < 4; i++) {
            for(int j = 1; j < 9; j += 2) {
                if((!board[i][j].equals(white))
                        && (!board[i+1][j+2].equals(white))
                        && (!board[i+2][j+4].equals(white))
                        && (!board[i+3][j+6] .equals(white))
                        && ((board[i][j] == board[i+1][j+2])
                        && (board[i+1][j+2] == board[i+2][j+4])
                        && (board[i+2][j+4] == board[i+3][j+6])))
                    return board[i][j];

            }
        }

        //Check for / diagonal win
        for(int i = 0; i < 4; i++) {
            for(int j = 7; j < 15; j += 2) {
                if((!board[i][j].equals(white))
                        && (!board[i+1][j-2].equals(white))
                        && (!board[i+2][j-4].equals(white))
                        && (!board[i+3][j-6].equals(white))
                        && ((board[i][j] == board[i+1][j-2])
                        && (board[i+1][j-2] == board[i+2][j-4])
                        && (board[i+2][j-4] == board[i+3][j-6])))
                    return board[i][j];
            }
        }

        //if no winner is found:
        return null;

    }




    //igakord kui keegi serverisse kirjutab siis see klass registeerib selle
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {



        if(count==2){
            count = 2;
            sendBoard(event,board);
            count +=1;
        }
       if(count==0) {board = createGameBoard();
            sendBoard(event,board);
            count +=1;}
        if(!checkWinner().equals(null))
        jda.removeEventListener(this);

    }
    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event){
        if(event.getMember().getId().equals("882913776108699658")){
        if(event.getReactionEmote().getEmoji().equals("1️⃣")){
            System.out.println( event.getMember());
            board = createGameBoardAddColor(board,0,player);
            count +=1;
            System.out.println(Arrays.toString(board));
        }
        else if(event.getReactionEmote().getEmoji().equals("2️⃣")){
                System.out.println( event.getMember());}
        else if(event.getReactionEmote().getEmoji().equals("3️⃣")){
            System.out.println( event.getMember());}
        else if(event.getReactionEmote().getEmoji().equals("4️⃣")){
            System.out.println( event.getMember());}
        else if(event.getReactionEmote().getEmoji().equals("5️⃣")){
            System.out.println( event.getMember());}
        else if(event.getReactionEmote().getEmoji().equals("6️⃣")){
            System.out.println( event.getMember());}
        else if(event.getReactionEmote().getEmoji().equals("7️⃣")){
            System.out.println( event.getMember());
        System.out.println("lõppp");}
        }


    }
}





