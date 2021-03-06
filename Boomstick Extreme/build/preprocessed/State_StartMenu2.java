/*
 * State_StartMenu2.java
 *
 * Created on June 22, 2007, 3:06 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */
class MergedPoster {
    BSMainApp mApp;
    BSImage mPoster;
    BSImage mContent;
    BSImage mPosterLocked;
    public boolean mRenderPoster;
    public boolean mRenderStage;
    public boolean mLocked;
    
    String mName;
    int mReward;
    String mStage; //Stage text (the level)
    
    //Physical Properties
    public int mX, mY;
    
    public MergedPoster(BSMainApp theApp)
    {
        mApp = theApp;
        mPoster = mApp.mMenuManager.mResources.GetImageThrow("Poster");
        mPosterLocked = mContent = mApp.mMenuManager.mResources.GetImageThrow("Poster_Locked");
        mRenderPoster = mRenderStage = true;
        mLocked = true;
        mX = mY = 0;
        mName = "Stage Locked";
        mReward = 0;
        mStage = "Locked";
    }
    public MergedPoster(BSMainApp theApp, String theContentName, String theName, int theReward, String theStage, boolean Locked)
    {
        mApp = theApp;
        mPoster = mApp.mMenuManager.mResources.GetImageThrow("Poster");
        mPosterLocked = mApp.mMenuManager.mResources.GetImageThrow("Poster_Locked");
        mContent = mApp.mMenuManager.mResources.GetImageThrow(theContentName);
        mRenderPoster = mRenderStage = true;
        mLocked = Locked;
        mX = mY = 0;
        mName = theName;
        mReward = theReward;
        mStage = theStage;
    }
    public void Render()
    {
        if (mRenderPoster) {
            mPoster.setPosition(mX, mY);
            mPoster.Render();
            mApp.mGraphics.drawBMString(mName, mX+BSGraphics.SCREEN_HEIGHT/2, mY+130, 0, 0, 0, 0, BSGraphics.HCENTER|BSGraphics.TOP);
            mApp.mGraphics.drawBMString("$"+String.valueOf(mReward), mX+BSGraphics.SCREEN_HEIGHT/2, mY+145, 0, 0, 0, 0, BSGraphics.HCENTER|BSGraphics.TOP);
        }
        if (mLocked) {
            mPosterLocked.setPosition(mX, mY);
            mPosterLocked.Render();
            if (mRenderStage)
                mApp.mGraphics.drawBMString("Locked", 217, 2, 0, 255, 255, 255, BSGraphics.RIGHT|BSGraphics.TOP);
        }
        else {
            mContent.setPosition(mX, mY);
            mContent.Render();
            if (mRenderStage)
                mApp.mGraphics.drawBMString(mStage, 217, 2, 0, 255, 255, 255, BSGraphics.RIGHT|BSGraphics.TOP);
        }
    }
    public void Destroy() {
        mPoster = mContent = mPosterLocked = null;
    }
}
public class State_StartMenu2 extends BSState {
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    public BS_MenuManager mMM;  //Instance to menu manager
    
    private int mTheBackground;
    public boolean mArrowsClicked[];
    MergedPoster mMPosters[];
    MergedPoster mMPosterIn; MergedPoster mMPosterOut;    //Instances
    
    //Images and Sprites
    BSImage mBackground[];
    BSImage mPositionBar;
    BSImage mPositionPoint;
    BSSprite mArrows[];
    
    //For Fading
    //BSMenuEffects mME;          ////<-Memory stuff
    
    //For animation
    private boolean mDirection;
    private boolean mMoving;
    private int mDistance;
    private int mTravelled;
    private int mSpeed;
    
    BSFileReader fin;
    
    /** Creates a new instance of State_StartMenu2 */
    public State_StartMenu2(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 1;
        mNumOptions = 5;
        
        //Background
        mTheBackground = 0;
        
        //mME = new BSMenuEffects(mApp);
        
        mDirection = mMoving = false;
        mDistance = 220;
        mTravelled = 0;
        mSpeed = mDistance*2/mApp.mCanvas.FPS;
        
        //File Reading
        fin = new BSFileReader();
    }
    
    public void ReadFile() {
        fin.Open("/Levels.txt");
        mNumOptions = 1 + Integer.parseInt(fin.ReadLine());
        
        if (mApp.mLevelPaths == null)   
            mApp.mLevelPaths = new String[mNumOptions-1];
        //Init Posters
        mMPosters = new MergedPoster [mNumOptions];
        mMPosters[0] = new MergedPoster(mApp,"Poster_Back"  ,"" ,0 ,"Go Back",false); mMPosters[0].mRenderPoster = false;
        String Header, Name, Stage; int Reward;
        for (int i=1;i<(mNumOptions);i++) {
            Header=fin.ReadLine(); Name=fin.ReadLine(); Reward=Integer.parseInt(fin.ReadLine()); Stage=fin.ReadLine();
            System.out.println(Header+" "+Name+" "+Reward+" "+Stage);

            mMPosters[i] = new MergedPoster(mApp,Header,Name,Reward,Stage,false);
            mApp.mLevelPaths[i-1] = fin.ReadLine();
            //mMPosters[2] = new MergedPoster(mApp,"Poster_1"     ,"Stage 1",true);
            //mMPosters[3] = new MergedPoster(mApp,"Poster_Locked","Stage 2",true);
            //mMPosters[4] = new MergedPoster(mApp,"Poster_Locked","Bonus",true);
        }
        
        try
        {
            byte leveldata[] = mApp.Rec.getRecord(2);
            mApp.UnlockedLevels = new boolean[mNumOptions - 1];
            for (int i = 1; i < mNumOptions; i ++)
            {
                mApp.UnlockedLevels[i - 1] = BSRms.CheckBoolean(leveldata[i - 1]);
            }
        }
        catch (Exception e)
        {
            mApp.UnlockedLevels = new boolean[mNumOptions - 1];
            for (int i = 1; i < mNumOptions; i ++)
                mApp.UnlockedLevels[i - 1] = false;
            mApp.UnlockedLevels[0] = true;
        }
    }
    
    public void Init()
    {
        mBackground = new BSImage [2];
        
        for (int i=0;i<2;i++) {
            mBackground[i] = mMM.mResources.GetImageThrow("Wall");
        }
        
        ReadFile();
        mMPosterIn = mMPosters[2]; mMPosterOut = mMPosters[1];
        
        //Position Bar
        mPositionBar = mMM.mResources.GetImageThrow("Bar");
        mPositionPoint = mMM.mResources.GetImageThrow("Pointer_Red");
        mPositionPoint.setPosition(47+(mCursor*110/(mNumOptions-1)),1);
        
        //Arrows
        mArrows = new BSSprite [2];
        mArrows[0] = mGraphics.NewSprite(mMM.mResources.GetImageThrow("LeftArrow"),48,48);
        mArrows[1] = mGraphics.NewSprite(mMM.mResources.GetImageThrow("RightArrow"),48,48);
        mArrows[0].setPosition(2,(BSGraphics.SCREEN_WIDTH-48)/2);
        mArrows[1].setPosition(170,(BSGraphics.SCREEN_WIDTH-48)/2);
        
        mArrowsClicked = new boolean [2];
        int seq[]={0}; int seq2[]={1};
        for (int j=0;j<2;j++) {
            mArrows[j].addFrameSequence(seq);
            mArrows[j].addFrameSequence(seq2);
            mArrows[j].playSequence(0,false);
            mArrowsClicked[j] = false;
        }
        
        //mME.Init();
    }
    
    public void EnterState() {
        mIsEnteringState = true;
        mCursor = 1;
        mPositionPoint.setPosition(47+(mCursor*110/(mNumOptions-1)),1);
        mTheBackground = 0;
        mBackground[mTheBackground].setPosition(0, 0);
        mMPosterOut = mMPosters[mCursor];
        mMPosterOut.mX = 0;
        
        //mME.FadeIn();
    }
    
    public void ExitState()
    {
        mIsExitingState = true;
        //mME.FadeOut();
    }
    public void Update()
    {
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
                mBackground[mTheBackground].Translate(mSpeed,0);
                mMPosterOut.mX += mSpeed; mMPosterIn.mX += mSpeed;
            }
            else {
                mTravelled += mSpeed;
                mBackground[mTheBackground].Translate(0-mSpeed,0);
                mMPosterOut.mX -= mSpeed; mMPosterIn.mX -= mSpeed;
            }
            if (mTravelled >= mDistance) {
                mMPosterIn.mX = 0;
                mMPosterIn.mRenderStage = true;
                mMPosterOut.mX = 0-BSGraphics.SCREEN_HEIGHT;
                mMPosterOut = mMPosterIn;
                mMoving = false;
            }
        }
        
        //Fading In and Out
        if (mIsEnteringState || mIsExitingState) {
            //if (mME.mCompleted) {
                //if (mME.mCurEffect == 2) {
                if (mIsExitingState) {
                    if (mCursor == 0) {      //Back To Main Menu
                        mMM.mSM.SetState(mMM.mStates[0]);
                    }
                    else { //Enter Levels Level
                        //mApp.mGameCanvas = null;

                        if (mApp.UnlockedLevels[mCursor - 1])
                        {
                            mMM.DeInitMenus();
                            mMM.mResources.mResources = null;
                            mMM.mResources.mLoadedResources = 0;
                            System.gc();
                            //mApp.mGameCanvas = new BSGameCanvas(mMM);

                            mApp.mCurLevel = mCursor-1;     //HERE IS WHERE YOU CHANGE THE CURRENT LEVEL
                            mApp.mGameCanvas.StartLevel(mApp.mLevelPaths[mApp.mCurLevel]);

                            BSLoadScreen LoadScreen = new BSLoadScreen(mMM, "/Resources.txt", mApp.mGameCanvas.mResources, mApp.mGameCanvas, false);
                            mMM.mSM.SetState(LoadScreen);
                        }

                    }
                }
                mIsEnteringState = mIsExitingState = false;
            //}
        }
        //mME.Update();
    }
    public void Draw()
    {
        //Background Rendering for infinite length
        mBackground[mTheBackground].Render();
        int theX = mBackground[mTheBackground].mX;
        if (theX > 0) { //If shifted right
            mBackground[1-mTheBackground].setPosition(theX-400, 0);
            mBackground[1-mTheBackground].Render();
            if (theX >= BSGraphics.SCREEN_HEIGHT)
                mTheBackground = 1-mTheBackground;
        }
        else if (theX+400 < BSGraphics.SCREEN_HEIGHT) { //If shifted left
            mBackground[1-mTheBackground].setPosition(theX+400, 0);
            mBackground[1-mTheBackground].Render();
            if (theX+400 <= 0)
                mTheBackground = 1-mTheBackground;
        }
        //Position Bar
        mPositionBar.Render();
        mPositionPoint.Render();
        
        //Poster and Content Rendering
        mMPosterOut.Render();
        if (mMoving) {
            mMPosterIn.Render();
        }
        
        mArrows[0].Render();
        mArrows[1].Render();
        //mME.Render();    //Fading Rendering
        
    }
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
    
    public void BeginMove(boolean Direction)
    {
        mDirection = Direction;
        mMoving = true;
        mTravelled = 0;
        
        mMPosterIn = mMPosters[mCursor];
        mMPosterIn.mRenderStage = false;

        if (Direction) {
            mMPosterIn.mX = 0-BSGraphics.SCREEN_HEIGHT;
        }
        else {
            mMPosterIn.mX = BSGraphics.SCREEN_HEIGHT;
        }
    }
    
    //BASE
    /* Performs an action (like traversing into another menu)
     * when an option in the menu is selected. */
    public void Select() {
        if (mMPosters[mCursor].mLocked) {
            System.out.println("Level is locked!");
        }
        else {
            ExitState();
        }
    }
    
    public void Destroy()
    {
        for (int i=0;i<mNumOptions;i++) {
            mMPosters[i].Destroy();
            mMPosters[i] = null;
        }
        mMPosterIn = mMPosterOut = null;
        mBackground[0] = mBackground[1] = null;
        mPositionBar = mPositionPoint = null;
        mArrows[0] = mArrows[1] = null;
        //mME.Destroy();
    }
}
