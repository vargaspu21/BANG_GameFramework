package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

/**
 * Class that describes a general action from any card played. Sends action to local game to be interpreted.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGMoveAction extends GameAction {
    private static final long serialVersionUID = -2242980258970485343L;

    private int cardNum, target;

    /**
     * Constructor for TTTMoveAction
     *
     * @param source the player making the move
     * @param cardNum the number of the card being played
     */
    public BANGMoveAction(GamePlayer player, int target, int cardNum)
    {
        // invoke superclass constructor to set the player
        super(player);
        this.cardNum= cardNum;
        this.target = target;
    }
    //get the object's cardNum
    public int getCardNum(){ return cardNum; }
    public int getTarget(){ return target; }
}
