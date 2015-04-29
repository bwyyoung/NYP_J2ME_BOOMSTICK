/*
 * BSItem.java
 *
 * Created on June 21, 2007, 10:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public abstract class BSItem extends BSHole {
    BSSprite mItemSprite;
    BSHoleManager mMgr;
    BSMultiHole mHole;
    
    int Item_Appear;
    int Item_Empty;
    int Item_Return;
    
    int mLifeSpan;
    int mResetSpan;
    
    boolean mShot;
    boolean Returning;
            
    /** Creates a new instance of BSItem */
    public BSItem() {
        
    }
    
    public void Init(BSHoleManager theMgr, BSMultiHole theHole, String theSpriteName, int theLifeSpan)
    {
        mMgr = theMgr;
        mHole = theHole;
        mItemSprite = mMgr.mApp.mGraphics.NewSprite(mMgr.mApp.mGameCanvas.mResources.GetImageThrow(theSpriteName), 30, 38);
        
        int Appear[] = { 0, 1, 2, 3 };
        int Empty[] = { 4 };
        int Return[] = { 3, 2, 1, 0, 4 };
        
        Item_Appear = mItemSprite.addFrameSequence(Appear);
        Item_Empty = mItemSprite.addFrameSequence(Empty);
        Item_Return = mItemSprite.addFrameSequence(Return);
     
        mLifeSpan = theLifeSpan;
        mResetSpan = mLifeSpan;
        
        mItemSprite.setPosition(X, Y);
        
        Returning = false;
        mShot = false;
        
        mItemSprite.playSequence(Item_Appear, false);
    }
    
    public void Render()
    {
        mItemSprite.setPosition(X, Y);
        mItemSprite.Render();
    }
    
    public void Reset()
    {
        mLifeSpan = mResetSpan;
        Returning = false;
        mShot = false;
        mItemSprite.playSequence(Item_Appear, false);
    }
    
    public void Update()
    {
        mItemSprite.Update();
        mLifeSpan -= BSCanvas.MSPF;
        
        if (mShot)
        {
            mItemSprite.playSequence(Item_Empty, false);
        }
        else if (mLifeSpan < BSCanvas.MSPF * 4 && Returning == false)
        {
            Returning = true;
            mItemSprite.playSequence(Item_Return, false);
        }
        
        if (mLifeSpan <= 0)
        {
            mHole.mHoleState = BSHole.HOLE_EMPTY;
            Reset();
        }
    }
    
    public void Shot(int theDamage)
    {
        if (!mShot)
        {
            Effect();
            mHole.mHoleState = BSHole.HOLE_EMPTY;
            mShot = true;
        }
    }
    
    public abstract void Effect();
}
