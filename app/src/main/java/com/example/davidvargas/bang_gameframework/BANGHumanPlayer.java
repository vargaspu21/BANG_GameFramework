package com.example.davidvargas.bang_gameframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.davidvargas.bang_gameframework.game.GameHumanPlayer;
import com.example.davidvargas.bang_gameframework.game.GameMainActivity;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;

public class BANGHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    // the current activity
    private Activity myActivity;

    private Button quitGame = null;
    private Button endTurn = null;
    private ImageView cardImage = null;


    // the ID for the layout to use
    private int layoutId;

    /**
     * constructor
     *
     * @param name
     * 		the player's name
     * @param layoutId
     *      the id of the layout to use
     */
    public BANGHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {

        

        /*if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof BANGState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((BANGState)info);
            surfaceView.invalidate();
            Log.i("human player", "receiving");
        }*/
    }

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activitiy
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
       // surfaceView = (BANGSurfaceView)myActivity.findViewById(R.id.surfaceView);
        //Log.i("set listener","OnTouch");
        //surfaceView.setOnTouchListener(this);
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.mainLayout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("BANG: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch (which we'll detect on
     * the "up" movement" onto a tic-tac-tie square
     *
     * @param event
     * 		the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {

        //linked to all image views

        //on click functions for buttons
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        /*int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            BANGMoveAction action = new BANGMoveAction();
            Log.i("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }

        // register that we have handled the event*/
        return true;

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.endTurn){
            game.sendAction(new BANGEndTurn(this) );
        }
        else if(v.getId() == R.id.quit){
            game.sendAction(new BANGQuitGame(this));
        }
    }
}
