package com.example.davidvargas.bang_gameframework.objects;

public class PlayableCard extends Card {

    //initializes variables:
    private boolean isActive; //determines if the card is currently an active blue card
    private int cardNum; //identify effect come switch case
    private int suit; //identifies suit of card for draw! cases

    //constructor
    public PlayableCard()
    {
        super();
        isActive = false;
        cardNum = 0;
        suit = 0;
    }

    //copy constructor for PlayableCard object
    public PlayableCard(PlayableCard p)
    {
        super(p);
        isActive = p.isActive;
        cardNum = p.cardNum;
        suit = p.suit;
    }
    //constructor that passes in both parameters
    public PlayableCard(boolean isActive, int cardNum)
    {
        super(cardNum);
        this.isActive = isActive;
        this.cardNum = cardNum;
        suit = 0; //for now defaults to 0 (hearts)
        //will need to update much of game state to set suits, will change in future
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

    //getter for suits
    public int getSuit()
    {
        return suit;
    }
}
