package com.example.davidvargas.bang_gameframework.objects;

import com.example.davidvargas.bang_gameframework.R;

public class Card {
    //initializes variables:
    //constant for playablecards, will all be added for copy constructor soon
    public final int SCHOFIELD = 0;
    public final int REVCARBINE = 1;
    public final int WINCHESTER = 2;
    public final int VOLCANIC = 3;
    public final int REMINGTON = 4;
    public final int BANG = 5;
    public final int MISSED = 6;
    public final int BEER = 7;
    public final int PANIC = 8;
    public final int CATBALOU = 9;
    public final int STAGECOACH = 10;
    public final int WELLSFARGO = 11;
    public final int GATLING = 12;
    public final int DUEL = 13;
    public final int INDIANS = 14;
    public final int GENERALSTORE = 15;
    public final int SALOON = 16;
    public final int JAIL = 17;
    public final int DYNAMITE = 18;
    public final int BARREL = 19;
    public final int SCOPE = 20;
    public final int MUSTANG = 21;
    protected String name, description;
    protected int resourceId;

    //constructor for Card object
    public Card()
    {
        name = null;
        description = null;
        resourceId = -1;
    }

    //copy constructor for Card object:
    public Card(Card c){
        name = c.name;
        description = c.description;
        resourceId = c.resourceId;
    }

    //received from playablecard constructor, will change name and description as suited
    public Card(int cardNum)
    {
        switch(cardNum)
        {
            case SCHOFIELD:
                name = "Schofield";
                description = "This is a good gun. Range+2\n";
                resourceId = R.drawable.schofield;
                break;
            case REVCARBINE:
                name = "Rev Carbine";
                description = "Rev it up. Range+4\n";
                resourceId = R.drawable.rev_carabine;
                break;
            case WINCHESTER:
                name = "Winchester";
                description = "For the win. Range+5\n";
                resourceId = R.drawable.winchester;
                break;
            case VOLCANIC:
                name = "Volcanic";
                description = "Pompeii. Unlimited uses of BANG. Range+1\n";
                resourceId = R.drawable.volcanic;
                break;
            case REMINGTON:
                name = "Remington";
                description = "Remington. Range+3\n";
                resourceId = R.drawable.remington;
                break;
            case BANG:
                name = "BANG!";
                description = "Dishes out one damage. I love you kitchen gun!\n";
                resourceId = R.drawable.bang;
                break;
            case MISSED:
                name = "Missed!";
                description = "I miss you! Dodges one BANG!\n";
                resourceId = R.drawable.missed;
                break;
            case BEER:
                name = "Beer";
                description = "Let's get drunk. Health+1\n";
                resourceId = R.drawable.beer;
                break;
            case GATLING:
                name = "Gatling";
                description = "Gratatatatatatata. Deals 1 damage to every enemy.\n";
                resourceId = R.drawable.gatling;
                break;
            case INDIANS:
                name = "Indians";
                description = "Angry Indians are here! Lose a BANG! card or lose one health!\n";
                resourceId = R.drawable.indians;
                break;
            case SALOON:
                name = "Saloon";
                description = "Refreshing! Everyone heals one health. You heal two!\n";
                resourceId = R.drawable.saloon;
                break;
            case DUEL:
                name = "Duel";
                description = "The player playing this card challenges any other player (at any distance) staring him in the eyes.\n";
                resourceId = R.drawable.duel_card;
                break;
        }
    }

    //setter method for card name
    public void setName(String name)
    {
        this.name = name;
    }

    //setter method for card description
    public void setDescription(String description)
    {
        this.description = description;
    }

    //getter method for card name
    public String getName()
    {
        return name;
    }

    //getter method for card description
    public String getDescription()
    {
        return description;
    }

    public int getResourceId(){
        return resourceId;
    }

    //toStriing method:
    public String toString()
    {
        return "\t\t\t\tName of Card: "+name+"\n"+"\t\t\t\t\t\tDescription: "+description+"\n";
    }
}
