/*
 * State_MainMenu.java
 *
 * Created on May 15, 2007, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */
public class State_MainMenu extends BSState {
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    public BS_MenuManager mMM;  //Instance to menu manager
    
    private boolean mFadeIn;
    private boolean mFadeOut;
    private int mFadeSpeed;
    
    BSImage ICursor;
    BSImage IBackground;
    BSImage IFade;
    
    
    /** Creates a new instance of State_MainMenu */
    public State_MainMenu(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 0;
        mNumOptions = 3;
        
        mFadeIn = mFadeOut = false;
        mFadeSpeed = 500/mApp.mCanvas.FPS;
        
        //Load Images
        ICursor = mGraphics.NewImage("/Images/Position_Pointer_White.png", 76, 80);
        IBackground = mGraphics.NewImage("/Images/MainMenu.png", 0, 0);
        IFade = mGraphics.NewImage("/Images/Black_DiagonalRight.png",BSGraphics.SCREEN_HEIGHT-500,0);
    }
    
    public void Init()
    {
        return;
    }
    
    //NOMINAL
    public void Update() {
        if (mIsEnteringState || mIsExitingState) {
            if (mFadeIn) {
                IFade.Translate(mFadeSpeed,0);
                if (IFade.mX >= BSGraphics.SCREEN_HEIGHT) {
                    mIsEnteringState = mFadeIn = false;
                    IFade.RotateImage(BSSprite.TRANS_ROT180);
                }
            }
            if (mFadeOut) {
                IFade.Translate(mFadeSpeed,0);
                if (IFade.mX >= 0) {
                    mIsExitingState = mFadeOut = false;
                    if (mCursor == 0) mMM.mSM.SetState(mMM.mStates[1]);
                }
            }
            if (!mFadeIn && !mFadeOut) mIsEnteringState = mIsExitingState = false;
        }
    }
    
    //NOMINAL
    public void Draw() {
        IBackground.Render();
        ICursor.Render();
        if (mFadeIn || mFadeOut)
            IFade.Render();
    }
    
    //BASE
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed) {
        if (mIsEnteringState || mIsExitingState) return;
        if (KeyPressed == mMM.B_DOWN || KeyPressed == mMM.B_RIGHT) {
            Scroll(true);
        }
        else if (KeyPressed == mMM.B_UP || KeyPressed == mMM.B_LEFT) {
            Scroll(false);
        }
        else if (KeyPressed == mMM.B_CTR) Select();
    }
    
    //BASE: UNTESTED
    /* Name: Scroll
     * Scrolls the cursor Down if parameter is true.
     * Scrolls the cursor Up if parameter is false. 
     * Cursor warps to the 1st option if it scrolls over the last option. 
     * Similarly, Cursor warps to the last option if it scrolls before the 1st option. */
    public void Scroll(boolean Scroll_Down) {
        if (Scroll_Down) {
            mCursor++;
            if (mCursor >= mNumOptions) mCursor = 0;
        }
        else {
            mCursor--;
            if (mCursor < 0) mCursor = mNumOptions - 1;
        }
        UpdateCursorPosition();
    }
    
    //NOMINAL: UNTESTED
    /** Updates the Cursor Image's Position */
    public void UpdateCursorPosition() {
        ICursor.setPosition(76, 80+mCursor*21);
    }
    
    //NOMINAL
    /* Performs an action (like traversing into another menu)
     * when an option in the menu is selected. */
    public void Select() {
        if (mCursor == 0) { //To Start Menu
            ExitState();
        }
        else if (mCursor == 1) { //To Settings Menu
            mMM.mSM.SetState(mMM.mStates[2]);
        }
        else if (mCursor == 2) { //To Exit Menu
            mMM.mSM.SetState(mMM.mStates[3]);
        }
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
        if (mMM.mSM.mPrevState == mMM.mStates[mMM.LOAD] || mMM.mSM.mPrevState == mMM.mStates[mMM.START]) {
            IFade.setPosition(BSGraphics.SCREEN_HEIGHT-500,0);
            IFade.RotateImage(BSSprite.TRANS_ROT180);
            mFadeIn=true;
        }
    }
    
    public void ExitState()
    {
        mMM.mStates[mMM.START].Init();
        mIsExitingState = true;
        mFadeOut = true;
        IFade.setPosition(-500,0);
    }
}