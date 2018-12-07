package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.game.infoMsg.GameState;
import com.example.davidvargas.bang_gameframework.objects.Card;
import com.example.davidvargas.bang_gameframework.objects.PlayableCard;
import com.example.davidvargas.bang_gameframework.objects.PlayerInfo;
import com.example.davidvargas.bang_gameframework.objects.RoleCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

/**
 * Contains information about all players and current state of game. Contains all actions players can do to other players.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGState extends GameState{

    private static final long serialVersionUID = 7552321013488624386L;

    //instance variables:
    private int playerToMove;

    //Character Card Constants
    public static final int PAULREGRET = 0;
    public static final int JOURDONNAIS = 1;
    public static final int BLACKJACK = 2;
    public static final int SLABTHEKILLER = 3;
    public static final int ELGRINGO = 4;
    public static final int JESSEJONES = 5;
    public static final int SUZYLAFAYETTE = 6;
    public static final int WILLYTHEKID = 7;
    public static final int ROSEDOOLAN = 8;
    public static final int BARTCASSIDY = 9;
    public static final int PEDRORAMIREZ = 10;
    public static final int SIDKETCHUM = 11;
    public static final int LUCKYDUKE = 12;
    public static final int VULTURESAM = 13;
    public static final int CALAMITYJANET = 14;
    public static final int KITCARLSON = 15;

    //Playable Card Constants
    public static final int SCHOFIELD = 0;
    public static final int REVCARBINE = 1;
    public static final int WINCHESTER = 2;
    public static final int VOLCANIC = 3;
    public static final int REMINGTON = 4;
    public static final int BANG = 5;
    public static final int MISSED = 6;
    public static final int BEER = 7;
    public static final int PANIC = 8;
    public static final int CATBALOU = 9;
    public static final int STAGECOACH = 10;
    public static final int WELLSFARGO = 11;
    public static final int GATLING = 12;
    public static final int DUEL = 13;
    public static final int INDIANS = 14;
    public static final int GENERALSTORE = 15;
    public static final int SALOON = 16;
    public static final int JAIL = 17;
    public static final int DYNAMITE = 18;
    public static final int BARREL = 19;
    public static final int SCOPE = 20;
    public static final int MUSTANG = 21;

    //Amount of cards in starting deck constants
    public final int NUMSCHOFIELD = 3;
    public final int NUMVOLCANIC = 2;
    public final int NUMBANG = 25;
    public final int NUMMISSED = 12;
    public final int NUMBEER = 6;
    public final int NUMPANIC = 4;
    public final int NUMCATBALOU = 4;
    public final int NUMSTAGECOACH = 2;
    public final int NUMDUEL = 3;
    public final int NUMINDIANS = 2;
    public final int NUMGENERALSTORE = 2;
    public final int NUMJAIL = 3;
    public final int NUMBARREL = 2;
    public final int NUMMUSTANG = 2;

    //CConstants for roles
    public static final int SHERIFF = 0;
    public static final int OUTLAW  = 1;
    public static final int RENEGADE = 2;

    //initializes variables:
    protected ArrayList<PlayableCard> drawPile;
    protected ArrayList <PlayableCard> discardPile;
    protected ArrayList<Integer> roles;
    protected int playerTurn, bangsPlayed;
    protected PlayerInfo[] players;
    public static Random rand = new Random ();
    protected boolean missedCard;

    //constructor for gameState, used to make a new one:
    public BANGState()
    {
        drawPile = new ArrayList<PlayableCard>();
        drawPile = initDeck(drawPile);
        discardPile = new ArrayList<PlayableCard>();
        players = new PlayerInfo[4];//can fit four players max
        players[0] = new PlayerInfo(1);//four new players inserted
        players[1] = new PlayerInfo(2);
        players[2] = new PlayerInfo(3);
        players[3] = new PlayerInfo(4);
        playerTurn = 0;//current players turn
        //amount of cards drawn depends on the health:
        for(int i =0; i<4; i++){
            for(int j = 0; j<players[i].getHealth(); j++){
                draw(i);
            }
        }
        /*
         External Citation
         Date: 10 October 2018
         Problem: Did not know how to set seed of Random using the current time.
         Resource: Tribelhorn
         Solution: Assisted with writing this line of code.
         */
        rand.setSeed(System.currentTimeMillis());

        //sets the roles randomly :
        roles = new ArrayList<Integer>();
        roles.add(0);
        roles.add(1);
        roles.add(1);
        roles.add(2);
        Collections.shuffle(roles, rand);
        for(int i=0; i<4; i++){
            players[i].setRole(new RoleCard(roles.get(i)));
            if(players[i].getRole().getRole() == SHERIFF){
                players[i].setMaxHealth(5);
                players[i].setHealth(5);
                playerTurn = i; //sets the playerTurn to start at the sherrif
            }
        }
        missedCard = false;
    }

    //copy constructor - used to replicate two gameStates:
    public BANGState(BANGState bs)
    {
        //creates a deep copy of each card in the array list:
        drawPile = new ArrayList<PlayableCard>();
        for(PlayableCard c: bs.drawPile) this.drawPile.add(new PlayableCard(c.getIsActive(),c.getCardNum()));

        //creates a deep copy of each card in the array list:
        discardPile = new ArrayList<PlayableCard>();
        for(PlayableCard c: bs.discardPile) this.discardPile.add(new PlayableCard(c.getIsActive(),c.getCardNum()));

        //creates a deep copy of each card in the array:
        players = new PlayerInfo[4];
        for(int i = 0; i< players.length; i++){
            this.players[i] = new PlayerInfo(bs.players[i]);
        }
        this.playerTurn = bs.playerTurn;
        this.bangsPlayed = bs.bangsPlayed;

        //copies array list of the roles:
        roles = new ArrayList<Integer>();
        for(Integer i: roles) this.roles.add(i);
        this.missedCard = bs.missedCard;
    }

    public PlayerInfo getPlayer(int i){
        return players[i];
    }

    //method to initialize deck: adds specific amount (80 total) for each card through for loops, and it randomizes the deck
    private ArrayList<PlayableCard> initDeck(ArrayList<PlayableCard> deck)
    {
        //adds cards based on the Constant amounts (for-loops);
        int i;
        for(i=0; i<NUMSCHOFIELD; i++) deck.add(new PlayableCard(true, SCHOFIELD));
        deck.add(new PlayableCard(true, REVCARBINE));
        deck.add(new PlayableCard(true, WINCHESTER));
        for(i=0; i<NUMVOLCANIC; i++) deck.add(new PlayableCard(true, VOLCANIC));
        deck.add(new PlayableCard(true, REMINGTON));
        for(i=0; i<NUMBANG; i++) deck.add(new PlayableCard(false, BANG));
        for(i=0; i<NUMMISSED; i++) deck.add(new PlayableCard(false, MISSED));
        for(i=0; i<NUMBEER; i++) deck.add(new PlayableCard(false, BEER));
        for(i=0; i<NUMPANIC; i++) deck.add(new PlayableCard(false, PANIC));
        for(i=0; i<NUMCATBALOU; i++) deck.add(new PlayableCard(false, CATBALOU));
        for(i=0; i<NUMSTAGECOACH; i++) deck.add(new PlayableCard(false, STAGECOACH));
        deck.add(new PlayableCard(false, WELLSFARGO));
        deck.add(new PlayableCard(false, GATLING));
        for(i=0; i<NUMDUEL; i++) deck.add(new PlayableCard(false, DUEL));
        for(i=0; i<NUMINDIANS; i++) deck.add(new PlayableCard(false, INDIANS));
        //for(i=0; i<NUMGENERALSTORE; i++) deck.add(new PlayableCard(false, GENERALSTORE));
        deck.add(new PlayableCard(false, SALOON));
        for(i=0; i<NUMJAIL; i++) deck.add(new PlayableCard (true, JAIL));
        //deck.add(new PlayableCard(true, DYNAMITE));
        //for(i=0; i<NUMBARREL; i++) deck.add(new PlayableCard(true, BARREL));
        //deck.add(new PlayableCard(true, SCOPE));
        //for(i=0; i<NUMMUSTANG; i++) deck.add(new PlayableCard(true, MUSTANG));
        shuffle();
        return deck;
    }

    //shuffle method for the drawPile:
    private void shuffle()
    {
        /*
         External Citation
         Date: 20 October 2018
         Problem: Did not know how to shuffle array list:
         Resource:
         https://stackoverflow.com/questions/16112515/how-to-shuffle-an-arraylist
         Solution: I used the example code from this post.
         */
        Collections.shuffle(drawPile, rand);//makes use of collections object to shuffle arraylist
    }

    public boolean discardCard(PlayableCard card, int player)
    {
        if (players[player].getCardsInHand().contains(card))
        {
            players[player].getCardsInHand().remove(card);//delete an instance of card if exists
            discardPile.add(card);//adds card into discard pile
            return true;
        }
        else
        {
            return false; //default: return true;
        }
    }

    //method to make new draw pile from discard pile:
    private boolean discardIntoDraw(ArrayList<PlayableCard> discardPile){
        if(this.drawPile.isEmpty()){ //if draw pile is empty, move discard pile cards to it
            for(PlayableCard c: this.discardPile){
                drawPile.add(c);
            }
            return true;
        }
        return false;
    }

    //function to draw the topmost card in the discard pile:
    public boolean drawFromDiscard(int player)
    {
        if(playerTurn != player)
        {
            return false; //if not turn, return
        }
        else
        {
            if(discardPile.isEmpty())
            {
                return false; //if discard pile empty, return
            }
            PlayableCard toDraw = discardPile.get(0);//gets topmost card
            players[player].setCardsInHand(toDraw);//adds topmost card to player's hand
            discardPile.remove(toDraw);//deletes the first instance of the card from drawpile
            return true;
        }
    }

    public boolean drawFromPlayer(int player, int target)
    {
        int handSize = players[target].getCardsInHand().size();//gets size of opponents hand
        int indexToDraw = rand.nextInt(handSize);//randomly chooses card index to draw
        PlayableCard toDraw = players[target].getCardsInHand().get(indexToDraw);//records what that card is
        players[player].getCardsInHand().add(new PlayableCard(toDraw));//new instance of that card added to player, ASK TRIBELHORN ABOUT THIS??
        players[target].getCardsInHand().remove(toDraw);//removes the one from target
        return true;
    }

    //draws one card:
    public boolean draw(int player)
    {
        if(playerTurn != player)
        {
            return false;
        }
        else
        {
            if(drawPile.isEmpty()){
                discardIntoDraw(drawPile);
            }
            PlayableCard toDraw = drawPile.get(0);
            players[player].getCardsInHand().add(toDraw);
            drawPile.remove(toDraw);
            return true;
        }
    }

    //draws two cards, used for starting-turn draw ONLY:
    public boolean drawTwo(int player)
    {
        if(playerTurn != player)//if not their turn, return
        {
            return false;
        }
        checkVultureSam(player); //checks for Vulture Sam
            /*if(p.getCardNum() == JAIL)
            {
                if(!drawExclamation(player, SPADES)) {
                    players[player].getActiveCards().remove(p);
                    discardPile.add(p);
                    playerTurn ++;
                    return true;
                }
                else
                {
                    players[player].getActiveCards().remove(p);
                    discardPile.add(p);
                }
            }
            if(p.getCardNum() == DYNAMITE) {
                if(drawExclamation(player, HEARTS))
                {
                    players[player].getActiveCards().remove(p);
                    discardPile.add(p);
                    players[player].setHealth(players[player].getHealth() - 3);
                    if(players[player].getHealth() <= 0)
                        return true;
                }
                else
                {
                    players[player].getActiveCards().remove(p);
                    if(player < 3)
                        players[player + 1].setActiveCards(new PlayableCard(true, DYNAMITE));
                    else if(player == 3)
                        players[0].setActiveCards(new PlayableCard(true, DYNAMITE));
                }
            }*/
        if(players[player].getCharacter().getCardNum()==JESSEJONES) //if player is Jesse Jones, first card drawn is from a random player
        {
            return checkJesseJones(player);
        }
        else if(players[player].getCharacter().getCardNum() == BLACKJACK) //if character is blackjack try to use their ability
        {
            /*
            Draws the first card then does a suit check for hearts or diamonds on second draw
            if it succeeds then draw a third card, else only two
            SUPPOSED TO REVEAL THE SECOND CARD THAT HE DRAWS BUT IDK HOW YET
             */


            draw(player);
           /* if(drawExclamation(player, HEARTS) || drawExclamation(player, DIAMONDS)){
                draw(player);
                draw(player);
                return true;
            }
            else {
                draw(player);
                return true;
            }*/

        }
        else
        {
            draw(player);//calls draw twice
            draw(player);
            return true;
        }
        return false;
    }

    //method to determine the distance between players:
    public int distanceBetween(int attacker, int target){
        //if first player, distance is 1 for players 2 and 4, and is 2 for player 3
        int distance = 1;
        /*
        First checks if the target is Paul Regret if it is, then add one to distance
        Then checks if the target has a mustang, if player does then adds one to distance
        These abilities can stack
         */
        if(this.players[target].getCharacter().getCardNum() == PAULREGRET)
            distance ++;
        if(this.players[attacker].getActiveCard().getCardNum() == MUSTANG)
            distance ++;
        if(attacker == 0 ){
            if(target == 1 || target == 3)
                return distance;
            else if(target ==2)
                return distance++;
        }
        //if second player, distance is 1 for players 3 and 1, and is 2 for player 4
        else if(attacker == 1){
            if(target == 2 || target == 0 )
                return distance;
            else if(target == 3)
                return distance++;
        }
        //if third player, distance is 1 for players 2 and 4, and is 2 for player 1
        else if(attacker == 2){
            if(target == 3 || target == 1 )
                return distance;
            else if(target == 0) {
                return distance++;
            }
        }
        //if fourth player, distance is 1 for players 3 and 1, and is 2 for player 2
        else if (attacker == 3){
            if(target == 0 || target == 2 )
                return distance;
            else if(target == 1)
                return distance++;
        }
        return 0; //default , return 0
    }

    //helper method to check for Vulture Sam when drawing two cards:
    public void checkVultureSam(int player){
        if(players[player].getHealth() <= 0)
        {
            for(int i = 0; i < 4; i++)
            {
                if(players[i].getCharacter().getCardNum() == VULTURESAM) {
                    for (PlayableCard p : players[player].getCardsInHand()) {
                        players[i].setCardsInHand(p);
                    }
                }
            }
        }
    }

    //helper method for drawing a card from opponent if character is Jesse Jones:
    public boolean checkJesseJones(int player){
        if(player==0)
        {
            int toDrawFrom = rand.nextInt(3)+1;
            drawFromPlayer(player, toDrawFrom);
        }
        else if(player==1)
        {
            int[] drawArray = {0,2,3};
            int toDrawFrom = rand.nextInt(3);
            drawFromPlayer(player,drawArray[toDrawFrom]);
        }
        else if(player==2)
        {
            int[] drawArray = {0,1,3};
            int toDrawFrom = rand.nextInt(3);
            drawFromPlayer(player,drawArray[toDrawFrom]);
        }
        else if(player==3)
        {
            int toDrawFrom = rand.nextInt(3);
            drawFromPlayer(player,toDrawFrom);
        }
        else
        {
            return false;
        }
        draw(player);
        return true;
    }

    public boolean playSchofield(int player){
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == SCHOFIELD){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setWeapon(new PlayableCard(false,SCHOFIELD));
                players[player].setRange((players[player].getRange()) + 2);
                return true;
            }
        }
        return false;
    }

    public boolean playRevCarbine(int player){
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == REVCARBINE){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setWeapon(new PlayableCard(false, REVCARBINE));
                players[player].setRange((players[player].getRange()) + 4);
                return true;
            }
        }
        return false;
    }

    public boolean playWinchester(int player){
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == WINCHESTER){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setWeapon(new PlayableCard(false, WINCHESTER));
                players[player].setRange((players[player].getRange()) + 5);
                return true;
            }
        }
        return false;
    }

    public boolean playVolcanic(int player){
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == VOLCANIC){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setWeapon(new PlayableCard(false, VOLCANIC));
                players[player].setRange((players[player].getRange()) + 1);
                return true;
            }
        }
        return false;
    }

    public boolean playRemington(int player){
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == REMINGTON){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setWeapon(new PlayableCard(false, REMINGTON));
                players[player].setRange((players[player].getRange()) + 3);
                return true;

            }
        }
        return false;
    }
    public boolean playCard(int player, int target, int cardNum)//will be the cases in playableCard; cases should be handled here because this is the main gamestate
    {
        if(playerTurn != player)
        {
            return false;
        }
        else
        {
            switch(cardNum)
            {
                case SCHOFIELD: //schofield, +2 range
                    playSchofield(player);
                    return true;
                case REVCARBINE: //rev carabine, +4 range
                    playRevCarbine(player);
                    return true;
                case WINCHESTER: //winchester, +5 range
                    playWinchester(player);
                    return true;
                case VOLCANIC: //volcanic, +1 range, play any number of bangs
                    //second effect apply during battle phase
                    playVolcanic(player);
                    return true;
                case REMINGTON: //remington, +3 range
                    playRemington(player);
                    return true;
                case BANG: //11-26-18 ~ COMPLETED, BUT BUGS
                    playBANG(player, target);
                    return true;

                case MISSED:
                    //cannot be used without replying to a bang
                    //can be implemented multiple ways, can have a separate fn
                    return false; //false for now?

                case BEER: //11-26-18 ~ COMPLETED
                    playBeer(player);
                    return true;

                case PANIC: //11-26-18 ~ COMPLETED
                    //player in 1 range gives up a card
                    playPanic(player, target);
                    return true;

                case CATBALOU: //11-26-18 ~ COMPLETED
                    //one player discards a card
                    playCatBalou(player, target);
                    return true;

                case STAGECOACH: //11-26-18 ~ COMPLETED
                    //draw two cards
                    playStagecoach(player);
                    return true;

                case WELLSFARGO: //11-26-18 ~ COMPLETED
                    //draw three cards
                    playWellsfargo(player);
                    return true;

                case GATLING: //11-26-18 ~ COMPLETED
                    //all other players lose one health
                    //copy rose doolans effect
                    playGatling(player);
                    return true;

                case DUEL: //11-26-18 ~ COMPLETED
                    //back-and-forth with selected player
                    playDuel(player, target);
                    return true;

                case INDIANS:
                    //automatic for now
                    //check players entire hands for a bang. discard if found. dont lose a life.
                    playIndians(player);
                    return true;

                case GENERALSTORE:
                    return false;

                case SALOON:
                     playSaloon(player);
                     return true;

                case JAIL:
                    playJail(player,target);
                    //playActiveCard(player, target, cardNum);
                    return true;
                //start of turn check for jail, if players has then skip turn
                //implemented in drawTwo method as that signals start of turn
                case DYNAMITE:
                    playActiveCard(player, target, cardNum);
                    return true;
                //start of turn draw! mechanic to determine if damage is taken.
                //implemented in drawTwo function as that signals start of turn
                case BARREL:
                    playActiveCard(player, player, cardNum);
                    return true;
                //implemented in bang function,
                case SCOPE: //scope, you see others -1 distance
                    players[player].setRange(players[player].getRange()-1);
                    return playActiveCard(player, player, cardNum);

                case MUSTANG: //mustang, people see you +1 distance
                    return playActiveCard(player, player, cardNum);
                //implemented in distance checker method
                default:
                    return false;

            }
        }
    }

    public boolean playJail(int player,int target)
    {
        for (PlayableCard p : players[player].getCardsInHand())//iterates through entire hand of player
        {
            if (p.getCardNum() == JAIL) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                this.players[target].setActiveCard(new PlayableCard(true,JAIL));
                return true;
            }
        }
        return false;
    }

    //playing a card
    //for now this just removes the card from person playing its hand
    //as well as add it to the targets active cards
    public boolean playActiveCard(int player, int target, int card) {
        boolean flag = false;
        for(PlayableCard p : players[player].getCardsInHand()) { //checks to see if it is possible to play this card
            if(p.getCardNum() == card) { //checks players hand
                if(this.players[player].getActiveCard().getCardNum() == card) { //checks targets Active cards
                    players[player].getCardsInHand().remove(p);
                    flag = true;
                }
            }
        }
        if(flag)
        {
            if(card == JAIL) {
                if(players[target].getRole().getRole() != SHERIFF) {
                    players[target].setActiveCard(new PlayableCard(true, JAIL));
                    return true;
                }
                return false;
            }
            else if(card == BARREL) {
                players[target].setActiveCard(new PlayableCard(true,BARREL));
                return true;
            }
            else if(card == SCOPE) {
                players[target].setActiveCard(new PlayableCard(true, SCOPE));
                return true;
            }
            else if(card == MUSTANG) {
                players[target].setActiveCard(new PlayableCard(true, MUSTANG));
                return true;
            }
            else if(card == DYNAMITE) {
                players[target].setActiveCard(new PlayableCard(true, DYNAMITE));
                return true;
            }
        }
        return false;
    }

    //BANG card function:
    public boolean playBANG(int attacker, int target)//automatically uses the attacked player's missed card if found for now
    {
        if(target == -1) return false;
        if(bangsPlayed >= 1 && players[attacker].getCharacter().getCardNum()!=WILLYTHEKID)
            return false; //if player has used a bang and player character is not Willy The Kid, return false
        if (players[attacker].getRange() < distanceBetween(attacker, target))
            return false; //if the attacker's range if less than the distance to the target, return false;

        for(PlayableCard p: players[attacker].getCardsInHand())//iterates through entire hand of player
        {
            if(players[attacker].getCharacter().getCardNum() == CALAMITYJANET) { //checks if calamityjanets ability is applicable
                if (p.getCardNum() == BANG || p.getCardNum() == MISSED) {
                    bangsPlayed++; //increases the count of bangsPlayed by 1
                    players[attacker].getCardsInHand().remove(p);//removes bang card
                    discardPile.add(p);
                        if (this.players[attacker].getActiveCard().getCardNum() == BARREL) { //if they have a barrel try for miss
                            //if (drawExclamation(target, SPADES)) //CHANGE TO HEARTS WHEN SUIT IS FULLY IMPLEMENTED
                            {
                                return true; //if the draw! is successful then it exits without the target taking damage
                            }
                        }
                        if(players[target].getCharacter().getCardNum() == JOURDONNAIS)
                        {
                            //if(drawExclamation(target, SPADES)) //CHANGE TO HEARTS WHEN SUIT IS FULLY IMPLEMENTED
                            {
                                return true;
                            }
                        }
                    for (PlayableCard q : players[target].getCardsInHand()) {
                        if (q.getCardNum() == MISSED) {//if there exists a missed card in the attacked player's hand
                            players[target].getCardsInHand().remove(q);//check if it works - removes missed card if one exists in the attacked player
                            discardPile.add(q);
                            return true;
                            //SLAB THE KILLER disabled for now due to complexity
                            //if(!(players[attacker].getCharacter().getCardNum()==SLABTHEKILLER))
                            //{
                            //  return true;
                            //}
                        }
                    }
                }
            }
            if(p.getCardNum()== BANG)//if particular card is the cardnumber for bang, use it
            {
                bangsPlayed++; //increases the count of bangsPlayed by 1
                players[attacker].getCardsInHand().remove(p);//removes bang card
                discardPile.add(p);//searches through targets blue cards for barrel
                    if(this.players[attacker].getActiveCard().getCardNum() == BARREL) { //if
                       // if(drawExclamation(target, SPADES)) //this should actually check for hearts but the default suit is hearts so i made it spades
                        {
                            return true; //if the draw! is successful then it exits without the target taking damage
                        }
                    }
                    if(players[target].getCharacter().getCardNum() == JOURDONNAIS)
                    {
                        //if(drawExclamation(target, SPADES)) //CHANGE TO HEARTS WHEN SUIT IS FULLY IMPLEMENTED
                        {
                            return true;
                        }
                    }
                for(PlayableCard q: players[target].getCardsInHand())
                {
                    if(players[target].getCharacter().getCardNum() == CALAMITYJANET)
                    {
                        if(q.getCardNum() == MISSED || q.getCardNum() == BANG)
                        {
                            players[target].getCardsInHand().remove(q);
                            discardPile.add(q);
                            return true;
                        }
                    }
                    if(q.getCardNum()== MISSED) {//if there exists a missed card in the attacked player's hand
                        players[target].getCardsInHand().remove(q);//check if it works - removes missed card if one exists in the attacked player
                        discardPile.add(q);
                        return true;
                        //SLAB THE KILLER disabled for now due to complexity
                        //if(!(players[attacker].getCharacter().getCardNum()==SLABTHEKILLER))
                        //{
                        //  return true;
                        //}
                    }
                }
                //else, no missed cards are found
                players[target].setHealth(players[target].getHealth()-1); //decreases health of target player

                //if target's character is El Gringo, takes card from the attacker
                if(players[target].getCharacter().getCardNum()==ELGRINGO)
                {
                    drawFromPlayer(target,attacker);
                }
                //if target's character is Bart Cassidy, draws a card from deck
                else if(players[target].getCharacter().getCardNum()==BARTCASSIDY)
                {
                    draw(target);
                }
                return true;
            }
        }
        return false;//after searching through entire hand, if bang card not found, exits
    }

    public boolean playStagecoach(int player) {
        for (PlayableCard p : players[player].getCardsInHand())//iterates through entire hand of player
        {
            if (p.getCardNum() == STAGECOACH) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                draw(player);
                draw(player);
                return true;
            }
        }
        return false;
    }

    public boolean playWellsfargo(int player) {
        for (PlayableCard p : players[player].getCardsInHand())//iterates through entire hand of player
        {
            if (p.getCardNum() == WELLSFARGO) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                draw(player);
                draw(player);
                draw(player);
                return true;
            }
        }
        return false;
    }

    //method to play the Duel card:
    public boolean playDuel(int player, int target) {
        //initializes counts of bang cards in attacker's and target's hands to 0
        for (PlayableCard q : players[player].getCardsInHand())//iterates through entire hand of player
        {
            if (q.getCardNum() == DUEL) {
                players[player].getCardsInHand().remove(q);
                discardPile.add(q);
                int attackerCount = 0, targetCount = 0;
                //counts how many bangs are in attacker's and target's hands
                for (PlayableCard p : players[player].getCardsInHand()){
                    if (p.getCardNum() == BANG) attackerCount++;
                }
                for (PlayableCard p : players[target].getCardsInHand()){
                    if (p.getCardNum() == BANG) targetCount++;
                }

                if (attackerCount <= targetCount) //if attacker has less than or same amount BANGs as target, attacker loses
                    players[player].setHealth(players[player].getHealth() - 1); //lose one health
                else
                    players[target].setHealth(players[target].getHealth() - 1); //else if attacker has more BANGs than target, target loses one health
                return true;
            }
        }
        return false;
    }

    //method to play gatling card; called from playCard method; deals damage to everyone except attacker
    public boolean playGatling(int player) {
        //checks for the user and deals damage accordingly
        for (PlayableCard p : players[player].getCardsInHand())//iterates through entire hand of player
        {
            if (p.getCardNum() == GATLING) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                for (int i = 0; i < 4; i++) {
                    if (player != i) {
                        players[i].setHealth(players[i].getHealth() - 1);
                    }
                }
                return true;
            }
        }
        return false;
    }


    //function to play Indians card:
    public boolean playIndians(int player){
        boolean hasBANG;
        for(PlayableCard p: players[player].getCardsInHand())
        {
            if(p.getCardNum() == INDIANS) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                for(int i = 0; i < 4; i++)
                {
                    hasBANG = false;
                    if(i != player)
                    {
                        for(PlayableCard q: players[i].getCardsInHand())
                        {
                            if(q.getCardNum() == BANG && hasBANG == false)
                            {
                                players[i].getCardsInHand().remove(q);
                                discardPile.add(q);
                                hasBANG = true;
                            }
                        }
                        if(hasBANG == false)
                        {
                            players[i].setHealth(players[i].getHealth()-1);
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean playPanic(int player, int target)
    {
        if(distanceBetween(player, target) == 1)
        {
            for(PlayableCard p: players[player].getCardsInHand()) {
                if(p.getCardNum() == PANIC) {
                    players[player].getCardsInHand().remove(p);
                    discardPile.add(p);
                    if(players[target].getCardsInHand().isEmpty())
                    {
                        return true;
                    }
                    else
                    {
                        drawFromPlayer(player, target);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /*target player discards a card,
    however currently the card is prechosen, this could be changed lated to allow for target to choose, or attacker to choose.
     */
    public boolean playCatBalou(int player, int target)
    {
        for(PlayableCard p: players[player].getCardsInHand()) {
            if (p.getCardNum() == CATBALOU) {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                if(players[target].getCardsInHand().isEmpty())
                {
                    return true;
                }
                else
                {
                    players[target].getCardsInHand().remove(0); //removes the left most card in targets hand
                    return true;
                }
            }
        }
        return false;
    }

    //Beer card function:
    public boolean playBeer(int player)
    {
        //This card lets a player regain one life point - slide the card so that one more bullet is shown.
        // A player cannot gain more life points than his starting amount! The Beer cards cannot be used to help other players.
        if(players[player].health >= players[player].getMaxHealth()) return false; //checks that user does not surpass the max health
        for(PlayableCard p: players[player].getCardsInHand())//iterates through entire players hand
        {
            if(p.getCardNum()==BEER)//if cardnum for beer, uses it
            {
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                players[player].setHealth(players[player].getHealth()+1); //adds one life point to user
                return true; //returns true, showing that the move was successful
            }
        }
        return false;//else, returns false
    }

    //used when playing saloon card; called from playCard; heals everyone 1 health. heals the user an additional one health.
    public boolean playSaloon(int player) {
        for(PlayableCard p: players[player].getCardsInHand()){
            if(p.getCardNum() == SALOON){
                players[player].getCardsInHand().remove(p);
                discardPile.add(p);
                for(int i =0; i< 4; i++){
                    if(i != player)
                        players[i].setHealth(players[i].getHealth() + 1);
                    else
                        players[i].setHealth((players[i].getHealth() + 2));
                }
                return true;
            }
        }
        return false;
    }

    public boolean useAbility(int player, int ability) {
        if(playerTurn != player)
            return false;
        else {
            switch(ability) {
                case PAULREGRET: //paul regret - +1 distance seen
                    /*
                    Implemented within the find distance method
                    COMPLETED
                     */
                    return true;
                case JOURDONNAIS: //jourdonnais - if draw heart when BANG'd, MISS'd
                    //Implement within the bang method
                    //IMPLEMENT - check last card they drew
                    //CHARACTER ABILITY SKIPPED DUE TO SUIT/NUMBER COMPLEXITY -
                    /*
                    Implemented within the bang method
                     */
                case BLACKJACK: //black jack - shows second card drawn, if heart or diamond, draws another card
                    //Implement within the draw two function, no functionality for showing card yet

                case SLABTHEKILLER: //slab the killer - other player needs 2 misses to cancel bang from him
                    //COMPLETED, happens during playBANG

                case ELGRINGO: //el gringo - anytime hit, draws card from player
                    //COMPLETED, happens during playBANG

                case JESSEJONES: //jesse jones - draw first card from selected players hand
                    //COMPLETED, happens during drawTwo

                case SUZYLAFAYETTE: //suzy lafayette - soon as there are no cards in hand, draws new one
                    if (players[player].getCardsInHand().isEmpty())
                    {
                        draw(player);
                    }
                    return true;

                case WILLYTHEKID: //willy the kid - can play any number of bangs
                    //COMPLETED, happens during playBANG

                case ROSEDOOLAN: //rose doolan - sees all players distance -1, PROBLEM - this sets this application to everyone, maybe add a distance to playerinfo instead?
                    players[player].setRange(players[player].getRange() + 1);
                    return true;

                case BARTCASSIDY: //bart cassidy - each time hit, draws a card
                    //COMPLETED, found alongside ELGRINGO in playBANG

                case PEDRORAMIREZ: //pedro ramiree - draws first card from discard pile
                    //JOHNNY CURRENTLY DOING

                case SIDKETCHUM: //sid ketchum -  can discard 2 cards to regain one life
                    //IMPLEMENT - discard two cards
                    players[player].setHealth(players[player].getHealth()+1);
                    return true;

                case LUCKYDUKE: //lucky duke - anytime draws, flips first two cards up and chooses one
                    //IMPLEMENT - new draw system for him
                    //implemented in drawExclamation method
                    //simplified so if either the first two cards matches needed suit then he succeeds

                case VULTURESAM: //vulture sam - whenever player eliminated, take all their cards
                    //IMPLEMENT - when player health 0, activate
                    /*
                    Currently implementing in drawTwo method to check if the current player has 0 health, this could change in future
                     */
                case CALAMITYJANET: //calamity janet - play bangs as miss and vice versa
                    //IMPLEMENT - during battle phase
                    /*
                    implemented in playBang method.
                    Currently reduced function, will remove the first instance of either a bang of missed when using either
                    Will not work with gatling or Indians currently
                     */

                case KITCARLSON: //kit carlson - looks at top three and draws two if drawTwo
                    //IMPLEMENT - during draw phase
                    /*
                    Currently do not have a way to examine cards
                     */

                default:
                    return false;
            }
        }
    }

    //function to endTurn
    public boolean endTurn(){ //ends the turn, determines next player
        //TODO: allow player to choose the cards discarded
        //TODO: fix out of bounds array
        for(int i = players[playerTurn].getCardsInHand().size()-1; i >= players[playerTurn].getHealth();i--){
            players[playerTurn].getCardsInHand().remove(i);
        }
        if(playerTurn != 3) playerTurn ++;
        else playerTurn = 0;

        drawTwo(playerTurn);
        bangsPlayed = 0;
        return true;
    }

    //method to examine card(name and description):
    public boolean examineCard(Card card)
    {
        System.out.println(card.toString());//for now: prototype
        return true;
    }

    //exists program, for now;prototype
    public void quitGame()
    {
        System.exit(0);
    }

    //getter method for missed card
    public void setMissedCard(boolean missedCard){
        this.missedCard = missedCard;
    }

    //setter method for missed card
    public boolean getMissedCard(){
        return missedCard;
    }



    //toString method:
    public String toString()
    {
        //DRAWPILE CARDS ARE MOSTLY NULL! For now...
        String string = "\tDraw pile:\n";
        for(PlayableCard c: drawPile) string +=  c.toString(); //concatenates strings of the draw pile
        string += "\tDiscard pile:\n";
        for(PlayableCard c: discardPile) string += "\t\t" + c.toString(); //concatenates strings of the discard pile
        string += "\tPlayers:\n";
        for(PlayerInfo p: players) string += p.toString() + "\n"; //concatenates strings of players
        string += "\t\tCurrent player turn: "+playerTurn+"\n"; ///concatenates player turn
        string += "\t\tBANGs played: "+bangsPlayed+"\n"; //concatenates current BANGs played
        return string;

    }
}
