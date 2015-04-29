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
class Option {
    BSMainApp mApp;
    int mX,mY;
    String value;
    
    public Option(String Value, int X, int Y, BSMainApp App)
    {
        mApp = App;
        value = Value;
        mX = X; mY = Y;
    }
    public void Render()
    {
        mApp.mGraphics.drawBMString(value, mX, mY, 0, 0, 0, 0, BSGraphics.LEFT|BSGraphics.TOP);
    }
}
public class State_MainMenu extends BSState {
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    Option mOptions[];
    public BS_MenuManager mMM;  //Instance to menu manager
    //BSMenuEffects mME;
    final int optX = 101; final int optY = 83;
    
    BSImage ICursor;
    BSImage IBackground;
    
    
    /** Creates a new instance of State_MainMenu */
    public State_MainMenu(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 0;
        mNumOptions = 4;
        mOptions = new Option [mNumOptions];
        
        mOptions[0] = new Option("Start", optX + 20, optY, mApp);
        mOptions[1] = new Option("Settings", optX + 20, optY+21, mApp);
        mOptions[2] = new Option("About", optX + 20, optY+42, mApp);
        mOptions[3] = new Option("Exit", optX + 20, optY+63, mApp);
        
        //mME = new BSMenuEffects(mApp);
    }
    
    public void Init()
    {
        //Load Images
        ICursor = mMM.mResources.GetImageThrow("Pointer_White");
        ICursor.setPosition(optX, optY);
        IBackground = mMM.mResources.GetImageThrow("MainMenu");
        //mME.Init();
    }
    
    //NOMINAL
    public void Update() {
        //if (mME.mCurEffect == 0)
        if (mIsEnteringState)
            mIsEnteringState = mIsExitingState = false;
        //if (mME.mCompleted)
        //{
            //if (mIsExitingState && mME.mCurEffect == 2) {
                //mMM.mSM.SetState(mMM.mStates[mMM.START]);
            //}
            if (mIsExitingState) {
                mMM.mSM.SetState(mMM.mStates[mMM.START]);
                mIsEnteringState = mIsExitingState = false;
            }
        //}
        //mME.Update();
    }
    
    //NOMINAL
    public void Draw() {
        IBackground.Render();
        ICursor.Render();
        for (int i=0;i<mNumOptions;i++)
            mOptions[i].Render();
        //mME.Render();
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
        ICursor.setPosition(optX, optY+mCursor*21);
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
        else if (mCursor == 2) { //To About Menu
            mMM.mSM.SetState(mMM.mStates[mMM.ABOUT]);
        }
        else if (mCursor == 3) { //To Exit Game
            //mMM.mSM.SetState(mMM.mStates[3]);
            mApp.mCanvas.mApp.notifyDestroyed();
        }
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
        /*if (mMM.mSM.mPrevState == mApp.mState_Intro || mMM.mSM.mPrevState == mMM.mStates[mMM.START]) {
            mME.FadeIn();
        }*/
    }
    
    public void ExitState()
    {
        mMM.mStates[mMM.START].Init();
        mIsExitingState = true;
        //mME.FadeOut();
    }
    
    public void Destroy() {
        ICursor = IBackground = null;
        //mME.Destroy();
    }
}