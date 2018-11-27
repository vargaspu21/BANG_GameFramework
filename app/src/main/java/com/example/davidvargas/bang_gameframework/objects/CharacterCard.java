package com.example.davidvargas.bang_gameframework.objects;

/**
 * Class that contains characters with special abilities to spice up BANG! gameplay. Also contains health modifiers.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class CharacterCard extends Card {

    //initialize variables
    int baseHealth;
    int cardNum;

    //constructor:
    public CharacterCard()
    {
        super();
        baseHealth = 4;
        cardNum = -1;
    }

    //copy constructor for CharacterCard
    public CharacterCard(CharacterCard c){
        super(c);
        baseHealth = c.baseHealth;
        cardNum = c.cardNum;
    }
    //constructor that passes in health
    public CharacterCard(int health, int num)
    {
        super();
        baseHealth = health;
        cardNum = num;
    }

    //getter method for cardNum
    public int getCardNum()
    {
        return cardNum;
    }

    //setter method for cardNum
    public void setCardNum(int cardNum)
    {
        this.cardNum = cardNum;
    }

    //getter method for base health
    public int getBaseHealth()
    {
        return baseHealth;
    }

    //setter method for base health
    public void setBaseHealth(int baseHealth)
    {
        this.baseHealth = baseHealth;
    }

    //toString method:
    public String toString()
    {
        return super.toString()+"\t\t\tBase Health: "+baseHealth+"\n"+ "\t\t\tAbility: "+cardNum+"\n";
    }
}
