package com.example.davidvargas.bang_gameframework;

import android.util.Log;
import android.widget.TextView;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.LocalGame;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;
import com.example.davidvargas.bang_gameframework.objects.PlayableCard;

/**
 * Receives actions from all players, changes game state accordingly. Only allows for actions to affect state if within regulations.
 * Also contains criteria for victory, with game state to interpret.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGLocalGame extends LocalGame{

    public BANGState state; //the game state
    public TextView topText; //along with BANGHumanPlayer, will have ability to display text on very top of screen


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
        boolean sheriffDead = false;
        int numOutlawDead = 0;
        boolean renegadeDead = false;

        for (int i = 0; i < 4; i++) {
            if ((((BANGState) state).players[i].getHealth() <= 0) && ((BANGState) state).players[i].getRole().getRole() == BANGState.SHERIFF) {
                sheriffDead = true;
            } else if ((((BANGState) state).players[i].getHealth() <= 0) && ((BANGState) state).players[i].getRole().getRole() == BANGState.OUTLAW) {
                numOutlawDead++;
            } else if ((((BANGState) state).players[i].getHealth() <= 0) && ((BANGState) state).players[i].getRole().getRole() == BANGState.RENEGADE) {
                renegadeDead = true;
            }
        }
        if (sheriffDead && numOutlawDead < 2) {
            return "Sheriff is dead! Outlaws win!";
        } else if (sheriffDead && numOutlawDead >= 2) {
            return "Everyone is dead but renegade. Renegade wins!";
        } else if (numOutlawDead >= 2 && renegadeDead) {
            return "Everyone is dead but sheriff. Sheriff wins!";
        } else {
            return null;
        }

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
        int player = getPlayerNum(p);

        Log.i("Make move", "checking turn...");
        if(player != state.playerTurn) return false; //if it is not the player turn, return


        //if game state doesnt check, check if move is valid
        //finally, actually make action by changing game state

        Log.i("Make move", "checking action type...");
        if(action instanceof BANGEndTurn)
        { //if action is End Turn,
            state.setToTextView("Player " + (state.playerTurn+1) + "ends turn");
            state.endTurn(); //call endTurn action
            return true;
        }

        else if(action instanceof BANGQuitGame)
        { //if action is quit game,
            state.setToTextView("Quitting game...");
            state.quitGame(); //call quit game action
            return true;
        }
        else if(action instanceof  BANGMoveAction)
        {
            BANGMoveAction moveAction = (BANGMoveAction) action;
            int cardNum = moveAction.getCardNum();
            int target = moveAction.getTarget();
            Log.i("Make move", "Playing card "+cardNum+" with target "+target);
            state.setToTextView("Playing" + (new PlayableCard(false,cardNum)).getName());
            state.playCard(player, target, cardNum);
            return true;
        }
        else if(action instanceof BANGMissedAction)
        {
            BANGMissedAction missedAction = (BANGMissedAction) action;
            boolean missedCard = missedAction.getMissedCard();
            state.setToTextView("Missed card triggered!");
            state.setMissedCard(missedCard);
            return true;
        }

        // false, not valid
        return false;
    }

    public int getPlayerNum(GamePlayer p) {
        for (int i = 0; i < players.length; i++) {
            if (p == players[i]) {
                return i;
            }
        }
        return -1;
    }
}
