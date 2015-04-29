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
    BSSprite Deathster;
    boolean death;
    boolean popped;
    int deadtime;
    
    //Bullet shot variables
    BSSprite mBullet[];
    boolean mShooting[];
    int mTotalBullets;
    int mBulletsUsed;
    Random generator;
    
    /** Creates a new instance of BSLoadScreen */
    public BSLoadScreen(BS_MenuManager theManager) 
    {
        mMM = theManager;
        mMM.mSM.NewState(this);
        
        Deathster = mGraphics.NewSprite(mGraphics.NewImage("/Images/poppingbastard.png"), 30, 38);
        int Popping[] = { 0, 1, 2, 3 };
        int Death[] = { 12, 13, 14, 15, 6 };
        
        Deathster.addFrameSequence(Popping);
        Deathster.addFrameSequence(Death);

        mApp.mResourceManager.LoadResource("/Resources.txt");
        
        death = false;
        
        Deathster.playSequence(0, false);
        Deathster.setRefPixel(30/2, 38/2);
        Deathster.setPosition(BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH/2);
        
        popped = true;
        
        int sequence1[] = {0,1,2,3,4,5};
        int sequence2[] = {6,7,8,9,10,11};
        int sequence3[] = {12,13,14,15,16,17};
        int sequence4[] = {18,19,20,21,22,23};
        mTotalBullets = 3;
        mBullet = new BSSprite [mTotalBullets];
        mShooting = new boolean [mTotalBullets];
        mBulletsUsed = 0;
        
        BSImage temp = mGraphics.NewImage("/Images/FingerShot.png");
        //Bullet shot initialization
        for (int i=0;i<mTotalBullets;i++)
        {
            mBullet[i] = mGraphics.NewSprite(temp, 48, 48);
            mBullet[i].setRefPixel(48/2, 48/2);
            mBullet[i].setPosition(BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH/2);
            mBullet[i].addFrameSequence(sequence1);
            mBullet[i].addFrameSequence(sequence2);
            mBullet[i].addFrameSequence(sequence3);
            mBullet[i].addFrameSequence(sequence4);
            
            mShooting[i] = false;
        }
        generator = new Random();
    }
    
    public void Update()
    {
        //run();
        
        Deathster.Update();
        for (int i=0;i<mTotalBullets;i++)
             mBullet[i].Update();
        
        deadtime += BSCanvas.MSPF;
        if (death == true)
        {
            Deathster.playSequence(1, false);
            deadtime = 0;
            death = false;
            popped = false;
        }
        
        if (deadtime >= BSCanvas.MSPF * 4 && death == false && popped == false)
        {
            Deathster.playSequence(0, false);
            popped = true;
        }
    }

    public void run() {
        while (Progress != 100)
            Progress = mApp.mResourceManager.LoadNextResource();
        
        // to Israel: Put code to load gui menu below here! ??????????
    }
    
    public void Draw()
    {
        run();
        //Draw the loading bar.
        mApp.mGraphics.g.setColor(255 * (100 - Progress)/100, 255 * Progress/100, 0);
        mApp.mGraphics.g.fillRect(10, BSGraphics.SCREEN_WIDTH - 20, Progress * (BSGraphics.SCREEN_HEIGHT-20)/100, 10);
        
        Deathster.Render();
        for (int i=0;i<mTotalBullets;i++) {
            if (mShooting[i]) {
                if (mBullet[i].GetIsAnimating())
                    mBullet[i].Render();
                else {
                    mBulletsUsed--;
                    mShooting[i] = false;
                }
            }
        }
    }
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
        if (KeyPressed == mMM.B_CTR && Progress == 100)
        {
            mApp.mGameCanvas = new BSGameCanvas(mApp.mMenuManager);
            mMM.mSM.SetState(mMM.mStates[0]);
        }
        else {
           if (deadtime >= BSCanvas.MSPF * 4)
               death = true;
           if (mBulletsUsed < mTotalBullets) {
               mBulletsUsed++;
               for (int i=0;i<mTotalBullets;i++) {
                   if (!mShooting[i]) {
                       mShooting[i] = true;
                       int temp = generator.nextInt(4);
                       System.out.println(temp);
                       mBullet[i].playSequence(temp,false);
                       break;
                   }
               }
           }
        }
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
