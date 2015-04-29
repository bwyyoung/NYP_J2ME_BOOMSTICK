import javax.microedition.lcdui.Graphics;
/*
 * BSHUD.java
 *
 * Created on 22 Jun 2007, AM 04:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class BSPlayerHUD {
    
    int CentreX,CentreY;
 
    int Luna;
    float LunaLength;
    float MaxLunaLength;
    int HP,MaxHP;
    int Combo;
    int CurrentWeapon;
    int CurrentScore;
    int AmmoNum,MaxAmmoNum;
    int AmmoStock;
    int HealthXRef;
    int BulletXRef;
    int FrameRate;
    long DudeCount;
    boolean Init;
    
    BSSprite HUDSprite;
    BSSprite DATASprite;
    BSSprite BULLETSprite[];
    BSSprite HEALTHSprite[];
    BSSprite Infinite;
    BSSprite CoolDude;
    Graphics g;
    BSMainApp mApp;
    public BSPlayerHUD(BSMainApp theApp) {
        CentreX=110;
        CentreY=88;
        Luna=0;
        LunaLength=0;
        MaxLunaLength=10;
        HP=0;
        CurrentScore=0;
        AmmoNum=MaxAmmoNum=0;
        AmmoStock=0;
        HealthXRef=10;
        BulletXRef=0;
        DudeCount=0;
        mApp=theApp;
        Init=false;
        FrameRate=BSCanvas.MSPF;
        
    }
    public void HUDRender()
    {
        HUDSprite.Render();
        RenderHealth();
        RenderLunaGuage();
        DATASprite.Render();
        RenderAmmo();
        RenderData();
       
    }
    public void HUDUpdate()
    {
        CoolDude.Update();
        //HUDSprite.Update();
        //DATASprite.Update();
        //UpdateSprites();
    }
    public void UpdateSprites()
    {
        for(int i=0; i<AmmoNum; i++)
        {
            BULLETSprite[i].Update();
        }
        for(int j=0; j<HP; j++)
        {
            HEALTHSprite[j].Update();
        }
        Infinite.Update();
   
        
        
    }
    public void SetData(int Weapon, int Health, int MaxHealth, float LunaGuageFraction, int LunaLevel, int Ammo,int MaxAmmo, int Stock, int Score, int theCombo)
    {
        Combo=theCombo;
        HP=Health;
        MaxHP=MaxHealth;
        LunaLength=LunaGuageFraction*MaxLunaLength;
        CurrentWeapon=Weapon;
        Luna=LunaLevel;
        CurrentScore=Score;
        AmmoNum=Ammo;
        MaxAmmoNum=MaxAmmo;
        AmmoStock=Stock;
        if (Init==false)
        {
            HUDSprite = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("HUD"),220,176);
            
            DATASprite=mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("HUD_DATA"),220,176);
            Infinite=mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("Infinite_Symbol"),44,18);
            BULLETSprite=new BSSprite[MaxAmmoNum];
            HEALTHSprite=new BSSprite[MaxHealth];
            HealthXRef=35;
            BulletXRef=80;
            for (int i=0; i<MaxAmmoNum; i++)
            {
                BULLETSprite[i]=mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("Bullet"),3,10);
                BULLETSprite[i].setRefPixel(0,0);
                BULLETSprite[i].setPosition(BulletXRef,166);
                BulletXRef+=5;
            }
            for (int j=0;j<MaxHealth;j++)
            {
               HEALTHSprite[j]=mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("Healthbar"),10,10);
               HEALTHSprite[j].setRefPixel(0,0);
               HEALTHSprite[j].setPosition(HealthXRef,35);
               HealthXRef+=10;
            }
            HUDSprite.setRefPixel(0,0);
            DATASprite.setRefPixel(0,0);
            HUDSprite.setPosition(0,0);
            DATASprite.setPosition(0,0);
            Infinite.setRefPixel(0,0);
            Infinite.setPosition(34,158);   
            
            CoolDude=mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("Boomsticker"),220,176);
            int CoolIdle[] ={0};
            int CoolMoves[]={0,1};
            CoolDude.addFrameSequence(CoolIdle);
            CoolDude.addFrameSequence(CoolMoves);
            CoolDude.setRefPixel(0,0);
            CoolDude.setPosition(-150,0);
            CoolDude.playSequence(0,false, FrameRate);
            Init=true;
        }
        
        
    }
    public void RenderCombo()
    {
        
      
      
        
        
    }
    public void RenderHealth()
    {
         for(int i=0;i<HP; i++)
         {
             HEALTHSprite[i].Render();
         }
    }
    public void RenderLunaGuage()
    {
       int LunaRefPosLeft = CentreX - 10 - (int)LunaLength*8;
       mApp.mGraphics.g.setColor(255,255,0);
       mApp.mGraphics.g.fillRect((int)LunaRefPosLeft,22,(int)LunaLength*8,4);
       
       int LunaRefPosRight=CentreX + 10;
       mApp.mGraphics.g.setColor(255,255,0);
       mApp.mGraphics.g.fillRect((int)LunaRefPosRight,22,(int)LunaLength*8,4);
    }
    public void RenderAmmo()
    {
       
        for (int i=0; i<AmmoNum; i++)
        {
          //  BULLETSprite[0].Render();
            BULLETSprite[i].Render();
        }
        
    }
    public void RenderData()
    {
        mApp.mGameCanvas.mGraphics.drawBMString(Integer.toString(Luna),110,30,0);
        mApp.mGameCanvas.mGraphics.drawBMString(Integer.toString(CurrentScore),110,13,0);
        if (CurrentWeapon==0)
        {
            Infinite.Render();
        }
        else
        {
             mApp.mGameCanvas.mGraphics.drawBMString(Integer.toString(AmmoStock),55,174,0);
        }
        
    }
    
}
