/*
 * BSGameCanvas.java
 *
 * Created on May 12, 2007, 11:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSGameCanvas extends BSState {
    public BS_MenuManager mMM;  //Instance to menu manager
    
    BSMaploader mMaploader;
    BSHoleManager mHoleManager;
    BSPlayerModel mPlayerModel;
    BSResourceHolder mResources;
    BSWinScreen mWinScreen;
    BSLoseScreen mLoseScreen;
    BSImage Backdrop;
    
    boolean mWonGame = false;
    boolean mLoadNextLevel = false;
    boolean mLostGame = false;
    boolean mPaused = false;
    boolean mQuit = false;
    
    String mLevel;
    
    BSSound BGMusic;
    /** Creates a new instance of BSGameCanvas */
    //BASE
    public BSGameCanvas(BS_MenuManager theManager)
    {
        mMM = theManager;
        mMM.mSM.NewState(this);   
        mResources = new BSResourceHolder();
    }
    
    //NULL
    public void Init()
    {
        mMaploader = new BSMaploader(mApp);
        mMaploader.LoadMap(mLevel);
        mHoleManager = new BSHoleManager(this);
        mHoleManager.SetMap(mMaploader.GetMap(0));

        mPlayerModel = new BSPlayerModel(mApp);
        mWinScreen = new BSWinScreen(this);
        mLoseScreen = new BSLoseScreen(this);
        BGMusic = mResources.GetSoundThrow("BGMus");
        BGMusic.SetLoop(true);
        BGMusic.SetVolume(100);
        BGMusic.Play();
        Backdrop = mResources.GetImageThrow("Backdrop");
    }
    
    public void StartLevel(String theLevel)
    {
        mLevel = theLevel;
    }
    
    //NULL
    public void Update()
    {
        if (!mPaused) {
            if (mLoadNextLevel)
            {
                mApp.LoadNextLevel();
                return;
            }
            if (mWonGame)
            {
                mWinScreen.Update();
                return;
            }
            if (mLostGame)
            {
                mLoseScreen.Update();
                return;
            }

            mHoleManager.Update();
            mPlayerModel.Update();
        }
        if (mQuit) {
            mApp.RestartGame();
            return;
        }
    }
    //NULL
    public void Draw()
    {
       if (mWonGame)
        {
            mWinScreen.Draw();
            return;
        }
       if (mLostGame)
       {
           mLoseScreen.Draw();
           return;
       }
       Backdrop.setPosition(0,0);
       Backdrop.Render();
       mHoleManager.Render();
       mPlayerModel.PlayerRender();
       
       if (mPaused)
       {
           if (mApp.is_RightHanded) {
               mGraphics.drawBMString("Continue", 217, 2, 0, 0, 0, 0, BSGraphics.RIGHT|BSGraphics.TOP);
               mGraphics.drawBMString("Quit", 217, 174, 0, 0, 0, 0, BSGraphics.RIGHT|BSGraphics.BOTTOM);
           }
           else {
               mGraphics.drawBMString("Continue", 3, 2, 0, 0, 0, 0, BSGraphics.LEFT|BSGraphics.TOP);
               mGraphics.drawBMString("Quit", 3, 174, 0, 0, 0, 0, BSGraphics.LEFT|BSGraphics.BOTTOM);
           }
       }
       else
       {
           if (mApp.is_RightHanded) {
               mGraphics.drawBMString("Menu", 217, 2, 0, 0, 0, 0, BSGraphics.RIGHT|BSGraphics.TOP);
           }
           else {
               mGraphics.drawBMString("Menu", 3, 2, 0, 0, 0, 0, BSGraphics.LEFT|BSGraphics.TOP);
           }
       }
    }   
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
        if (mWonGame)
        {
            mLoadNextLevel = true;
            return;
        }
        if (mLostGame)
            return;
        //whee lets test monsters!
        mPlayerModel.KeyDetect(KeyPressed);
        if (KeyPressed == mMM.B_TOP) mPaused = !mPaused;
        if (KeyPressed == mMM.B_BOT) {
            mQuit = true;
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
    
    //Do your dereferencing here (setting resources to null)
    public void Destroy()
    {
        //mMM = null;
        mMaploader = null;
        mPlayerModel = null;
        mHoleManager = null;
        mResources = null;
        mWinScreen = null;
        Backdrop = null;
        BGMusic = null;
    }
}
