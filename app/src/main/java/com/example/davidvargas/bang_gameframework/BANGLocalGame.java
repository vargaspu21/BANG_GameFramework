package com.example.davidvargas.bang_gameframework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.LocalGame;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameOverAckAction;
import com.example.davidvargas.bang_gameframework.game.actionMsg.MyNameIsAction;
import com.example.davidvargas.bang_gameframework.game.actionMsg.ReadyAction;
import com.example.davidvargas.bang_gameframework.game.actionMsg.TimerAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.BindGameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameOverInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.StartGameInfo;
import com.example.davidvargas.bang_gameframework.game.util.GameTimer;

public class BANGLocalGame extends LocalGame {

    @Override
    protected boolean canMove(int playerIdx)
    {
        return true;
    }

    @Override
    protected String checkIfGameOver()
    {
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action)
    {
        return true;
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p)
    {
    }
}
