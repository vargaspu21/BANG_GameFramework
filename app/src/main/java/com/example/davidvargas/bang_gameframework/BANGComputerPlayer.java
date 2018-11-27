package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GameComputerPlayer;
import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;

import java.util.Random;

/**
 * Mainly contains AI for computer player, both dumb and smart. Decisions for computers are made here.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGComputerPlayer extends GameComputerPlayer
{
    protected int playerNum;

    //constructor for the BANGComputerPlayer class:
    public BANGComputerPlayer(String name)
    {
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
    protected void receiveInfo(GameInfo info)
    {
        //for now, just constantly listen for your turn
        BANGState state;

        // if it was a "not your turn" message, just ignore it
        if (info instanceof NotYourTurnInfo) return;

        //if info is GameState info
        if(info instanceof BANGState)
        {
            //cast info as BANGState
            state = (BANGState) info;
            int player = ((BANGLocalGame)game).getPlayerNum(this);
            if(state.playerTurn != player)
                return;
            sleep(1000);
           //game.sendAction(new BANGEndTurn(this));


            Random rand = new Random();
            int size = state.players[player].getCardsInHand().size();
            int random = rand.nextInt(size);
            if(size == 0)
                game.sendAction(new BANGEndTurn(this));
            int cardNum = state.players[player].getCardsInHand().get(random).getCardNum();
            int target = 0;
            int targetRandom = rand.nextInt(2);
            switch(player){
                case 1:
                    if(targetRandom == 1)
                        target = 2;
                    else
                        target = 0;
                    break;
                case 2:
                    if(targetRandom == 1)
                        target = 3;
                    else
                        target = 1;
                    break;
                case 3:
                    if(targetRandom == 1)
                        target = 0;
                    else
                        target = 2;
                    break;
            }
            game.sendAction(new BANGMoveAction(this, target, cardNum ));
            game.sendAction(new BANGEndTurn(this));

            /*
            //if the game is instance of the local game
            if(game instanceof BANGLocalGame)
            {
                //delay for a second to make opponent think we're thinking
                sleep(1000);

                //sends game action
                game.sendAction(new BANGMoveAction(this, target, cardNum));
            }
            */
        }
    }
}
