package com.example.davidvargas.bang_gameframework.objects;

/**
 * Class of cards that are played from hand, and exists in drawPile and discardPile.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class PlayableCard extends Card
{

    //initializes variables:
    private boolean isActive;
    private int cardNum;

    //constructor
    public PlayableCard()
    {
        super();
        isActive = false;
        cardNum = 0;
    }

    //copy constructor for PlayableCard object
    public PlayableCard(PlayableCard p)
    {
        super(p);
        isActive = p.isActive;
        cardNum = p.cardNum;
    }

    //constructor that passes in both parameters
    public PlayableCard(boolean isActive, int cardNum)
    {
        super(cardNum);
        this.isActive = isActive;
        this.cardNum = cardNum;
    }

    //getter method for if card is active
    public boolean getIsActive()
    {
        return isActive;
    }

    //getter method for card number
    public int getCardNum()
    {
        return cardNum;
    }

    //toString method:
    public String toString()
    {
        return super.toString() + "\t\t\t\t\t\tCard number: "+cardNum+"\n"+"\t\t\t\t\t\tIs card active: "+String.valueOf(isActive)+"\n";
    }
}
