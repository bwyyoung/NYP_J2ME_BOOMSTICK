import java.util.Random;
/*
 * BSPlayerModel.java
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
// 
public class BSPlayerModel 
{
    BSMainApp mApp;
   
    /*weapon {DAMAGE, BARRELSIZE, STOCK, LEVELID); */
    public final static int [] FRENZYFINGERS = {1,-100,-100,0};
    public final static int [] BOOMSTICK     = {2,10,2,1};
    public final static int [] NAPALM        = {2,1 ,2,2};
    public final static int [] GRAPESHOT     = {2,1 ,2,2};
    public final static int [] ARCTIC         = {2,1 ,2,2};
   
    public final static int FINGERSNAME = 0;
    public final static int BOOMSTICKNAME = 1;
    public final static int NAPALMNAME  = 2;
    public final static int GRAPESHOTNAME = 3;
    public final static int ARCTICNAME = 4;
    
    
    int sHP;
    int WeaponName;
    public int sAmmo;
    public int sBarrelSize;
    public int sAtkDamage;
    
    Random NewWeapon;
    
    int sStock;
    int sGuage;
    int sGuageMax;
    int sGuageCap;
    int sLevel;
    
    int sScore;
    
    boolean Delay;
    long time;
    BSPlayerView View;
    
    /**
     * Creates a new instance of BSPlayerModel
     */
    public BSPlayerModel(BSMainApp theApp) 
    {
        mApp=theApp;
        View= new BSPlayerView(theApp);
        Delay=false;
        
        NewWeapon = new Random();
        sHP=10;
        
        SetCurrentWeapon(FRENZYFINGERS, 0);
        
        sGuage=0;
        sGuageMax=10;//maximum luna guage size
        sGuageCap=2;//maximum level
        sAtkDamage=1;
        sLevel=0;
        sScore=0;
        time=0;
        
       
    }
    void Update()
    {
        View.Update();
        DeductLuna();
    }
    public void PlayerRender()
    {
        View.Render();
        mApp.mGameCanvas.mGraphics.drawBMString("Ammo " +Integer.toString(sAmmo),170,20,0);
        mApp.mGameCanvas.mGraphics.drawBMString("Stock "+Integer.toString(sStock),170,30,0);
        mApp.mGameCanvas.mGraphics.drawBMString("Guage "+Integer.toString(sGuage)+" /"+Integer.toString(sGuageMax),170,40,0);
        mApp.mGameCanvas.mGraphics.drawBMString("Level "+Integer.toString(sLevel),170,50,0);
        mApp.mGameCanvas.mGraphics.drawBMString("System time "+Long.toString(System.currentTimeMillis()),160,60,0);
    }
    //BASE: Untested
    //Deduct health from player
    public void Damage(int theDamage)
    {
        sHP-=theDamage;
        if (sHP<=0)
        {
            System.out.println("Player is Dead");
        }
    }
    //BASE: Untested
    //Returns values of stats
    public int ReturnStats (int theStat)
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
            case 5:
                return sBarrelSize;
            case 6:
                return sStock;
            default:
                System.err.println("no such stat");
                return 0;
        }
    }
    //NULL
    //Detects Key input and sends a signal to the hole manager
    void KeyDetect(int code)
    {
        System.out.println(code);
        if ((code>=49)&&(code<=57))
        {
            if(View.GetCurrentState()!=BSPlayerView.ReloadState)
            {
                View.RenderShoot(code);
               
                //if(sLevel!=0)
                DeductAmmo();

                if (mApp.mGameCanvas.mHoleManager.mHoles[code-48].GetEntity()==BSHole.HOLE_FILLED)//Problem with multiple key input
                {
                   sGuage++;
                   System.out.println("sGuage Increase!");
                   Delay=true;
                   time=System.currentTimeMillis();
                }
                else
                {
                    
                }
            }
        }
        AddLuna();
    }
    public void GetPowerUp (String ItemType)
    {
        if (ItemType=="Life")
            sHP+=1;
        else if (ItemType=="Ammo")
            sAmmo+=1;
        else if (ItemType=="Poo")
            sHP-=1;
        else if (ItemType=="FriedChunks")
            sGuage+=1;
    }
    public void SetParameters(int level)
    {
        
        switch(level)
        {
            case 0:
                SetCurrentWeapon(FRENZYFINGERS, 0);
                break;
            case 1:
                SetCurrentWeapon(BOOMSTICK, 1);
                break;
            case 2:
                int TheWeapon=NewWeapon.nextInt(3)+2;
                switch(TheWeapon)
                {
                    case 2:
                        SetCurrentWeapon(NAPALM, 2);
                        break;
                    case 3:
                        SetCurrentWeapon(GRAPESHOT, 2);
                        break;
                    case 4:
                        SetCurrentWeapon(ARCTIC, 2);
                        break;
                }
                break;
        }
    }
    public void SetCurrentWeapon(int []a, int name)
    {
        WeaponName=name;
        sAtkDamage=a[0];
        sBarrelSize=a[1];
        sAmmo=sBarrelSize;
        sStock=a[2];
        View.ResetWeapon(name);
    }
    public void AddScore(int theScore)
    {
        sScore+=theScore;
    }
    public void AddLuna()
    {
         
        if (sGuage>=sGuageMax)
        {
            if (sLevel==3)
            {
                sGuage=sGuageMax-1;
                sLevel=2;
            }
            else 
            {
                sLevel++;
                SetParameters(sLevel);
                sGuage=0;
                Delay=true;
                time=System.currentTimeMillis();
            }
        }
        else if (sGuage<0)
        {
            if (sLevel>0)
            {
                sLevel--;
                sGuage=sGuageMax-1;
            }
        }
     
    }
    public void DeductLuna()
    {
        if (Delay)
        {
            long change=(System.currentTimeMillis()-time)/1000;
            if (change>1)
            {
                Delay=false;
                time=System.currentTimeMillis();
            }
        }
        if (!Delay)
        {
            long diff=(System.currentTimeMillis()-time)/1000;
            if (diff>1)
            {
                if(sLevel>=0)
                {
                    if((sLevel==0)&&(sGuage<=0))
                    {}
                    else
                    {
                        sGuage--;
                        time=System.currentTimeMillis();
                        if (sGuage<=0)
                        {
                            if (sLevel>0)
                            {
                                sLevel--;
                                SetParameters(sLevel);
                                sGuage=sGuageMax-1;
                            }
                        }
                    }
                }
            }
        }   
    }
    public void DeductAmmo()
    {
        if(WeaponName!=FINGERSNAME)
        {
            if (sAmmo>0)
            {
                --sAmmo;
                System.out.println("Shoot!" + sAmmo);
            }
            else if(sAmmo<=0)
            {
                if (sStock>0)
                {
                    --sStock;
                    View.RenderReload();
                    sAmmo=sBarrelSize;
                    //System.out.println("Reload! "+ sStock);
                }
                else
                {
                    System.out.println("GAME OVER");
                }
            }
        }
    }
}
