import java.io.*;
import java.util.*;

public class Solution {
static int calculate_bid(int player, int pos,int[] first_moves,int[] second_moves){
        //Initialize Variables
        int player1Money = 100;
        int player2Money = 100;
        int glassPosition = pos;
        int[] player2Moves = second_moves;
        boolean advantageDraw = true;

        //Loop to calculate money left by both players
        for(int i = 0; i < first_moves.length; i++){
            if (first_moves[i] > second_moves[i])
                player1Money -= first_moves[i];
            else if(first_moves[i] < second_moves[i])
                player2Money -= second_moves[i];
            else{
                if(advantageDraw == true){
                    player1Money -= first_moves[i];
                    advantageDraw = false;
                }else{
                    player2Money -= second_moves[i];
                    advantageDraw = true;
                }
            }
        }



        //If player equals 2, then we are player 2
        //Swaps values to correct orientation
        if(player == 2){
            int temp = player1Money;
            player1Money = player2Money;
            player2Money = temp;
            player2Moves = first_moves;
            glassPosition = 10 - pos;
        }

        if(first_moves.length < 3)  //Standard bet at beginning of match
            return 15;    
        else if(first_moves.length >= 3){       //After stardard betting phase
            int moneyDifference = player1Money - player2Money;
            if(glassPosition == 9)  //Make sure opponent does not win
                return Math.min(player1Money, player2Money + 1);
            else if(glassPosition == 1) //Need to win one more round. Go all in.
                return player1Money;
            else if((player2Money + 1 * glassPosition) < player1Money)  //Guaranteed win situation
               return Math.max(player1Money/glassPosition, 1);
            else if(BettingSteady(player2Moves) == true)    //Outbid opponent if consistanly betting
                return player2Moves[2] + 1;
            else if(BettingPattern(player2Moves) == true) //Determine another pattern of opponent  
                return Math.max(player2Moves[player2Moves.length - 1] - 1, 1);  
            else
                return Math.max(player2Money/glassPosition, 1); //Match is lost of this point in reached
        }
        return 5;   //Mandatory return
    }


    //Checks if opponent is bidding same amount consistantly 
    static boolean BettingSteady(int[] second_moves){
        boolean steady = true;
        for (int i = 0; i < second_moves.length - 1; ++i){
            if(second_moves[i] != second_moves[i + 1])
                steady = false;
        }
        return steady;
    }


    //Checks for a specific type of bidding pattern
    static boolean BettingPattern(int[] second_moves){
        boolean pattern = true;
        int last = second_moves.length - 1;
        if(second_moves[last - 2] > second_moves[last - 1] && second_moves[last - 1] > second_moves[last])
            pattern = true;
        return pattern;
    }
    
public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int player = in.nextInt();                     //1 if first player 2 if second
        int scotch_pos = in.nextInt();                 //position of the scotch
        int bid = 0;                                   //Amount bid by the player
        in.useDelimiter("\n");
        String first_move = in.next();
        String[] move_1 = first_move.split(" ");
        String second_move = in.next();
        String[] move_2= second_move.split(" ");
        int[]first_moves = new int[move_1.length];
        int[] second_moves = new int[move_2.length];
        if(first_move.equals("") == false) {
            for (int i=0;i<move_1.length;i++) {
                first_moves[i] = Integer.parseInt(move_1[i]);
                second_moves[i] = Integer.parseInt(move_2[i]);
            }
        }
        bid = calculate_bid(player,scotch_pos,first_moves,second_moves);
        System.out.print(bid);
    }
}
