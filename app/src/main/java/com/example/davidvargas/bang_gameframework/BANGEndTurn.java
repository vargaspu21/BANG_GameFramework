package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

/**
 * Class of GameAction that players send to BANGLocalGame to request to end their turn.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 * @see BANGLocalGame
 */

public class BANGEndTurn extends GameAction {
    private static final long serialVersionUID = -2242980258970485343L;

    public BANGEndTurn(GamePlayer player){
        super(player);
    }
}
