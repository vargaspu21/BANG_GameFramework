package com.example.davidvargas.bang_gameframework.objects;

import com.example.davidvargas.bang_gameframework.R;

/**
 * Class for aligned roles. Assigned goal depends on this.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class RoleCard extends Card {

    public int role; //0: Sheriff, 1: Outlaw, 2: Renegade

    public final int SHERIFF = 0;
    public final int OUTLAW = 2;
    public final int RENEGADE = 1;

    //constructor:
    public RoleCard(int roleNum)
    {
        super(roleNum);
        role = roleNum;
        switch (roleNum){
            case SHERIFF:
                name = "Sheriff";
                description = "The goal is to eliminate all the Outlaws and the Renegade, to protect law and order.\n";
                resourceId = R.drawable.sheriff;
                break;
            case RENEGADE:
                name = "Renegade";
                description = "The goal is to be the last character in play.\n";
                resourceId = R.drawable.renegade;
                break;
            case OUTLAW:
                name = "Outlaw";
                description = "The goal is to kill the Sheriff.\n";
                resourceId= R.drawable.outlaw;
                break;

        }
    }

    //copy constructor for RoleCard object:
    public RoleCard(RoleCard c){
        super(c);
        role = c.role;
    }

    //setter method for role
    public void setRole(int roleNum)
    {
        role = roleNum;
    }

    //getter method for role
    public int getRole()
    {
        return role;
    }

    //toString method:
    public String toString()
    {
        //convert role number to string role:
        if(role == SHERIFF) return "\t\t\t\tThe role is a Sheriff\n";
        else if(role == RENEGADE) return "\t\t\t\tThe role is an Outlaw\n";
        else return "\t\t\t\tThe role is a Renegade\n";

    }
}
