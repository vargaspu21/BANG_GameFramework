package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GameComputerPlayer;
import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;

/**
 * Mainly contains AI for computer player, both dumb and smart. Decisions for computers are made here.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGComputerPlayer extends GameComputerPlayer {
    protected int playerNum;




    //constructor for the BANGComputerPlayer class:
    public BANGComputerPlayer(String name){
        super(name); //invoke superclass constructor

    }


    public int getPlayerNum(){
        return this.playerNum;
    }

    public void setPlayerNum(int playerNum){
        this.playerNum = playerNum;
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
            int player = ((BANGLocalGame)game).getPlayerNum(this);
            if(state.playerTurn != player){
                return;
            }
            sleep(1000);
            //TODO: get rid of this line, and uncomment
           game.sendAction(new BANGEndTurn(this));

            int cardNum = state.BANG;
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
                else target = player - 1;
            }
            if(game instanceof BANGLocalGame){ //if the game is instance of the local game
                sleep(1000); //delay for a second to make opponent think we're thinking
                game.sendAction(new BANGMoveAction(this, target, cardNum)); //sends game action
            }
        }

    }
}
