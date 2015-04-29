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
    

 
    
    int CurrentX, CurrentY;
    int LeftX,LeftY;
    int MiddleX,MiddleY;
    int RightX, RightY;
    BSMainApp mApp;
    BSSprite mFingerSprite;
    BSSprite mBoomStickSprite;
    
    int CurrentWeapon;

    public BSPlayerView(BSMainApp theApp) 
    {
     
        
        LeftX=0;
        LeftY=130;
        
        MiddleX=70;
        MiddleY=130;
        
        RightX=140;
        RightY=130;
        
        CurrentX=MiddleX;
        CurrentY=MiddleY;
        mApp=theApp;
        
        mFingerSprite = mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponFingers"),70,38);
        mBoomStickSprite= mApp.mGraphics.NewSprite(mApp.mGameCanvas.mResources.GetImageThrow("WeaponBoomstick"),70,38);
        
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
        
        mFingerSprite.playSequence(CurrentState,false,125);
        mBoomStickSprite.playSequence(CurrentState,false,125);
        
        Shoot=false;
        Reload=false;
        IsHit=false;
        TimeBuffer=0;
        ShootBuffer = 125*4;
        ReloadBuffer= 125*4;
        StartTime=0;
        CurrentBuffer=0;
        NumOfFrames=0;
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
            mFingerSprite.Render();
        }
        else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
        {
            mBoomStickSprite.Render();
        }  
    }
    public BSSprite GetSpriteName(int Name)
    {
        if(Name==0)
        {
            return mFingerSprite;
        }
        else if (Name==1)
        {
            return mBoomStickSprite;
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
       if (CurrentBuffer>0)
       {
            if((java.lang.System.currentTimeMillis()-StartTime)>CurrentBuffer)
            {
               CurrentState=IdleState;
               if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
               {
                   if(CurrentX==LeftX)
                        mFingerSprite.playSequence(CurrentState+1,false,125);
                   else if(CurrentX==MiddleX)
                        mFingerSprite.playSequence(CurrentState,false,125);
                   else if(CurrentX==RightX)
                        mFingerSprite.playSequence(CurrentState+2,false,125);
               }
               else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
               {
                   if(CurrentX==LeftX)
                        mBoomStickSprite.playSequence(CurrentState+1,false,125);
                   else if(CurrentX==MiddleX)
                        mBoomStickSprite.playSequence(CurrentState,false,125);
                   else if(CurrentX==RightX)
                        mBoomStickSprite.playSequence(CurrentState+2,false,125);
               }
               CurrentBuffer=0;
            }
            
       }
      
    }
    public void RenderReload()
    {
        NumOfFrames=5;
    
        SetCurrentWeaponPosition(MiddleX,MiddleY);
        CurrentState=ReloadState;
        CurrentBuffer=ReloadBuffer;
        PlayAnimation();
        
    }
    public void PlayAnimation()
    {
        StartTime = java.lang.System.currentTimeMillis();
        if(CurrentWeapon==BSPlayerModel.FINGERSNAME)
        {
            if(CurrentX==LeftX)
                mFingerSprite.playSequence(CurrentState+1,false,(int)CurrentBuffer/NumOfFrames);
            else if(CurrentX==MiddleX)
                mFingerSprite.playSequence(CurrentState,false,(int)CurrentBuffer/NumOfFrames);
            else if(CurrentX==RightX)
                mFingerSprite.playSequence(CurrentState+2,false,(int)CurrentBuffer/NumOfFrames);
        }
        else if (CurrentWeapon==BSPlayerModel.BOOMSTICKNAME)
        {
            if(CurrentX==LeftX)
                mBoomStickSprite.playSequence(CurrentState+1,false,(int)CurrentBuffer/NumOfFrames);
            else if(CurrentX==MiddleX)
                mBoomStickSprite.playSequence(CurrentState,false,(int)CurrentBuffer/NumOfFrames);
            else if(CurrentX==RightX)
                mBoomStickSprite.playSequence(CurrentState+2,false,(int)CurrentBuffer/NumOfFrames);
        }
    }
    public void ResetWeapon(int theName)
    {
        CurrentWeapon=theName;
        CurrentState=IdleState;
        if(CurrentX==LeftX)
           mFingerSprite.playSequence(CurrentState+1,false,125);
        else if(CurrentX==MiddleX)
           mFingerSprite.playSequence(CurrentState,false,125);
        else if(CurrentX==RightX)
           mFingerSprite.playSequence(CurrentState+2,false,125);
        
        
    }
    public void RenderShoot(int code)
    {
        NumOfFrames=4;
        CurrentState=ShootState;
        CurrentBuffer=ShootBuffer;
        if(mApp.is_RightHanded)
        {
            if(code==49 || code==50||code==51)
            {
                CurrentX=LeftX;
                SetCurrentWeaponPosition(LeftX,LeftY);
            }
            else if(code==52 || code==53||code==54)
            {
                CurrentX=MiddleX;
                SetCurrentWeaponPosition(MiddleX,MiddleY);
            }
            else if(code==55 || code==56||code==57)
            {
                CurrentX=RightX;
                SetCurrentWeaponPosition(RightX,RightY);
            }
        }
        else 
        {
            if(code==51 || code==54||code==57)
            {
                CurrentX=LeftX;
                SetCurrentWeaponPosition(LeftX,LeftY);
            }
            else if(code==50 || code==53||code==56)
            {
                CurrentX=MiddleX;
                SetCurrentWeaponPosition(MiddleX,MiddleY);
            }
            else if(code==49 || code==52||code==55)
            {
                CurrentX=RightX;
                SetCurrentWeaponPosition(RightX,RightY);
            }
        }
        PlayAnimation();
        //CurrentState=ShootState;
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
    }
  
}
