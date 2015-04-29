import javax.microedition.lcdui.game.Sprite;
import java.util.Random;
/*
 * BSLoadScreen.java
 *
 * Created on May 15, 2007, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSLoadScreen extends BSState {
    //Thread LoadingThread;
    int Progress = 0;
    public BS_MenuManager mMM;  //Instance to menu manager
    BSImage Logo;
    boolean ClickCont;
    
    /*
    BSSprite Deathster;
    boolean death;
    boolean popped;
    int deadtime;
     **/
    
    BSResourceHolder mResourceHolder;
    
    //Bullet shot variables
    /*BSSprite mBullet[];
    boolean mShooting[];
    int mTotalBullets;
    int mBulletsUsed;
    Random generator; */
    
    BSState mExitState;
    
    /** Creates a new instance of BSLoadScreen */
    public BSLoadScreen(BS_MenuManager theManager, String thePath, BSResourceHolder theResourceHolder, BSState theExitState, boolean Click) 
    {
        mMM = theManager;
        mMM.mSM.NewState(this);
        
        mExitState = theExitState;
        ClickCont = Click;
        
        Logo = mApp.mGraphics.NewImage("/Images/Logo.png", 0, 0);
        
//        Deathster = mGraphics.NewSprite(mGraphics.NewImage("/Images/poppingbastard.png"), 30, 38);
//        int Popping[] = { 0, 1, 2, 3 };
//        int Death[] = { 12, 13, 14, 15, 6 };
//        
//        Deathster.addFrameSequence(Popping);
//        Deathster.addFrameSequence(Death);
//        
//        death = false;
//        
//        Deathster.playSequence(0, false);
//        Deathster.setRefPixel(30/2, 38/2);
//        Deathster.setPosition(BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH/2);
//        
//        popped = true;
//        
//        int sequence1[] = {0,1,2,3,4,5};
//        int sequence2[] = {6,7,8,9,10,11};
//        int sequence3[] = {12,13,14,15,16,17};
//        int sequence4[] = {18,19,20,21,22,23};
//        mTotalBullets = 3;
//        mBullet = new BSSprite [mTotalBullets];
//        mShooting = new boolean [mTotalBullets];
//        mBulletsUsed = 0;
//        
//        BSImage temp = mGraphics.NewImage("/Images/FingerShot.png");
//        //Bullet shot initialization
//        for (int i=0;i<mTotalBullets;i++)
//        {
//            mBullet[i] = mGraphics.NewSprite(temp, 48, 48);
//            mBullet[i].setRefPixel(48/2, 48/2);
//            mBullet[i].setPosition(BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH/2);
//            mBullet[i].addFrameSequence(sequence1);
//            mBullet[i].addFrameSequence(sequence2);
//            mBullet[i].addFrameSequence(sequence3);
//            mBullet[i].addFrameSequence(sequence4);
//            
//            mShooting[i] = false;
//        }
//        generator = new Random();
        
        mApp.mResourceManager.LoadResource(thePath, theResourceHolder);
        mResourceHolder = theResourceHolder;
    }
    
    public void Init()
    {
        
    }
    
    /** Loads resources from the specified file path and sets the ExitState. */
    public void SetUp(String thePath, BSState theExitState, boolean Click)
    {
        ClickCont = Click;
        Progress = 0;
        mExitState = theExitState;
        if (mResourceHolder.mResources.length > 0) {
            mResourceHolder.mLoadedResources = 0;
            mResourceHolder.mResources = null;
        }
        mApp.mResourceManager.LoadResource(thePath, mResourceHolder);
    }
    
    public void Update()
    {
        if (Progress != 100)
            Progress = mApp.mResourceManager.LoadNextResource(mResourceHolder);
        
        if (ClickCont && Progress == 100)
        {
            mExitState.Init();
            mMM.mSM.SetState(mExitState);
        }
        
//        Deathster.Update();
//        for (int i=0;i<mTotalBullets;i++)
//             mBullet[i].Update();
//        
//        deadtime += BSCanvas.MSPF;
//        if (death == true)
//        {
//            Deathster.playSequence(1, false);
//            deadtime = 0;
//            death = false;
//            popped = false;
//        }
//        
//        if (deadtime >= BSCanvas.MSPF * 4 && death == false && popped == false)
//        {
//            Deathster.playSequence(0, false);
//            popped = true;
//        }
    }
    
    public void Draw()
    {
        //Draw the loading bar.
        mGraphics.g.setColor(255 * (100 - Progress)/100, 255 * Progress/100, 0);
        //BGImg.Render();
        Logo.Render();
        mGraphics.g.fillRect(10, BSGraphics.SCREEN_WIDTH - 20, Progress * (BSGraphics.SCREEN_HEIGHT-20)/100, 10);
        if (Progress == 100) {
            mGraphics.drawBMString("Complete!", BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH - 50, 0, 255, 255, 255, BSGraphics.HCENTER|BSGraphics.TOP);
            mGraphics.drawBMString("Click to continue", BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH - 22, 0, 255, 255, 255, BSGraphics.HCENTER|BSGraphics.TOP);
        }
        else
            mGraphics.drawBMString("Loading..", BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH - 50, 0, 255, 255, 255, BSGraphics.HCENTER|BSGraphics.TOP);
        
//        Deathster.Render();
//        for (int i=0;i<mTotalBullets;i++) {
//            if (mShooting[i]) {
//                if (mBullet[i].GetIsAnimating())
//                    mBullet[i].Render();
//                else {
//                    mBulletsUsed--;
//                    mShooting[i] = false;
//                }
//            }
//        }
    }
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
        if (Progress == 100)
        {
            mExitState.Init();
            mMM.mSM.SetState(mExitState);
        }
//        else {
//           if (deadtime >= BSCanvas.MSPF * 4)
//               death = true;
//           if (mBulletsUsed < mTotalBullets) {
//               mBulletsUsed++;
//               for (int i=0;i<mTotalBullets;i++) {
//                   if (!mShooting[i]) {
//                       mShooting[i] = true;
//                       int temp = generator.nextInt(4);
//                       System.out.println(temp);
//                       mBullet[i].playSequence(temp,false);
//                       break;
//                   }
//               }
//           }
//        }
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
    }
    
    public void ExitState()
    {
        mIsExitingState = true;
    }
}
