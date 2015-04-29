/*
 * BSPlayerView.java
 *
 * Created on May 15, 2007, 4:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Norma Sit
 */
public class BSPlayerView {
    
    final static int IdleState  = 0;
    final static int ShootState = 3;
    final static int ReloadState= 6;
    final static int HitState   = 2;
    
    
    boolean Shoot;
    boolean Reload;
    boolean IsHit;
    
    long TimeBuffer;
    long ShootBuffer;
    long ReloadBuffer;
    long CurrentBuffer;
    long StartTime;
    int NumOfFrames;
    int CurrentState;
    int Framerate;
   
    int CurrentX, CurrentY;
    final int LeftX=0;
  

    final int MiddleX=70;
    

    final int RightX=140;
    
    
    BSMainApp mApp;
    BSSprite mFingerSprite;
    BSSprite mBoomStickSprite;
    BSSprite mNapalmSprite;
    BSSprite mBackground;
    int CurrentWeapon;

    public BSPlayerView(BSMainApp theApp) 
    {
        
        //FrameRate=BSCanvas.MSPF;
        CurrentX=MiddleX;
        CurrentY=130;
        mApp=theApp;
        
        mFingerSprite    = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponFingers"),70,38);
        mBoomStickSprite = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponBoomstick"),70,38);
        mNapalmSprite    = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponNapalm"),70,70);
        mNapalmSprite    = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponNapalm"),70,70);
        int IdleMiddle[]   = {0};
        int IdleLeft[]     = {1};
        int IdleRight[]    = {2};
        int ShootingMiddle[] = {5 ,6 ,7 ,8 ,9 };
        int ShootingLeft[]   = {10,11,12,13,14};
        int ShootingRight[]  = {15,16,17,18,19};
        int Reloading[]      = {20,21,22,23,24};
        
        mFingerSprite.addFrameSequence(IdleMiddle);
        mFingerSprite.addFrameSequence(IdleLeft);
        mFingerSprite.addFrameSequence(IdleRight);
        mFingerSprite.addFrameSequence(ShootingMiddle);
        mFingerSprite.addFrameSequence(ShootingLeft);
        mFingerSprite.addFrameSequence(ShootingRight);
       
        mBoomStickSprite.addFrameSequence(IdleMiddle);
        mBoomStickSprite.addFrameSequence(IdleLeft);
        mBoomStickSprite.addFrameSequence(IdleRight);
        mBoomStickSprite.addFrameSequence(ShootingMiddle);
        mBoomStickSprite.addFrameSequence(ShootingLeft);
        mBoomStickSprite.addFrameSequence(ShootingRight);
        mBoomStickSprite.addFrameSequence(Reloading);
   
        mBoomStickSprite.setRefPixel(0,0);
        mBoomStickSprite.setPosition(CurrentX,CurrentY);
        
        mFingerSprite.setRefPixel(0,0);
        mFingerSprite.setPosition(CurrentX,CurrentY);
        
        CurrentState = 0;
        CurrentWeapon=BSPlayerModel.FINGERSNAME;
        
        mFingerSprite.playSequence(CurrentState,false);
        mBoomStickSprite.playSequence(CurrentState,false);
        
        Shoot=false;
        Reload=false;
        IsHit=false;
        TimeBuffer=0;
        ShootBuffer = 125*4;
        ReloadBuffer= 125*4;
        StartTime=0;
        CurrentBuffer=0;
        NumOfFrames=0;
        
        int AIdleMiddle[] = {0 ,1 ,2 };
        int AIdleLeft[]   = {12,13,14};
        int AIdleRight[]  = {24,25,26};
        int AShootingMiddle[] = {11,10, 9, 8, 7, 6};
        int AShootingLeft[]   = {23,22,21,20,19,18};
        int AShootingRight[]  = {35,34,33,32,31,30};
        int AReloadMiddle[]= {3 , 4, 5, 5 ,4, 3};
        int AReloadLeft[]  = {15,16,17,17,16,15};
        int AReloadRight[] = {27,28,29,29,28,27};
        
        mNapalmSprite.addFrameSequence(AIdleMiddle);
        mNapalmSprite.addFrameSequence(AIdleLeft);
        mNapalmSprite.addFrameSequence(AIdleRight);
        mNapalmSprite.addFrameSequence(AShootingMiddle);
        mNapalmSprite.addFrameSequence(AShootingLeft);
        mNapalmSprite.addFrameSequence(AShootingRight);
        mNapalmSprite.addFrameSequence(AReloadMiddle);
        mNapalmSprite.addFrameSequence(AReloadLeft);
        mNapalmSprite.addFrameSequence(AReloadRight);
        mNapalmSprite.setRefPixel(0,0);
        mNapalmSprite.setPosition(CurrentX,98);
        mNapalmSprite.playSequence(CurrentState,false);
    }
    public int GetCurrentState()
    {
        return CurrentState;
    }
    public int TheState(int thestate)
    {
        switch (thestate)
        {
            case 0:
                return ShootState;
                
            case 1:
                return ReloadState;
               
            case 2:
                return HitState;
               
            case 3:
                return IdleState;
            default:
               System.err.println("NO SUCH WEAPONSTATE");
               break;       
        }
        return 3;
    }
    public void Render()
    {
        if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
        {
            mFingerSprite.setPosition(CurrentX,CurrentY);
            mNapalmSprite.setPosition(0,176);
            mBoomStickSprite.setPosition(0,176);
        }
        else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
        {
            mBoomStickSprite.setPosition(CurrentX,CurrentY);
            mNapalmSprite.setPosition(0,176);
            mFingerSprite.setPosition(0,176);
        }  
        else if (CurrentWeapon==BSPlayerModel.NAPALMNAME)
        {
            mNapalmSprite.setPosition(CurrentX,CurrentY);
            mBoomStickSprite.setPosition(0,176);
            mFingerSprite.setPosition(0,176);
        }
        mFingerSprite.Render();
        mNapalmSprite.Render();
        mBoomStickSprite.Render();
    }
    public BSSprite GetSpriteName(int Name)
    {
        if(Name==BSPlayerModel.FINGERSNAME)
        {
            return mFingerSprite;
        }
        else if (Name==BSPlayerModel.BOOMSTICKNAME)
        {
            return mBoomStickSprite;
        }
        else if (Name==BSPlayerModel.NAPALMNAME)
        {
            return mNapalmSprite;
        }
        else
        {
            return mFingerSprite;
        }
        
    }
    public void Update()
    {
       
       mFingerSprite.Update();
       mBoomStickSprite.Update();
       mNapalmSprite.Update();
       if (CurrentBuffer>0)
       {
            if((java.lang.System.currentTimeMillis()-StartTime)>CurrentBuffer)
            {
               CurrentState=IdleState;
               if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
               {
                   if(CurrentX==LeftX)
                        mFingerSprite.playSequence(CurrentState+1,false);
                   else if(CurrentX==MiddleX)
                        mFingerSprite.playSequence(CurrentState,false);
                   else if(CurrentX==RightX)
                        mFingerSprite.playSequence(CurrentState+2,false);
               }
               else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
               {
                   if(CurrentX==LeftX)
                        mBoomStickSprite.playSequence(CurrentState+1,false);
                   else if(CurrentX==MiddleX)
                        mBoomStickSprite.playSequence(CurrentState,false);
                   else if(CurrentX==RightX)
                        mBoomStickSprite.playSequence(CurrentState+2,false);
               }
               else if (CurrentWeapon==BSPlayerModel.NAPALMNAME)
               {
                   if(CurrentX==LeftX)
                        mNapalmSprite.playSequence(CurrentState+1,true);
                   else if(CurrentX==MiddleX)
                        mNapalmSprite.playSequence(CurrentState,true);
                   else if(CurrentX==RightX)
                        mNapalmSprite.playSequence(CurrentState+2,true);
               }
               CurrentBuffer=0;
            }
            
       }
      
    }
    public void RenderReload()
    {
        NumOfFrames=5;
        if (CurrentWeapon==BSPlayerModel.NAPALMNAME)
             NumOfFrames=6;
        else
             NumOfFrames=5;
    
        SetCurrentWeaponPosition(MiddleX,CurrentY);
        CurrentState=ReloadState;
        CurrentBuffer=ReloadBuffer;
        PlayAnimation();
    }
    public void PlayAnimation()
    {
        StartTime = java.lang.System.currentTimeMillis();
        if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
        {
              switch(CurrentX)
                {
                    case LeftX:
                        mFingerSprite.playSequence(CurrentState+1,false);
                        break;
                    case MiddleX:
                        mFingerSprite.playSequence(CurrentState,false);
                        break;
                    case RightX:
                        mFingerSprite.playSequence(CurrentState+2,false);
                        break;
                }
                   
        }
        else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
        {
            if(CurrentState==ReloadState)
            {
                 mBoomStickSprite.playSequence(CurrentState,false);
            }
            else
            {
                switch(CurrentX)
                {
                    case LeftX:
                         mBoomStickSprite.playSequence(CurrentState+1,false);
                        break;
                    case MiddleX:
                         mBoomStickSprite.playSequence(CurrentState,false);
                        break;
                    case RightX:
                         mBoomStickSprite.playSequence(CurrentState+2,false);
                        break;
                }
            
                  
            }
        }
        else if (CurrentWeapon==BSPlayerModel.NAPALMNAME)
        {
            switch(CurrentX)
            {
                case LeftX:
                    mNapalmSprite.playSequence(CurrentState+1,false);
                    break;
                case MiddleX:
                    mNapalmSprite.playSequence(CurrentState,false);
                    break;
                case RightX:
                    mNapalmSprite.playSequence(CurrentState+2,false);
                    break;
            }
            
        }
    }
    public void ResetWeapon(int theName)
    {
        CurrentWeapon=theName;
        CurrentState=IdleState;
        if(CurrentX==LeftX)
           mFingerSprite.playSequence(CurrentState+1,false);
        else if(CurrentX==MiddleX)
           mFingerSprite.playSequence(CurrentState,false);
        else if(CurrentX==RightX)
           mFingerSprite.playSequence(CurrentState+2,false);
        
        
    }
    public void RenderShoot(int code)
    {
        if (CurrentWeapon>1)
            NumOfFrames=6;
        else
            NumOfFrames=4;
        CurrentState=ShootState;
        CurrentBuffer=ShootBuffer;
       
        if(code==mApp.mMenuManager.B_S1|| code==mApp.mMenuManager.B_S4||code==mApp.mMenuManager.B_S7)
        {
            CurrentX=LeftX;
            SetCurrentWeaponPosition(LeftX,CurrentY);
        }
        else if(code==mApp.mMenuManager.B_S2 || code==mApp.mMenuManager.B_S5||code==mApp.mMenuManager.B_S8)
        {
            CurrentX=MiddleX;
            SetCurrentWeaponPosition(MiddleX,CurrentY);
        }
        else if(code==mApp.mMenuManager.B_S3 || code==mApp.mMenuManager.B_S6||code==mApp.mMenuManager.B_S9)
        {
            CurrentX=RightX;
            SetCurrentWeaponPosition(RightX,CurrentY);
        }
        PlayAnimation();
    }
    
    void SetCurrentWeaponPosition(int x,int y)
    {
        if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
        {
            mFingerSprite.setPosition(x,y);
        }
        else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
        {
            mBoomStickSprite.setPosition(x,y);
        } 
        else if (CurrentWeapon==BSPlayerModel.NAPALMNAME)
        {
            mNapalmSprite.setPosition(x,y);
        }
    }
  
}
