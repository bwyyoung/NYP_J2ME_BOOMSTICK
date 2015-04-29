/*
 * State_Intro.java
 *
 * Created on June 23, 2007, 2:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */
public class State_Intro extends BSState {
    public BS_MenuManager mMM;  //Instance to menu manager
    //BSMenuEffects mME;
    int mCount;
    
    /** Creates a new instance of State_Intro */
    public State_Intro(BS_MenuManager theMgr) {
        mMM = theMgr;
        mMM.mSM.NewState(this);
        //mME = new BSMenuEffects(mApp);
        
        mCount = 0;
    }
    
    public void Init()
    {
        mMM.mStates[mMM.MAIN].Init();
        mMM.mStates[mMM.START].Init();
        mMM.mStates[mMM.SETTINGS].Init();
        mMM.mStates[mMM.ABOUT].Init();
        //mME.Init();
    }
    public void EnterState()
    {
        mIsEnteringState = true;
    }
    public void ExitState()
    {
        mIsExitingState = true;
        //mME.FadeOut();
    }
    public void Update()
    {
        mCount++;
        //if (mCount == 1*mApp.mCanvas.FPS) mME.FadeIn();
        if (mCount == 2*mApp.mCanvas.FPS) ExitState();
        //if (mIsExitingState && mME.mCompleted) mMM.mSM.SetState(mMM.mStates[mMM.MAIN]);
        if (mIsExitingState) mMM.mSM.SetState(mMM.mStates[mMM.MAIN]);
        //mME.Update();
    }
    public void Draw()
    {
        //if (mME.mCompleted && mME.mCurEffect == 2);
        //else if (mME.mCurEffect == 0);
        //else {
            mGraphics.drawBMString("Simian Softwerks", BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH - 50, 0, 255, 255, 255, BSGraphics.HCENTER|BSGraphics.TOP);
            mGraphics.drawBMString("Presents", BSGraphics.SCREEN_HEIGHT/2, BSGraphics.SCREEN_WIDTH - 38, 0, 255, 255, 255, BSGraphics.HCENTER|BSGraphics.TOP);
        //}
        //mME.Render();
    }
    public void GetKeyPressed(int KeyPressed)
    {
        if (mIsExitingState) return;
        ExitState();
    }
    public void Destroy() {
        //mME.Destroy();
    }
}