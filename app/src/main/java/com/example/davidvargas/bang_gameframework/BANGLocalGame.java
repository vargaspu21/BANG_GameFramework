package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.LocalGame;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;

public class BANGLocalGame extends LocalGame{

    public BANGState state; //the game state


    public BANGLocalGame(){
        super();//perform superclass initialization
        state = new BANGState(); //create a new, unfilled-in BANGState object
    }

    /**
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        return "";

    }

    /**
     * Notify the given player that its state has changed. This should involve sending
     * a GameInfo object to the player. If the game is not a perfect-information game
     * this method should remove any information from the game that the player is not
     * allowed to know.
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        // make a copy of the state, and send it to the player
        p.sendInfo(new BANGState(state));

    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     * 		true iff the player is allowed to move
     */
    protected boolean canMove(int playerIdx) {
        return playerIdx == state.playerTurn;
    }

    /**
     * Makes a move on behalf of a player.
     * @param action
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        //check that it is the correct player turn
        GamePlayer p = action.getPlayer(); //gets the player that sent the action
        int player = 0; //initialize player #
        for(int i = 0 ; i < players.length; i++){
            if(players[i] == p){
                player = i; //the player # is the index of the array where the player is positioned
            }
        }

        if(player != state.playerTurn) return false; //if it is not the player turn, return


        //if game state doesnt check, checj if move is valid
        //finally, actually make action by changing game state


        if(action instanceof BANGEndTurn){ //if action is End Turn,
            state.endTurn(); //call endTurn action
            return true;
        }

        else if(action instanceof BANGQuitGame){ //if action is quit game,
            state.quitGame(); //call quit game action
            return true;
        }
        else if(action instanceof  BANGMoveAction){
            BANGMoveAction moveAction = (BANGMoveAction) action;
            int cardNum = moveAction.getCardNum();
            int target = moveAction.getTarget();
            state.playCard(player, target, cardNum);
        }

        // false, not valid
        return false;
    }

}
