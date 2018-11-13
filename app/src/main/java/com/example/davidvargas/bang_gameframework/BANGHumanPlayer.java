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
import com.example.davidvargas.bang_gameframework.objects.PlayableCard;

import java.util.ArrayList;

public class BANGHumanPlayer extends GameHumanPlayer implements  View.OnClickListener {
    // the current activity
    private Activity myActivity;
    private TextView topText = null;
    private ImageView drawPile = null;
    private ImageView discardPile = null;
    private Button quitGame = null;
    private Button endTurn = null;
    private ArrayList<ImageView> handCards;
    private ArrayList<ArrayList<ImageView>> activeCards;
    private BANGState state;
    private int cardLastClicked;




    // the ID for the layout to use
    private int layoutId;

    //constructor
    public BANGHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }


     //Callback method, called when player gets a message
    @Override
    public void receiveInfo(GameInfo info) {

        if(info instanceof BANGState){
            this.state = (BANGState)info;

            //redraw all active cards, and player hand, discard, etc
            //go through gamestate and show correct things


            for(int i = 0; i < state.getPlayer(0).getCardsInHand().size(); i++){ //for cards in player's hand
                int Id = state.getPlayer(0).getCardsInHand().get(i).getResourceId(); //gets the Id for card in hand
                handCards.get(i).setImageResource(Id); //draws specific card in image view
                handCards.get(i).invalidate(); //invalidate each image view
            }
            for(int i = state.getPlayer(0).getCardsInHand().size(); i < handCards.size(); i++){ //for image views that are not cards
                handCards.get(i).setImageResource(R.drawable.card_flipped); // //set cards flipped
                handCards.get(i).invalidate(); //invalidate each image view
            }


        }

    }

    @Override
    public void onClick(View v){
        GameAction gameAction; //initialize instance variable
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
        else
        {
            int cardCliked = -1;
            for(int i = 0; i < handCards.size(); i++){
                if(v.getId() == handCards.get(i).getId()){
                    cardCliked = i;
                }
            }
            if(cardCliked != -1){
                int cardNum = state.getPlayer(0).getCardsInHand().get(cardCliked).getCardNum();
                game.sendAction(new BANGMoveAction(this, -1, cardNum));

                this.cardLastClicked = cardNum;

                //ImageView card = handCards.get(cardCliked);
                //card.setImageResource(R.drawable.card_flipped);
                //card.invalidate();
                //need to send action if card was valid
            }

        }
        //if target clicked
        {
            //send new game action withn target and this.cardLastClicked
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

        handCards = new ArrayList<>(); //
        activeCards = new ArrayList<>();
        for(int i = 0; i < 4; i++){ //iterates for each player (0)
            activeCards.add(new ArrayList<ImageView>()); //adds image view array list for each player
        }

        this.topText = (TextView) myActivity.findViewById(R.id.topText);

        //Initialize the widget reference member variables
        this.quitGame = (Button) myActivity.findViewById(R.id.quit);
        this.endTurn = (Button) myActivity.findViewById(R.id.endTurn);


        //for(int i =0; i<handCardsId.size(); i++){
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c1));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c2));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c3));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c4));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c5));




        this.drawPile = (ImageView) myActivity.findViewById(R.id.drawPile);
        drawPile.setOnClickListener(this);

        this.discardPile = (ImageView) myActivity.findViewById(R.id.discardPile);

        //TO-DO: make this like hand cards above
        //this.active1 = (ImageView) activity.findViewById(R.id.p1a1);
        //this.active2 = (ImageView) activity.findViewById(R.id.p1a2);
        //this.active3 = (ImageView) activity.findViewById(R.id.p1a3);
        //this.active4 = (ImageView) activity.findViewById(R.id.p1a4);
        //this.active5 = (ImageView) activity.findViewById(R.id.p1a5);

        //Listen for button presses
        quitGame.setOnClickListener(this);
        endTurn.setOnClickListener(this);

        //Listen for image presses
        for(ImageView v: this.handCards){
            v.setOnClickListener(this);
        }

        //active1.setOnTouchListener(this);
        // active2.setOnTouchListener(this);
        // active3.setOnTouchListener(this);
        // active4.setOnTouchListener(this);
        // active5.setOnTouchListener(this);

    }




}
