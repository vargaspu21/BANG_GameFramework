package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;

/**
 * Requested action to local game to quit game. If granted, will exit application.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGQuitGame extends GameAction {
    private static final long serialVersionUID = -2242980258970485343L;

    public BANGQuitGame(GamePlayer player){
        super(player);
    }

}
