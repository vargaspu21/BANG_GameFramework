package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GameComputerPlayer;
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

        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;



        //randomly choose from current hand and play card here
        //get the size of the hand
        //randomize a number smaller than size
        //play random number card in the hand
        int cardNum = 0;


        // delay for a second to make opponent think we're thinking
        sleep(1000);

        // Submit our move to the game object. We haven't even checked it it's
        // our turn, or that that position is unoccupied. If it was not our turn,
        // we'll get a message back that we'll ignore. If it was an illegal move,
        // we'll end up here again (and possibly again, and again). At some point,
        // we'll end up randomly pick a move that is legal.

        //fix this BANGMoveAction
        game.sendAction(new BANGMoveAction(this, cardNum)); //card index in my hand

    }
}
