package com.example.davidvargas.bang_gameframework.objects;

public class RoleCard extends Card {

    private int role; //0: Sheriff, 1: Outlaw, 2: Renegade

    //constructor:
    public RoleCard(int roleNum)
    {
        role = roleNum;
    }

    //copy constructor for RoleCard object:
    public RoleCard(RoleCard c){ role = c.role;}

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
        if(role == 0) return "\t\t\t\tThe role is a Sheriff\n";
        else if(role == 1) return "\t\t\t\tThe role is an Outlaw\n";
        else return "\t\t\t\tThe role is a Renegade\n";

    }
}
