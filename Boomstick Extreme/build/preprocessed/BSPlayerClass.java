/*
 * BSPlayerClass.java
 *
 * Created on May 11, 2007, 10:08 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
/**
 * The PlayerClass
 *
 * @author Brian Young
 */
public class BSPlayerClass {
    
    int sHP;
    int sAmmo;
    int sGuage;
    int sAtkDamage;
    int sScore;
    
    /** Creates a new instance of BSPlayerClass */
    public BSPlayerClass() 
    {
       
    }
    public boolean PlayerConditionCheck()
    {
        if (sAmmo>0)
            return true;
        else
            return false;
    }
    //BASE: Untested
    //Deduct health from player
    public void Damage(int theDamage)
    {
        sHP-=theDamage;
    }
    //BASE: Untested
    //Add score to player
    public void AddScore(int theScore)
    {
        sScore+=theScore;
    }
    //NULL
    //add item to inventory
    public void AddtoInventory (int amount, String ItemType)
    {
        
        
    }
    //BASE: Untested
    //Returns values of stats
    public int ReturnStats(int theStat)
    {
        switch(theStat)
        {
            case 0:
                return sHP;
            case 1:
                return sAmmo;
            case 2:
                return sGuage;
            case 3:
                return sAtkDamage;
            case 4:
                return sScore;
            default:
                System.err.println("no such stat");
                return 0;
        }
    }
    //NULL
    //Detects Key input and sends a signal to the hole manager
    void KeyDetect(int code)
    {
        if ((code>49)&&(code<57))
        {
            //attack button was pressed
        }
      
    }
    
}
