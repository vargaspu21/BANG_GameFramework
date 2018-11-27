package com.example.davidvargas.bang_gameframework;

import android.util.Log;

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

        /*
        //first, checks if sheriff is dead
        //counts how many players left
        int numPlayersLeft = 4;
        boolean sheriffDead = false;
        int numOutlawsDead = 0;

        //iterates entire player array in (casted) state
        for (int i = 0; i < 4; i++)
        {
            //if player is dead
            if(((BANGState)state).players[i].getHealth()<=0)
            {
                //marks if sheriff is dead
                if(((BANGState)state).players[i].getRole().getRole()==BANGState.SHERIFF)
                {
                    sheriffDead = true;
                }

                //counts number of outlaws dead
                if(((BANGState)state).players[i].getRole().getRole()==BANGState.OUTLAW)
                {
                    numOutlawsDead++;
                }
                //subtracts 1 onto players left if found dead
                numPlayersLeft--;
            }
            //by the end, number of players recorded

            //ASSUMES PLAYER IS RENDERED NOT ABLE TO ACT WHEN DEAD
            //THEREFORE, THE IF-ELSEIF CONDITION ORDER *SHOULD* NOT MATTER, SINCE IT SHOULD BE CHECKED PER PLAYER'S TURN

            //if sheriff is dead and numoutlawsdead < 2, an outlaw is still alive, and outlaws win
            if(sheriffDead&&(numOutlawsDead<2))
            {
                //victory for outlaw
                return "Outlaws win!";
            }
            //if last man standing is sheriff, sheriff wins
            else if(!sheriffDead&&(numPlayersLeft==1))
            {
                return "Sheriff wins!";
            }
            //if sheriff dead and 2 outlaws dead, last man standing MUST be renegade
            else if(sheriffDead&&(numOutlawsDead>=2)&&(numPlayersLeft==1))
            {
                return "Renegade wins!";
            }
            //if everyone died for some reason, stalemate
            else if(numPlayersLeft==0)
            {
                return "Stalemate: everyone is dead!";
            }
            else
            {
                return null;
            }
        }
        //supposed to be unreachable
        return null;
        */
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

        Log.i("Make move", "checking ation type...");
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
            Log.i("Make move", "Playing card "+cardNum+" with target "+target);
            state.playCard(player, target, cardNum);
            if(cardNum == state.BANG && state.getResponseRequired() != 0){
                state.playerTurn = target;
            }
            return true;
        }
        else if(action instanceof BANGMissedAction){
            BANGMissedAction missedAction = (BANGMissedAction) action;
            boolean missedCard = missedAction.getMissedCard();
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
