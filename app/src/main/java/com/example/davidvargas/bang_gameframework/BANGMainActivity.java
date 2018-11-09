package com.example.davidvargas.bang_gameframework;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.davidvargas.bang_gameframework.game.config.GameConfig;
import com.example.davidvargas.bang_gameframework.game.GameMainActivity;
import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.LocalGame;
import com.example.davidvargas.bang_gameframework.game.config.GamePlayerType;

import java.util.ArrayList;

public class BANGMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;

    /**
     * a tic-tac-toe game is for two players. The default is human vs. computer
     */
    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // human GUI
        playerTypes.add(new GamePlayerType("Local Human Player ") {
            public GamePlayer createPlayer(String name) {
                return new BANGHumanPlayer1(name, R.layout.activity_main);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new BANGComputerPlayer1(name);
            }
        });

        /*
        // smarter computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new BANGComputerPlayer2(name);
            }
        });
        */

        // Create a game configuration class for Tic-tac-toe
        GameConfig defaultConfig = new GameConfig(playerTypes, 4,4, "BANG!", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // human GUI
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        /*
        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI
        */

        //done!
        return defaultConfig;

    }//createDefaultConfig


    /**
     * createLocalGame
     *
     * Creates a new game that runs on the server tablet,
     *
     * @return a new, game-specific instance of a sub-class of the LocalGame
     *         class.
     */
    @Override
    public LocalGame createLocalGame() {
        return new BANGLocalGame();
    }
}
