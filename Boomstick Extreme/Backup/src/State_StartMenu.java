/*
 * State_StartMenu.java
 *
 * Created on May 15, 2007, 11:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */

public class State_StartMenu extends BSState {
    
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    public BS_MenuManager mMM;  //Instance to menu manager
    
    public int mNumLevels;          //The total number of stages including bonus levels and demo level
    public boolean mLevelIsLocked[];
    public boolean mArrowsClicked[];
    private int mtheBackground;     //The main Background. You won't understand anyway.
    private int mthePoster_Locked;  //The main Locked Poster
    
    //For Fading
    private boolean mFadeIn;
    private boolean mFadeOut;
    private int mFadeSpeed;
    BSImage IFade;
    
    //For animation
    private boolean mDirection;
    private boolean mMoving;
    private int mDistance;
    private int mTravelled;
    private int mSpeed;
    
    
    //Images and Sprites
    private BSImage mBackground[];
    private BSImage mPoster_Locked[];
    private BSImage mPoster_Back;
    private BSImage mPoster_BG;
    private BSImage mPoster[];
    private BSImage mStage_Locked;
    private BSImage mStage_Back;
    private BSImage mStage[];
    private BSSprite mArrows[];
    private BSImage mPositionBar;
    private BSImage mPositionPoint;
    
    // Rendering Stuff
    private BSImage mPoster_In;
    private BSImage mPoster_Out;
    private BSImage mCurStage;
    
    /** Creates a new instance of State_StartMenu */
    public State_StartMenu(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 1;
        mNumOptions = 5;
        
        
        mFadeIn = mFadeOut = false;
        mFadeSpeed = 500/mApp.mCanvas.FPS;
        IFade = mGraphics.NewImage("/Images/Black_DiagonalRight.png",BSGraphics.SCREEN_HEIGHT-500,0);
        
        mNumLevels = 4;
        mLevelIsLocked = new boolean [mNumLevels];
        mLevelIsLocked[0] = false;
        mLevelIsLocked[1] = true;
        mLevelIsLocked[2] = true;
        mLevelIsLocked[3] = true;
        mtheBackground = mthePoster_Locked = 0;
        mDirection = mMoving = false;
        mDistance = 220;
        mTravelled = 0;
        mSpeed = mDistance*2/mApp.mCanvas.FPS;
        
        //Load Sprites and Images
        int seq[]={0}; int seq2[]={1};
        mBackground = new BSImage [2];
        for (int i=0;i<2;i++) {
            mBackground[i] = mGraphics.NewImage("/Images/Wall.png");
        }
        
        mPoster_Locked = new BSImage [2];
        mPoster_Locked[0] = mGraphics.NewImage("/Images/Poster_Locked.png");
        mPoster_Locked[1] = mGraphics.NewImage("/Images/Poster_Locked.png");
        mPoster_Back = mGraphics.NewImage("/Images/Poster_Back.png");
        mPoster = new BSImage [mNumLevels];
        mPoster[0] = mGraphics.NewImage("/Images/Poster_Demo.png");
        mPoster[1] = mGraphics.NewImage("/Images/Poster_1.png");
        mPoster[2] = mGraphics.NewImage("/Images/Poster_Locked.png");
        mPoster[3] = mGraphics.NewImage("/Images/Poster_Locked.png");
        mPoster_BG = mGraphics.NewImage("/Images/Poster.png");
                
        mStage_Locked = mGraphics.NewImage("/Images/Stage_Locked.png");
        mStage_Back = mGraphics.NewImage("/Images/Stage_Back.png");
        mStage = new BSImage [mNumLevels];
        mStage[0] = mGraphics.NewImage("/Images/Stage_Demo.png");
        mStage[1] = mGraphics.NewImage("/Images/Stage_1.png");
        mStage[2] = mGraphics.NewImage("/Images/Stage_2.png");
        mStage[3] = mGraphics.NewImage("/Images/Stage_Bonus1.png");
        
        mArrowsClicked = new boolean [2];
        mArrows = new BSSprite [2];
        mArrows[0] = mGraphics.NewSprite(mGraphics.NewImage("/Images/Arrow_Left.png"),48,48);
        mArrows[0].setPosition(2,(BSGraphics.SCREEN_WIDTH-48)/2);
        mArrows[1] = mGraphics.NewSprite(mGraphics.NewImage("/Images/Arrow_Right.png"),48,48);
        mArrows[1].setPosition(170,(BSGraphics.SCREEN_WIDTH-48)/2);
        
        for (int j=0;j<2;j++) {
            mArrows[j].addFrameSequence(seq);
            mArrows[j].addFrameSequence(seq2);
            mArrows[j].playSequence(0,false);
            mArrowsClicked[j] = false;
        }
        
        mPositionBar = mGraphics.NewImage("/Images/Position_Bar.png");
        mPositionPoint = mGraphics.NewImage("/Images/Position_Pointer.png",47+(mCursor*110/(mNumOptions-1)),1);
        
        mPoster_In = mPoster[mCursor];
        mPoster_Out = mPoster[mCursor-1];
        mCurStage = mStage[mCursor-1];
    }
    
    public void Init()
    {
        
    }
    
    //NULL
    public void Update() {
        // Process Button Flashing with clicked
        for (int i=0;i<2;i++) {
            if (mArrowsClicked[i])
            {
                mArrows[i].playSequence(1,false);
                mArrowsClicked[i] = false;
            }
            else if (mArrows[i].GetCurSequence() == 1) mArrows[i].playSequence(0,false);
        }
        
        // Process Background Movement
        if (mMoving) {
            if (mDirection) {
                mTravelled += mSpeed;
                mBackground[mtheBackground].Translate(mSpeed,0);
                mPoster_BG.Translate(mSpeed,0);
                mPoster_In.Translate(mSpeed,0);
                mPoster_Out.Translate(mSpeed,0);
                
            }
            else {
                mTravelled += mSpeed;
                mBackground[mtheBackground].Translate(0-mSpeed,0);
                mPoster_BG.Translate(0-mSpeed,0);
                mPoster_In.Translate(0-mSpeed,0);
                mPoster_Out.Translate(0-mSpeed,0);
            }
            if (mTravelled >= mDistance) {
                mPoster_BG.setPosition(0,0);
                mPoster_In.setPosition(0,0);
                mPoster_Out.setPosition(0-BSGraphics.SCREEN_HEIGHT,0);
                mPoster_Out = mPoster_In;
                mthePoster_Locked = 1 - mthePoster_Locked;
                mMoving = false;
            }
        }
        
        //Fading In and Out
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
                    if (mCursor == 0) {      //Back To Main Menu
                        mMM.mSM.SetState(mMM.mStates[0]);
                    }
                    else if (mCursor == 1) { //Start Tutorial Level
                        mMM.mSM.SetState(mApp.mGameCanvas);
                    }
                    else if (mCursor == 2) { //Start Level 1: Hum-Tharo
                        //mMM.mSM.SetState(mMM.mStates[3]);
                    }
                    else if (mCursor == 3) { //Start Level 2

                    }
                    else if (mCursor == 4) { //Start Bonus Level 1

                    }
                }
            }
            if (!mFadeIn && !mFadeOut) mIsEnteringState = mIsExitingState = false;
        }
    }
    
    //NULL
    public void Draw() {
        mBackground[mtheBackground].Render();
        int theX = mBackground[mtheBackground].mX;
        if (theX > 0) { //If shifted right
            mBackground[1-mtheBackground].setPosition(theX-400, 0);
            mBackground[1-mtheBackground].Render();
            if (theX >= BSGraphics.SCREEN_HEIGHT)
                mtheBackground = 1-mtheBackground;
        }
        else if (theX+400 < BSGraphics.SCREEN_HEIGHT) { //If shifted left
            mBackground[1-mtheBackground].setPosition(theX+400, 0);
            mBackground[1-mtheBackground].Render();
            if (theX+400 <= 0)
                mtheBackground = 1-mtheBackground;
        }
        mPoster_BG.Render();
                
        mPositionBar.Render();
        mPositionPoint.Render();
        
        mCurStage.Render();
        mPoster_Out.Render();
        if (mMoving) mPoster_In.Render();
        
        mArrows[0].Render();
        mArrows[1].Render();
        
        if (mFadeIn || mFadeOut) IFade.Render();
    }
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
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
        if (mMoving) return;
        if (Scroll_Down) {
            mCursor++;
            if (mCursor >= mNumOptions) mCursor = 0;
            mArrowsClicked[1] = true;
        }
        else {
            mCursor--;
            if (mCursor < 0) mCursor = mNumOptions - 1;
            mArrowsClicked[0] = true;
        }
        BeginMove(!Scroll_Down);
        mPositionPoint.setPosition(47+(mCursor*110/(mNumOptions-1)),1);
    }
    
    //BASE
    /* Performs an action (like traversing into another menu)
     * when an option in the menu is selected. */
    public void Select() {
        
        if (mCursor == 0) ExitState();
        else if (mLevelIsLocked[mCursor-1]) {
            System.out.println("Level is locked!");
        }
        else {
            ExitState();
        }
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
        mCursor = 1;
        mPositionPoint.setPosition(47+(mCursor*110/(mNumOptions-1)),1);
        mtheBackground = 0;
        mBackground[mtheBackground].setPosition(0, 0);
        mPoster_Out = mPoster[mCursor-1];
        mPoster_Out.setPosition(0,0);
        mCurStage = mStage[mCursor-1];
        
        IFade.setPosition(BSGraphics.SCREEN_HEIGHT-500,0);
        IFade.RotateImage(BSSprite.TRANS_ROT180);
        mFadeIn=true;
    }
    
    public void ExitState()
    {
        mIsExitingState = true;
        mFadeOut = true;
        IFade.setPosition(-500,0);
    }
    
    public void BeginMove(boolean Direction)
    {
        mDirection = Direction;
        mMoving = true;
        mTravelled = 0;
       
        if (mCursor == 0) {
            mPoster_In = mPoster_Back;
            mCurStage = mStage_Back;
        }
        else {
            mPoster_In = GetPoster(mCursor-1);
            mCurStage = GetStage(mCursor-1);
        }

        if (Direction)
            mPoster_In.setPosition(0-BSGraphics.SCREEN_HEIGHT,0);
        else
            mPoster_In.setPosition(BSGraphics.SCREEN_HEIGHT,0);
    }
    public BSImage GetPoster(int thePoster)
    {
        if (mLevelIsLocked[thePoster]) {
            return mPoster_Locked[1-mthePoster_Locked];
        }
        return mPoster[thePoster];
    }
    public BSImage GetStage(int theStage)
    {
        if (mLevelIsLocked[theStage]) {
            return mStage_Locked;
        }
        return mStage[theStage];
    }
}
