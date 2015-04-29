/*
 * BSJumpster.java
 *
 * Created on June 17, 2007, 12:58 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSJumpster extends BSMonsterClass {    
    /** Creates a new instance of BSPopster */
    public BSJumpster(BSHoleManager theMgr, BSMultiHole theHole) {
        Init(theMgr, theHole, 2, 2000);
        mMonsterSprite = mMgr.mApp.mGraphics.NewSprite(mMgr.mApp.mGameCanvas.mResources.GetImageThrow("Jumpster"), 30, 38);
        int Popping[] = { 0, 1, 2, 3 };
        int Empty[] = {8};
        int ReturntoHole[] = { 3, 2, 1, 0, 8 };
        int Death[] = { 4, 5, 6, 7, 8 };
        
        mMonsterSprite.addFrameSequence(Popping);
        mMonsterSprite.addFrameSequence(Empty);
        mMonsterSprite.addFrameSequence(ReturntoHole);
        mMonsterSprite.addFrameSequence(Death);
        
        Monster_Appear = 0;
        Monster_Idle = 0;
        Monster_Gone = 1;
        Monster_Return = 2;
        Monster_Death = 3;
        
        Reset();
        mMonsterSprite.playSequence(Monster_Appear, false, mLifeSpan/2/4);
    }
    
    public void Update()
    {
        mMonsterSprite.Update();
        mLifeSpan -= BSCanvas.MSPF;
        
        if (mLifeSpan < mResetLifespan/2 && ReturnHole == false)
        {
            ReturnHole = true;
            mMonsterSprite.playSequence(Monster_Return, false);
        }
        
        if (mLifeSpan <= 0)
        {
            mLifeSpan = 0;
            mState = 3;
            mHole.SetDrawIndex(0);
            mHole.mHoleState = BSHole.HOLE_EMPTY;
        }
    }
}
