package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

public class BANGEndTurn extends GameAction {
    private static final long serialVersionUID = -2242980258970485343L;

    public BANGEndTurn(GamePlayer player){
        super(player);
    }
}
