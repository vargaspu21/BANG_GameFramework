package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GameComputerPlayer;
import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;

public class BANGComputerPlayer extends GameComputerPlayer {

    //constructor for the BANGComputerPlayer class:
    public BANGComputerPlayer(String name){
        super(name); //invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        //for now, just constantly listen for your turn

        BANGState state;
        GameAction gameAction;
        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;


        if(info instanceof BANGState){ //if info is GameState info
            state = (BANGState) info; //cast info as BANGState


            GamePlayer p = this; //gets the player that sent the action
            int player = 0; //initialize player #
            for(int i = 0 ; i < state.players.length; i++){
                if(state.players[i] == p){
                    player = i; //the player # is the index of the array where the player is positioned
                }
            }
            int cardNum = 0;
            int number = state.players[player].getCardsInHand().size(); //gets size of players hand
            int random = (int )(Math.random() * number); //randomizes number between 0 and size of players hand
            if(number != 0){
                cardNum = state.players[player].getCardsInHand().get(random).getCardNum(); //gets the random card num
            }

            int target;
            int rand = (int) (Math.random()* 2) +1; //randomizes 1 or 2
            if(player == 3){ //if player 4, randomizes between attacking player 3 or 1
                if(rand == 1) target = 2;
                else target = 0;
            }
            else if(player == 0){ //if player 1, randomizes between attacking player 4 or 2
                if(rand == 1) target = 1;
                else target = 3;
            }
            else{ //if player 2 or 3, randomizes between attacking player 3 and 1 or 4 and 2, respectively.
                if(rand == 1) target = player+1;
                else target = player-1;
            }


            if(game instanceof BANGLocalGame){ //if the game is instance of the local game
                sleep(1000); //delay for a second to make opponent think we're thinking
                game.sendAction(new BANGMoveAction(this, target, cardNum)); //sends game action
            }


        }

        //randomly choose from current hand and play card here
        //get the size of the hand
        //randomize a number smaller than size
        //play random number card in the hand
        //  int cardNum = 0;


        // delay for a second to make opponent think we're thinking
        //sleep(1000);

        // Submit our move to the game object. We haven't even checked it it's
        // our turn, or that that position is unoccupied. If it was not our turn,
        // we'll get a message back that we'll ignore. If it was an illegal move,
        // we'll end up here again (and possibly again, and again). At some point,
        // we'll end up randomly pick a move that is legal.

        //fix this BANGMoveAction
         //game.sendAction(new BANGMoveAction(this, cardNum)); //card index in my hand


    }
}
