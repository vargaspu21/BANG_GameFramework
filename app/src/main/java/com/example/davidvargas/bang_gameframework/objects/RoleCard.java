package com.example.davidvargas.bang_gameframework.objects;

import com.example.davidvargas.bang_gameframework.R;

/**
 * Class for aligned roles. Assigned goal depends on this.
 *
 * @author David Vargas
 * @author Johnny Huang
 * @version November 2018
 */

public class RoleCard extends Card
{
    //0: Sheriff, 1: Outlaw, 2: Renegade
    public int role;

    public static final int SHERIFF = 0;
    public static final int OUTLAW = 1;
    public static final int RENEGADE = 2;

    //constructor:
    public RoleCard(int roleNum)
    {
        super(roleNum);
        role = roleNum;
        switch (roleNum){
            case SHERIFF:
                name = "Sheriff";
                description = "Goal: Eliminate all outlaws and renegades!\n";
                resourceId = R.drawable.sheriff_copy;
                break;
            case RENEGADE:
                name = "Renegade";
                description = "Goal: Be the last player standing!\n";
                resourceId = R.drawable.renegade;
                break;
            case OUTLAW:
                name = "Outlaw";
                description = "Goal: Eliminate the sheriff!\n";
                resourceId= R.drawable.outlaw;
                break;
        }
    }

    //copy constructor for RoleCard object:
    public RoleCard(RoleCard c)
    {
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
