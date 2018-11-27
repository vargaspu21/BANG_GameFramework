package com.example.davidvargas.bang_gameframework;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.media.Image;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidvargas.bang_gameframework.game.GameHumanPlayer;
import com.example.davidvargas.bang_gameframework.game.GameMainActivity;
import com.example.davidvargas.bang_gameframework.game.GamePlayer;
import com.example.davidvargas.bang_gameframework.game.actionMsg.GameAction;
import com.example.davidvargas.bang_gameframework.game.infoMsg.GameInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.IllegalMoveInfo;
import com.example.davidvargas.bang_gameframework.game.infoMsg.NotYourTurnInfo;
import com.example.davidvargas.bang_gameframework.objects.PlayableCard;

import java.util.ArrayList;

/**
 * Main player as well as containing the main layout when playing. Actions are sent from this class.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGHumanPlayer extends GameHumanPlayer implements  View.OnClickListener {
    // the current activity
    private Activity myActivity;
    private TextView topText = null;
    private ImageView drawPile = null;
    private ImageView discardPile = null;

    private ImageView player2cards = null;
    private ImageView player3cards = null;
    private ImageView player4cards = null;

    private Button quitGame = null;
    private Button endTurn = null;
    private Button chooseTarget = null;
    private ArrayList<ImageView> handCards;
    private ArrayList<ArrayList<ImageView>> activeCards;
    private ArrayList<ArrayList<ImageView>> health;
    private ArrayList<ImageView> roles;
    private BANGState state;
    private int cardLastClicked;
    private static final int MAXHAND = 8;
    private LinearLayout player2, player3, player4;
    private ArrayList<TextView> playerTexts;
    private Button missed = null;
    private int target;
    private GamePlayer player = this;
    private ArrayList<LinearLayout> playerLayouts;
    // the ID for the layout to use
    private int layoutId;


    //constructor
    public BANGHumanPlayer(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    //method that initializes the GUI for the human player:
    private void render(){
        int discard = -1;

        //demonstrates the last card added to the discard pile on discard pile:
        if(state.discardPile.size() != 0) {
            discard = state.discardPile.get(state.discardPile.size() - 1).getResourceId(); //the last card added to discard pile
            discardPile.setImageResource(discard); //draws the last card played on discard pile
        }

        //if a player is dead, sets their role card and layout as invisible, and sets text:
        for(int i = 0; i<4; i++){
            if(state.players[i].getHealth() <= 0){
                roles.get(i).setVisibility(View.INVISIBLE);
                roles.get(i).invalidate();
                playerTexts.get(i-1).setText("Player " + String.valueOf(i+1)+" is dead!\n");
                playerLayouts.get(i-1).setVisibility(View.INVISIBLE);
                playerLayouts.get(i-1).invalidate();
            }
        }

        //only demonstratest the role card for sheriff:
        for(int i = 0; i<4; i++) {
            if (state.players[i].getRole().getRole() == state.SHERIFF) {
                roles.get(i).setImageResource(R.drawable.sheriff_copy);
                roles.get(i).invalidate();
            }
        }

        //demonstrates role card for human player:
        if(state.players[0].getRole().getRole() == state.OUTLAW) {
            roles.get(0).setImageResource(R.drawable.outlaw);
            roles.get(0).invalidate();
        }
        else if(state.players[0].getRole().getRole() == state.RENEGADE){
            roles.get(0).setImageResource(R.drawable.renegade);
            roles.get(0).invalidate();
        }

        //sets all of the health image views to the bullet image.
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                health.get(i).get(j).setImageResource(R.drawable.bullet);
                health.get(i).get(j).setVisibility(View.VISIBLE);
                health.get(i).get(j).invalidate();
            }
        }

        //sets the bullets that are "extra" to invisible:
        for(int i = 0; i<4; i++){
            int bullet = state.players[i].getHealth();
            if(bullet < 0) break;
            for(int j = bullet; j<5; j++) { //set the difference of the player health and 4 to an invisible bullet
                health.get(i).get(j).setVisibility(View.INVISIBLE);
                health.get(i).get(j).invalidate();
            }
        }

        //do i start all of the healths at 4 even though character initializes the health?
        //where are the individual characters initialized?
        //how are the roles initialized?
        //redraw all active cards, and player hand, discard, etc
        //go through gamestate and show correct things

        Log.i("render","Player 0 has " + state.getPlayer(0).getCardsInHand().size()+" cards");

        //draws the images for the specific card
        for(int i = 0; i < Math.min(state.getPlayer(0).getCardsInHand().size(), MAXHAND); i++){ //for cards in player's hand
            int Id = state.getPlayer(0).getCardsInHand().get(i).getResourceId(); //gets the Id for card in hand
            if(Id != -1){
                handCards.get(i).setImageResource(Id); //draws specific card in image view
                handCards.get(i).invalidate(); //invalidate each image view
            }
        }

        //draws the flipped-down card images for the "left over" cards
        for(int i = state.getPlayer(0).getCardsInHand().size(); i < Math.min(handCards.size(),MAXHAND); i++){ //for image views that are not cards
            handCards.get(i).setImageResource(R.drawable.card_flipped); // //set cards flipped
            handCards.get(i).invalidate(); //invalidate each image view
        }


    }


     //Callback method, called when player gets a message
    @Override
    public void receiveInfo(GameInfo info) {

        if(info instanceof BANGState){
            this.state = (BANGState)info;
           render();
        }

    }

    //listener for all clicks in the GUI:
    @Override
    public void onClick(View v){
        GameAction gameAction; //initialize instance variable

        Log.i("ButtonInfo","click arrived");
        if(v == null)
        {
            Log.i("ButtonInfo","null view clicked on?");
            return;
        }
        if(v.getId() == endTurn.getId()){ //if end turn button is clicked
            Log.i("ButtonInfo","Ending turn");
            gameAction = new BANGEndTurn(this);
            game.sendAction(gameAction); //sends the EndTurn action
        }
        else if(v.getId() == quitGame.getId()){ //else if quit game button is clicked
            //this can work for now; there currently only exists one human player, and the only one who can quit the game
            Log.i("ButtonInfo","Quitting game");
            System.exit(0);
        }
        else if(v.getId() == drawPile.getId()) //when draw pile clicked, demonstrates the amount in pile
        {
            Log.i("ButtonInfo","Drawing two cards");
            topText.setText("Number of cards in deck: " + ((BANGLocalGame)game).state.drawPile.size());
            topText.invalidate();
        }
        else if(v.getId() == player2cards.getId()) //gets the amount 
        {
            topText.setText("Number of cards in player 2's hand: " + ((BANGLocalGame)game).state.players[1].getCardsInHand().size());
            topText.invalidate();
        }
        else if(v.getId() == player3cards.getId())
        {
            topText.setText("Number of cards in player 3's hand: " + ((BANGLocalGame)game).state.players[2].getCardsInHand().size());
            topText.invalidate();
        }
        else if(v.getId() == player4cards.getId())
        {
            topText.setText("Number of cards in player 4's hand: " + ((BANGLocalGame)game).state.players[3].getCardsInHand().size());
            topText.invalidate();
        }
        //11-26-18 ~ used for debugging
        else if(v.getId() == roles.get(0).getId())
        {
            topText.setText(""+state.players[0].getHealth());
            topText.invalidate();
        }
        else if(v.getId() == roles.get(1).getId())
        {
            topText.setText(""+state.players[1].getHealth());
            topText.invalidate();
        }
        else if(v.getId() == roles.get(2).getId())
        {
            topText.setText(""+state.players[2].getHealth());
            topText.invalidate();
        }
        else if(v.getId() == roles.get(3).getId())
        {
            topText.setText(""+state.bangsPlayed);
            topText.invalidate();
        }
        else if(v.getId() == chooseTarget.getId()){
            /*
             External Citation
             Date: 23 November 2018
             Problem: Needed to allow player to choose target.
             Resource: https://stackoverflow.com/questions/21329132/android-custom-dropdown-popup-menu
             Solution: used a tutorial to create popup menu.
            */
            PopupMenu popupMenu = new PopupMenu(myActivity, v);
            popupMenu.getMenuInflater().inflate(R.menu.target_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(myActivity, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                    switch (item.getItemId()){
                        case R.id.player2:
                            target = 1;
                            break;
                        case R.id.player3:
                            if(state.players[0].getRange() < state.distanceBetween(0, 2)) {
                                Toast.makeText(myActivity, "Cannot reach this player, try again.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            target = 2;
                            break;
                        case R.id.player4:
                            target = 3;
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
        else if(v.getId() == missed.getId()){
            PopupMenu popupMenu = new PopupMenu(myActivity, v);
            popupMenu.getMenuInflater().inflate(R.menu.missed, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Toast.makeText(myActivity, "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                    switch (item.getItemId()){
                        case R.id.noMissed:
                            game.sendAction(new BANGMissedAction(player, false));
                            break;
                        case R.id.yesMissed:
                            game.sendAction(new BANGMissedAction(player, true));
                            break;
                    }
                    return true;
                }
            });
            popupMenu.show();
        }
        else
        {
            int cardCliked = -1;
            for(int i = 0; i < handCards.size(); i++){
                if(v.getId() == handCards.get(i).getId()){
                    cardCliked = i;
                }
            }
            Log.i("buttn click","Player 0 has " + state.getPlayer(0).getCardsInHand().size()+" cards");
            if(cardCliked != -1){
                int cardNum = -1;
                if(state.players[0].getCardsInHand().size() <= cardCliked)
                {
                    Log.i("recieve info", "Ask for card beyond hand size: "+cardCliked);
                    return;
                }

                cardNum = state.players[0].getCardsInHand().get(cardCliked).getCardNum();
                Log.i("button click", "Asked for card "+ cardCliked);

                if(state.players[target].getHealth() <= 0) target++;
                this.cardLastClicked = cardNum;

                game.sendAction(new BANGMoveAction(this, target, cardNum));

                //need to send action if card was valid
            }
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

    //returns the GUI's top view
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.mainLayout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("BANG: " + allPlayerNames[0] + " vs. " + allPlayerNames[1]);

        playerTexts = new ArrayList<>();
        roles = new ArrayList<>();
        handCards = new ArrayList<>(); //new array list for human player's hand
        activeCards = new ArrayList<>(); //array list for players' active cards
        for (int i = 0; i < 4; i++) { //iterates for each player (0)
            activeCards.add(new ArrayList<ImageView>()); //adds image view array list for each player
        }

        this.topText = (TextView) myActivity.findViewById(R.id.topText);

        //Initialize the widget reference member variables
        this.quitGame = (Button) myActivity.findViewById(R.id.quit);
        this.endTurn = (Button) myActivity.findViewById(R.id.endTurn);
        this.chooseTarget = (Button) myActivity.findViewById(R.id.chooseTarget);
        this.missed = (Button) myActivity.findViewById(R.id.missed);


        this.drawPile = (ImageView) myActivity.findViewById(R.id.drawPile);

        this.player2cards = (ImageView) myActivity.findViewById(R.id.p2c);
        player2cards.setOnClickListener(this);

        this.player3cards = (ImageView) myActivity.findViewById(R.id.p3c);
        player3cards.setOnClickListener(this);

        this.player4cards = (ImageView) myActivity.findViewById(R.id.p4c);
        player4cards.setOnClickListener(this);


        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c1));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c2));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c3));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c4));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c5));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c6));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c7));
        this.handCards.add((ImageView) myActivity.findViewById(R.id.p1c8));


        this.roles.add((ImageView) myActivity.findViewById(R.id.p1role));
        this.roles.add((ImageView) myActivity.findViewById(R.id.p2role));
        this.roles.add((ImageView) myActivity.findViewById(R.id.p3role));
        this.roles.add((ImageView) myActivity.findViewById(R.id.p4role));

          for(ImageView v: this.roles)
          {
            v.setOnClickListener(this);
          }

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
        chooseTarget.setOnClickListener(this);
        missed.setOnClickListener(this);

        drawPile.setOnClickListener(this);

        //Listen for image presses
        for(ImageView v: this.handCards){
            v.setOnClickListener(this);
        }



        //active1.setOnTouchListener(this);
        // active2.setOnTouchListener(this);
        // active3.setOnTouchListener(this);
        // active4.setOnTouchListener(this);
        // active5.setOnTouchListener(this);



        health = new ArrayList<>(); //array list for players' health
        for(int i = 0; i < 4; i++){ //iterates for each player (0)
            health.add(new ArrayList<ImageView>()); //adds image view array list for each player
        }

        health.get(0).add((ImageView) myActivity.findViewById(R.id.p1h1));
        health.get(0).add((ImageView) myActivity.findViewById(R.id.p1h2));
        health.get(0).add((ImageView) myActivity.findViewById(R.id.p1h3));
        health.get(0).add((ImageView) myActivity.findViewById(R.id.p1h4));
        health.get(0).add((ImageView) myActivity.findViewById(R.id.p1h5));


        health.get(1).add((ImageView) myActivity.findViewById(R.id.p2h1));
        health.get(1).add((ImageView) myActivity.findViewById(R.id.p2h2));
        health.get(1).add((ImageView) myActivity.findViewById(R.id.p2h3));
        health.get(1).add((ImageView) myActivity.findViewById(R.id.p2h4));
        health.get(1).add((ImageView) myActivity.findViewById(R.id.p2h5));


        health.get(2).add((ImageView) myActivity.findViewById(R.id.p3h1));
        health.get(2).add((ImageView) myActivity.findViewById(R.id.p3h2));
        health.get(2).add((ImageView) myActivity.findViewById(R.id.p3h3));
        health.get(2).add((ImageView) myActivity.findViewById(R.id.p3h4));
        health.get(2).add((ImageView) myActivity.findViewById(R.id.p3h5));


        health.get(3).add((ImageView) myActivity.findViewById(R.id.p4h1));
        health.get(3).add((ImageView) myActivity.findViewById(R.id.p4h2));
        health.get(3).add((ImageView) myActivity.findViewById(R.id.p4h3));
        health.get(3).add((ImageView) myActivity.findViewById(R.id.p4h4));
        health.get(3).add((ImageView) myActivity.findViewById(R.id.p4h5));

        //11-26-18 ~ bullet imageviews does not need to listen to anything?
        //for(int i = 0; i<4; i++){
          //  for(ImageView v: health.get(i)){
            //    v.setOnClickListener(this);
            //}
        //}

        playerLayouts = new ArrayList<>();

        this.playerLayouts.add((LinearLayout) myActivity.findViewById(R.id.p2MainLayout));
        this.playerLayouts.add((LinearLayout) myActivity.findViewById(R.id.p3row));
        this.playerLayouts.add((LinearLayout) myActivity.findViewById(R.id.p4MainLayout));




        this.playerTexts.add((TextView) myActivity.findViewById(R.id.computer1));
        this.playerTexts.add((TextView) myActivity.findViewById(R.id.computer2));
        this.playerTexts.add((TextView) myActivity.findViewById(R.id.computer3));




    }

}
