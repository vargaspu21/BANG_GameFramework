package com.example.davidvargas.bang_gameframework.objects;

import java.util.ArrayList;

/**
 * Contains information of each player, stored in gameState
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 * @see com.example.davidvargas.bang_gameframework.BANGState
 */

public class PlayerInfo
{
    public int health, range;
    protected int maxHealth;
    protected RoleCard role;
    protected CharacterCard character;
    //12-06-18 ~ Active Card will only be one at a time, not enough time to make it handle multiple, nor be able to represent multiple
    protected PlayableCard activeCard;
    protected ArrayList<PlayableCard> cardsInHand;
    protected PlayableCard weapon;
    protected int name;

    //constructor for the Player Info object
    public PlayerInfo()
    {
        health = 4;
        range= 1;
        maxHealth = 4;
        role = new RoleCard(0); //maybe find a default role to change into?
        character = new CharacterCard(); //default character too?
        activeCard = new PlayableCard(); //filler/default cards in hand?
        cardsInHand = new ArrayList<PlayableCard>();
        weapon = new PlayableCard();

    }

    //constructor for Player info object that passes in the name of the player: initializes it
    public PlayerInfo(int i)
    {
        health = 4;
        range = 1;
        maxHealth = 4;
        role = new RoleCard(0); //maybe find a default role to change into?
        character = new CharacterCard(); //default character too?
        activeCard = new PlayableCard(); //filler/default cards in hand?
        cardsInHand = new ArrayList<PlayableCard>();
        name = i;
        weapon = new PlayableCard();
    }

    //constructor for Player object that passes in the role and character, initializing them
    public PlayerInfo(RoleCard role, CharacterCard character)
    {
        health = character.baseHealth; //gets the player health from the character card
        range = 1;
        if(role.getRole()==0) health++; //if the role is sheriff, add one more life point
        maxHealth = health; //sets the max health to the starting health
        this.role = role; //declares the role of this player to that passed in
        this.character = character; //declares the character of this player to that passed in
    }

    //copy constructor for Player Info:
    public PlayerInfo(PlayerInfo pi)
    {
        this.health = pi.health;
        this.range = pi.range;
        this.maxHealth = pi.maxHealth;
        this.role = pi.role;
        this.character = pi.character;

        //following lines creates a copy for each of the cards in the different array lists:
        this.weapon  = new PlayableCard(pi.getWeapon());
        this.activeCard = new PlayableCard(pi.getActiveCard());
        cardsInHand = new ArrayList<PlayableCard>();
        for(PlayableCard c: pi.cardsInHand) this.cardsInHand.add(new PlayableCard(c));
    }

    //gives the name, which is a number
    public int getName()
    {
        return name;
    } //getter method for the player's name

    //sets the name/number
    public void setName(int i)
    {
        this.name = i;
    } //setter method for the player's name

    public int getHealth()
    {
        return health;
    } //getter method for the player's health

    public int getMaxHealth()
    {
        return maxHealth;
    } //getter method for the player's max health

    public int getRange(){ return range;} //getter method for player's range

    public RoleCard getRole()
    {
        return role;
    } //getter method for the player's role

    public CharacterCard getCharacter() { return character; } //getter method for the player's character

    public PlayableCard getActiveCard() { return activeCard; } //getter method for "active" blue cards

    public PlayableCard getWeapon() { return weapon; }

    public ArrayList<PlayableCard> getCardsInHand() { return cardsInHand; } //getter method for player's hand

    //setter method for player's health'
    public void setHealth(int health)
    {
        if(health > this.getMaxHealth())
            this.health = this.getMaxHealth();
        else
            this.health = health;
    }

    public void setRange(int range){ this.range = range; } //setter method for player's range

    public void setMaxHealth(int maxHealth) { this.maxHealth = maxHealth; } //setter method for player's max health'

    public void setRole(RoleCard role)
    {
        this.role = role;
    } //setter method for player's role'

    public void setCharacter(CharacterCard character) { this.character = character; } //setter method for player's character

    //setter method for list of player's active blue cards
    public void setActiveCard(PlayableCard c)
    {
        activeCard = c;
    }

    public void setWeapon(PlayableCard c)
    {
        weapon = c;
    }

    //setter method for list of player's hand
    public void setCardsInHand(PlayableCard c)
    {
        cardsInHand.add(c);
    }

    //toString method for Player's Information:
    public String toString()
    {
        //concatenates the string of the name
        String s = "\t\tPlayer " + String.valueOf(name) + ":\n";
        s+= "\t\t\tActive Cards:\n";

        //concatenates strings of active cards
        s+= "\t\t\tActive Card:\n" + activeCard.toString();

        //concatenates strings of cards in hand
        for(PlayableCard p: cardsInHand) s+=  p.toString();

        //concatenates health and max health strings
        s+= "\t\t\tHealth: "+health+"\n"+"\t\t\tRange: "+range+"\n"+"\t\t\tMax Health: "+maxHealth+"\n";

        //concatenates role and character strings
        s+= "\t\t\tRole:\n"+role.toString() +"\t\t\tCharacter:\n"+ character.toString();
        return s;
    }
}
