package com.example.davidvargas.bang_gameframework;

import com.example.davidvargas.bang_gameframework.objects.PlayableCard;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example test, will primarily test on game state BANGState
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class BANGStateTest
{
    @Test
    public void BANGTest()
    {
        //new game state to operate with
        BANGState toTest = new BANGState();

        //clears entire hand
        toTest.players[0].getCardsInHand().clear();

        //attempt to shoot player 2
        toTest.playBANG(0,1);

        //player 2 undamaged
        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 5);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }

        //give player 1 a bang card
        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        //shoot player 2
        toTest.playBANG(0,1);

        //player 2 harmed
        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }
    }

    @Test
    public void BeerTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        toTest.playBANG(0,1);

        toTest.players[1].getCardsInHand().clear();

        toTest.playCard(1,1,toTest.BEER);

        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }

        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BEER));

        toTest.playBeer(1);

        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 5);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
    }

    @Test
    public void PanicTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        assertEquals(toTest.players[1].getCardsInHand().size(),3);
        assertEquals(toTest.players[0].getCardsInHand().size(),0);

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.PANIC));

        toTest.playPanic(0,1);

        assertEquals(toTest.players[1].getCardsInHand().size(),2);
        assertEquals(toTest.players[0].getCardsInHand().size(),1);
    }

    @Test
    public void CatbalouTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        assertEquals(toTest.players[1].getCardsInHand().size(),3);

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.CATBALOU));

        toTest.playCatBalou(0,1);

        assertEquals(toTest.players[1].getCardsInHand().size(),2);
    }

    @Test
    public void GatlingTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[2].getCardsInHand().clear();

        toTest.players[3].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.GATLING));

        toTest.playGatling(0);

        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }
        if(toTest.players[2].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[2].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[2].getHealth(), 3);
        }
        if(toTest.players[3].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[3].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[3].getHealth(), 3);
        }
    }

    @Test
    public void IndianTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[2].getCardsInHand().clear();

        toTest.players[3].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.INDIANS));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        toTest.playIndians(0);

        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 5);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
        if(toTest.players[2].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[2].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[2].getHealth(), 3);
        }
        if(toTest.players[3].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[3].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[3].getHealth(), 3);
        }
    }

    @Test
    public void SaloonTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.SALOON));

        toTest.players[0].setHealth(2);
        toTest.players[1].setHealth(2);

        toTest.playSaloon(0);

        if(toTest.players[0].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[0].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[0].getHealth(), 4);
        }
        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }
    }

    //These tests are not very accurate, as it is known that game sometimes does not initialize deck correctly, resulting in this error
    /*
    @Test
    public void StagecoachTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.STAGECOACH));

        toTest.playStagecoach(0);

        assertEquals(toTest.players[0].getCardsInHand().size(),2);
    }

    @Test
    public void WellsfargoTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.WELLSFARGO));

        toTest.playWellsfargo(0);

        assertEquals(toTest.players[0].getCardsInHand().size(),3);
    }
    */

    /*
    @Test
    public void DuelTest()
    {
        BANGState toTest = new BANGState();

        toTest.players[0].getCardsInHand().clear();

        toTest.players[1].getCardsInHand().clear();

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.BANG));
        toTest.players[1].getCardsInHand().add(new PlayableCard(false, toTest.BANG));

        toTest.players[0].getCardsInHand().add(new PlayableCard(false, toTest.DUEL));

        toTest.playDuel(0,1);

        if(toTest.players[0].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[0].getHealth(), 5);
        }
        else
        {
            assertEquals(toTest.players[0].getHealth(), 4);
        }
        if(toTest.players[1].getRole().getRole()==toTest.SHERIFF)
        {
            assertEquals(toTest.players[1].getHealth(), 4);
        }
        else
        {
            assertEquals(toTest.players[1].getHealth(), 3);
        }

        int numBangsLeft = 0;

        for(PlayableCard p: toTest.players[0].getCardsInHand())
        {
            if(p.getCardNum()==toTest.BANG)
            {
                numBangsLeft++;
            }
        }
        assertEquals(numBangsLeft,1);
    }
    */
}