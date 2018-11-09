package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

public class BANGMoveAction extends GameAction {
    private static final long serialVersionUID = -2242980258970485343L;

    private int cardNum;
    /**
     * Constructor for TTTMoveAction
     *
     * @param source the player making the move
     * @param cardNum the number of the card being played
     */
    public BANGMoveAction(GamePlayer player, int cardNum)
    {
        // invoke superclass constructor to set the player
        super(player);

        this.cardNum= cardNum;

    }

    //get the object's cardNum
    public int getCardNum(){ return cardNum; }

}
