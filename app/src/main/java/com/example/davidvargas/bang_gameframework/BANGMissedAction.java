package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

public class BANGMissedAction extends GameAction {

    private static final long serialVersionUID = -2242980258970485343L;


    private boolean missedCard;

    public BANGMissedAction(GamePlayer player, boolean missedCard){
        super(player);
        this.missedCard = missedCard;
    }

    public boolean getMissedCard(){
        return missedCard;
    }
}
