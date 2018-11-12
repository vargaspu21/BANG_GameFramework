package com.example.davidvargas.bang_gameframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidvargas.bang_gameframework.game.GameHumanPlayer;
import com.example.davidvargas.bang_gameframework.game.GameMainActivity;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;

public class BANGHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    // the current activity
    private Activity myActivity;

    private TextView topText = null;

    private ImageView drawPile = null;
    private ImageView discardPile = null;

    private Button quitGame = null;
    private Button endTurn = null;

    private ImageView card1 = null;
    private ImageView card2 = null;
    private ImageView card3 = null;
    private ImageView card4 = null;
    private ImageView card5 = null;

    //private ImageView active1 = null;
    //private ImageView active2 = null;
    //private ImageView active3 = null;
    //private ImageView active4 = null;
    //private ImageView active5 = null;






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

        if(info instanceof BANGState){
            BANGState state = (BANGState)info;
            state.drawTwo(0);
            card1.setImageResource(R.drawable.bang_card);
            card2.setImageResource(R.drawable.bang_card);
        }

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

    @Override
    public void onClick(View v){
        GameAction gameAction; //intialize instance variable
        if(v == null) return;

        if(v.getId() == endTurn.getId()){ //if end turn button is clicked
            Log.i("ButtonInfo","Ending turn");
            BANGLocalGame convert = (BANGLocalGame)game;
            convert.state.endTurn();
            Log.i("ButtonInfo","It is now Player " + convert.state.playerTurn + "'s turn");
            topText.setText("It is now Player " + ((convert.state.playerTurn)+1) + "'s turn");
            topText.invalidate();
            sendInfo(convert.state);
            //gameAction = new BANGEndTurn(this);
            //game.sendAction(gameAction); //sends the EndTurn action
        }
        else if(v.getId() == quitGame.getId()){ //else if quit game button is clicked
            //this can work for now; there currently only exists one human player, and the only one who can quit the game
            Log.i("ButtonInfo","Quitting game");
            System.exit(0);
            //gameAction = new BANGQuitGame(this);
            //game.sendAction(gameAction); //sends the quitGame action
        }
        else if(v.getId() == drawPile.getId())
        {
            topText.setText("Number of cards in deck: " + ((BANGLocalGame)game).state.drawPile.size());
            topText.invalidate();
        }
        else if(v.getId() == card1.getId()||v.getId() == card2.getId())
        {
            ImageView caste = (ImageView)v;
            caste.setImageResource(R.drawable.card_flipped);
            caste.invalidate();
        }
    }

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activitiy
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        this.topText = (TextView) activity.findViewById(R.id.topText);

        //Initialize the widget reference member variables
        this.quitGame = (Button) activity.findViewById(R.id.quit);
        this.endTurn = (Button) activity.findViewById(R.id.endTurn);

        this.card1 = (ImageView) activity.findViewById(R.id.p1c1);
        this.card2 = (ImageView) activity.findViewById(R.id.p1c2);
        this.card3 = (ImageView) activity.findViewById(R.id.p1c3);
        this.card4 = (ImageView) activity.findViewById(R.id.p1c4);
        this.card5 = (ImageView) activity.findViewById(R.id.p1c5);

        this.drawPile = (ImageView) activity.findViewById(R.id.drawPile);
        drawPile.setOnClickListener(this);

        this.discardPile = (ImageView) activity.findViewById(R.id.discardPile);

        //this.active1 = (ImageView) activity.findViewById(R.id.p1a1);
        //this.active2 = (ImageView) activity.findViewById(R.id.p1a2);
        //this.active3 = (ImageView) activity.findViewById(R.id.p1a3);
        //this.active4 = (ImageView) activity.findViewById(R.id.p1a4);
        //this.active5 = (ImageView) activity.findViewById(R.id.p1a5);

        //Listen for button presses
        quitGame.setOnClickListener(this);
        endTurn.setOnClickListener(this);

        //Listen for image presses
        card1.setOnTouchListener(this);
        card2.setOnTouchListener(this);
        card3.setOnTouchListener(this);
        card4.setOnTouchListener(this);
        card5.setOnTouchListener(this);
        //active1.setOnTouchListener(this);
       // active2.setOnTouchListener(this);
       // active3.setOnTouchListener(this);
       // active4.setOnTouchListener(this);
       // active5.setOnTouchListener(this);


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


}
