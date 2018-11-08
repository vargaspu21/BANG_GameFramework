package com.example.davidvargas.bang_gameframework;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidvargas.bang_gameframework.objects.PlayableCard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //the sample game created
        GameState game = new GameState();

        //finds textview, prints a sample text
        TextView testText = (TextView)findViewById(R.id.testText);
        testText.setText("Player 2 health: "+game.players[1].getHealth());

        //gives player 1 a bang card
        //game.players[0].setCardsInHand(new PlayableCard(false,game.BANG));

        /* CURRENTLY DOES NOT WORK
        //links player 1's (currently invisible) cards to layout elements
        ImageView p1c1 = (ImageView)findViewById(R.id.p1c1);
        ImageView p1c2 = (ImageView)findViewById(R.id.p1c2);
        ImageView p1c3 = (ImageView)findViewById(R.id.p1c3);
        ImageView p1c4 = (ImageView)findViewById(R.id.p1c4);
        ImageView p1c5 = (ImageView)findViewById(R.id.p1c5);

        p1c1.setImageResource(R.drawable.bang_card);
        p1c1.invalidate();
        */

        //plays bang card, player 2 hp -1
        //game.playBANG(0,1);


    }
}
