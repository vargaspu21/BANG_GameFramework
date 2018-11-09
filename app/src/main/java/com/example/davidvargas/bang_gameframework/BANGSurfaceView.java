package com.example.davidvargas.bang_gameframework;

import android.content.Context;
import android.util.AttributeSet;

import com.example.davidvargas.bang_gameframework.game.util.FlashSurfaceView;

public class BANGSurfaceView extends FlashSurfaceView {

    protected BANGState state; //the game's state

    /**
     * Constructor for the TTTSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public BANGSurfaceView(Context context){
        super(context);
        init();
    }

    public BANGSurfaceView(Context context, AttributeSet atrrs){
        super(context, atrrs);
        init();
    }

    public void init(){

    }

    public void setState(BANGState state){
        this.state = state;
    }
}
