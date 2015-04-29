import java.util.Random;
import java.lang.Math;
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
    public final static int [] FRENZYFINGERS = {1,0,0,0};
    public final static int [] BOOMSTICK     = {2,12,2,1};
    
    public final static int [] NAPALM        = {2,2,2,2};
    public final static int [] GRAPESHOT     = {2,2,2,2};
    public final static int [] ARCTIC        = {2,2,2,2};
   
    public final static int FINGERSNAME   = 0;
    public final static int BOOMSTICKNAME = 1;
    public final static int NAPALMNAME    = 2;
    public final static int GRAPESHOTNAME = 3;
    public final static int ARCTICNAME    = 4;
    
    int Framerate=BSCanvas.MSPF;
    int sHP;
    int sHPMax;
    int WeaponName;
    int NumOfHamstersKilled;
    public int sAmmo,sAmmoMax;
    public int sBarrelSize;
    public int sAtkDamage;
    
    Random NewWeapon;
    long DudeCount;
    long CurrentDudeCount;
    int sStock;
    int sCombo;
    float sGuage;
    float sGuageMax;
    
    boolean Miss;
    boolean DelayLevel;
    int sGuageCap;
    int sLevel;
    int Position;
    
    int sScore;
    
    boolean Delay;
    long time;
    BSPlayerView View;
    BSPlayerHUD HUD;
    
    //BSSound ReloadSound;
    
    /*
     * Creates a new instance of BSPlayerModel
     */
    public BSPlayerModel(BSMainApp theApp) 
    {
        mApp=theApp;
        View= new BSPlayerView(theApp);
        HUD=new BSPlayerHUD(theApp);
        Delay=false;
        NewWeapon = new Random();
        sHP=sHPMax=4;
        sAmmoMax=12;
        DudeCount=0;
        SetCurrentWeapon(FRENZYFINGERS, 0);
        sCombo=0;
        sGuage=0;
        sGuageMax=40;//maximum luna guage size
        sGuageCap=2;//maximum level
        sLevel=0;
        sScore=0;
        time=0;
        Position=0;
        Miss=false;
        DelayLevel=false;
        NumOfHamstersKilled=0;
        
        //ReloadSound = mApp.mGameCanvas.mResources.GetSoundThrow("Reload");
        
        HUD.SetData(WeaponName,sHP,sHPMax,(float)sGuage/(float)sGuageMax,sLevel,sAmmo,sAmmoMax,sStock,sScore,sCombo);
    }
    void AddStock(int theStock)
    {
        sStock=theStock;
    }
    void Update()
    {
        View.Update();
        HUD.HUDUpdate();
        HUD.SetData(WeaponName,sHP,sHPMax,(float)sGuage/(float)sGuageMax,sLevel,sAmmo,sAmmoMax,sStock,sScore,sCombo);
        DeductLuna();
      
    }
    public void PlayerRender()
    {
        View.Render();
        HUD.HUDRender();
        
        if (sCombo>1)
            mApp.mGameCanvas.mGraphics.drawBMString("Combo " + Integer.toString(sCombo),170,60,0);
        long CurrentDudeCount = (System.currentTimeMillis()-DudeCount);
       
        if ((CurrentDudeCount>=0)&&(CurrentDudeCount<1500))
        {
//            System.out.println(CurrentDudeCount);
//            System.out.println("Position "+Position);
            if (Position<=5)
            {
                HUD.CoolDude.Translate(20,0);
            }
            else if (Position>10)
            {
                HUD.CoolDude.Translate(-20,0);
            }
            if (Position>=15)
            {
                DudeCount=0;
                Position=0;
            }
            Position++;
        }
        else
        {
            HUD.CoolDude.setPosition(-150,0);
            DudeCount=0;
            Position=0;
        }
        HUD.CoolDude.Render();
//        mApp.mGameCanvas.mGraphics.drawBMString("Ammo " +Integer.toString(sAmmo),170,20,0);
//        mApp.mGameCanvas.mGraphics.drawBMString("Stock "+Integer.toString(sStock),170,30,0);
//        mApp.mGameCanvas.mGraphics.drawBMString("Guage "+Float.toString(sGuage)+" /"+Float.toString(sGuageMax),170,40,0);
//        mApp.mGameCanvas.mGraphics.drawBMString("Level "+Integer.toString(sLevel),170,50,0);
//        mApp.mGameCanvas.mGraphics.drawBMString("System time "+Long.toString(System.currentTimeMillis()),160,60,0);
    }
    //BASE: Untested
    //Deduct health from player
    public void Damage(int theDamage)
    {
        
        if ((sHP>0)&&((sHP-theDamage)<=sHPMax))
          sHP-=theDamage;
        if (sHP<=0)
        {
            mApp.mGameCanvas.mLostGame = true;
            //System.out.println("Player is Dead");
        }
        System.out.println("HP left "+sHP);
    }
    boolean GetMiss()
    {
        return Miss;
    }
    void Fire(int thecode)
    {
        if (thecode == mApp.mMenuManager.B_S1)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(0, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S2)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(1, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S3)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(2, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S4)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(3, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S5)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(4, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S6)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(5, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S7)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(6, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S8)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(7, sAtkDamage, WeaponName);
        if (thecode == mApp.mMenuManager.B_S9)
            mApp.mGameCanvas.mHoleManager.HoleIsShot(8, sAtkDamage, WeaponName);
    }
    //NULL
    //Detects Key input and sends a signal to the hole manager
    void KeyDetect(int code)
    {
        //System.out.println(Math.abs(code-mApp.mMenuManager.B_S1));
        int hid = 0;
        if (code == mApp.mMenuManager.B_S1)
            hid = 0;
        if (code == mApp.mMenuManager.B_S2)
            hid = 1;
        if (code == mApp.mMenuManager.B_S3)
            hid = 2;
        if (code == mApp.mMenuManager.B_S4)
            hid = 3;
        if (code == mApp.mMenuManager.B_S5)
            hid = 4;
        if (code == mApp.mMenuManager.B_S6)
            hid = 5;
        if (code == mApp.mMenuManager.B_S7)
            hid = 6;
        if (code == mApp.mMenuManager.B_S8)
            hid = 7;
        if (code == mApp.mMenuManager.B_S9)
            hid = 8;
        
        System.out.println(hid);
        if ((code>=49)&&(code<=57))
        {
            if(View.GetCurrentState()!=BSPlayerView.ReloadState)
            {
                View.RenderShoot(code);

                DeductAmmo();
                if (mApp.mGameCanvas.mHoleManager.mHoles[hid].GetEntity()==BSHole.HOLE_EMPTY)
                {
                    sCombo=0;
                    Miss=true;
                }
                else
                {
                    if (mApp.mGameCanvas.mHoleManager.mHoles[hid].GetEntity()==BSHole.ENTITY_JUMPSTER && 
                            ((BSMonsterClass)mApp.mGameCanvas.mHoleManager.mHoles[hid].Holes[mApp.mGameCanvas.mHoleManager.mHoles[hid].DrawIndex]).mAlive)//Problem with multiple key input
                    {
                       AddScore(50);
                       sGuage+=4;
                       Delay=true;
                       time=System.currentTimeMillis();

                       NumOfHamstersKilled++;

                       if (sCombo+1==5)
                        {
                            ComboCheck();
                        }
                        sCombo++;

                    }
                    else if (mApp.mGameCanvas.mHoleManager.mHoles[hid].GetEntity()==BSHole.ENTITY_POPSTER &&
                            ((BSMonsterClass)mApp.mGameCanvas.mHoleManager.mHoles[hid].Holes[mApp.mGameCanvas.mHoleManager.mHoles[hid].DrawIndex]).mAlive)
                    {   
                       AddScore(100);
                       sGuage+=6;
                       Delay=true;

                       NumOfHamstersKilled++;

                       time=System.currentTimeMillis();  
                       if (sCombo+1==5)
                        {
                            ComboCheck();
                        }
                        sCombo++;

                    }

                    
                    
                    if (((sCombo+1)%20.0==0)&&(sCombo>19))
                    {
                        ComboCheck();
                    }
                    sCombo++;

                }
            }
           
        }
        else if ((code==35)&&(mApp.is_RightHanded))
        {
            ManualReload();
        }
        AddLuna();
        Fire(code);
    }
    public void ComboCheck()
    {
           HUD.CoolDude.playSequence(1,true,Framerate);
           DudeCount=System.currentTimeMillis();
    }
    public void GetPowerUp (String ItemType)
    {
        if (ItemType=="Life")
            if (sHP<4)
                sHP+=1;
        else if (ItemType=="Ammo")
            sStock+=1;
        else if (ItemType=="Poo")
            sHP-=1;
        else if (ItemType=="FriedChunks")
            sGuage+=1;
    }
    public void SetParameters(int level)
    {
        if(View.CurrentState!=View.ReloadState)
        {
            switch(level)
            {
                case 0:
                    sGuageMax=40;
                    View.CurrentY=130;
                    SetCurrentWeapon(FRENZYFINGERS, 0);
                    break;
                case 1:
                    sGuageMax=20;
                    View.CurrentY=130;
                    SetCurrentWeapon(BOOMSTICK, 1);
                    break;
                case 2:
                    sGuageMax=20;
                    View.CurrentY=110;
                    AddOnWeapon(NAPALM, 2);
    //                int TheWeapon=NewWeapon.nextInt(3)+2;
    //                switch(TheWeapon)
    //                {
    //                    case 2:
    //                        AddOnWeapon(NAPALM, 2);
    //                        break;
    //                    case 3:
    //                        AddOnWeapon(GRAPESHOT,2);
    //                        break;
    //                    case 4:
    //                        AddOnWeapon(ARCTIC, 2);
    //                        break;
    //                }
                    break;
            }
        }
    }
    public void AddOnWeapon(int []a, int name)
    {
        WeaponName=name;
        if((sAmmo+a[1])>sBarrelSize)
        {
            sAmmo=(sAmmo+a[1])-sBarrelSize;
            sBarrelSize+=(sAmmo+a[1])%sBarrelSize;
        }
        else
        {
            sAmmo+=a[1];
        }
        sStock+=a[2];
        View.ResetWeapon(name);
        
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
            if ((sLevel+1)==3)
            {
                sGuage=sGuageMax-1;
                sLevel=2;
            }
            else 
            {
                sLevel++;
                SetParameters(sLevel);
                sGuage=0;
                DelayLevel=true;
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
        if (!DelayLevel)
        {
            if (Delay)
            {
                long change=(System.currentTimeMillis()-time);
                if (change>750)
                {
                    Delay=false;
                    time=System.currentTimeMillis();
                }
            }
            if (!Delay)
            {
                long diff=(System.currentTimeMillis()-time);
                if (diff>750)
                {
                    if(sLevel>=0)
                    {
                        if((sLevel==0)&&(sGuage<=0))
                        {
                        }
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
        else
        {
             long thediff=(System.currentTimeMillis()-time);
             
             if (thediff>5000)
             {
                 DelayLevel=false;
                 
             }
        
        }
    }
    public void ManualReload()
    {
        if((WeaponName!=0)&&(sStock>0)&&(sAmmo!=sBarrelSize))
        {
            sAmmo=sBarrelSize;
            sStock--;
            View.RenderReload();
        }
            
    }
    public void DeductAmmo()
    {
        if(WeaponName!=FINGERSNAME)
        {
            if (sAmmo>0)
            {
                --sAmmo;
            }
            else if(sAmmo<=0)
            {
                if (sStock>0)
                {
                    --sStock;
                    //ReloadSound.Play();                            
                    View.RenderReload();
                    sAmmo=sBarrelSize;
                    //System.out.println("Reload! "+ sStock);
                }
                else if (sStock<=0)
                {
                    sLevel=0;
                    sGuage=0;
                    SetParameters(sLevel);
                }
              
            }
        }
        
    }
}
