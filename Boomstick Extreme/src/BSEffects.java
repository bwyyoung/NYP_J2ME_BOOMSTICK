/*
 * BSEffects.java
 *
 * Created on June 27, 2007, 3:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.util.*;

class EffectAnim
{
    int ID;
    int Anim[];
}

/**
 *
 * @author Bryan
 */
public class BSEffects {
    BSHoleManager mMgr;
    BSMultiHole mHole;
    
    BSSprite mFXSprite;
    
    int x; 
    int y;
    
    int NumSequences = 0;
    Vector Sequences;
    
    Random RanGenerator;
    
    /** Creates a new instance of BSEffects */
    public BSEffects() 
    {
        
    }
    
    public void Init(String theName, int Width, int Height, int X, int Y, int CenterPixelX, int CenterPixelY, BSHoleManager theMgr, BSMultiHole theHole)
    {
        mMgr = theMgr;
        mHole = theHole;
        
        mFXSprite = theMgr.mApp.mGraphics.NewSprite(theMgr.mApp.mGameCanvas.mResources.GetImageThrow(theName), Width, Height);
        x = X;
        y = Y;
        
        mFXSprite.setRefPixel(CenterPixelX, CenterPixelY);
        mFXSprite.setPosition(X, Y);
        
        Sequences = new Vector();
        RanGenerator = new Random();
    }
    
    public void AddEffect(int theEffect[])
    {
        EffectAnim i = new EffectAnim();
        i.ID = mFXSprite.addFrameSequence(theEffect);
        //i.Anim = theEffect;
        Sequences.setSize(NumSequences + 1);
        Sequences.setElementAt(i, NumSequences);
        NumSequences ++;
    }
    
    public void Render()
    {
        mFXSprite.setPosition(x, y);
        mFXSprite.Render();
    }
    
    public void Update()
    {
        mFXSprite.Update();
    }
    
    public void Shot()
    {
        if (NumSequences > 1)
        {
            int CurShot = RanGenerator.nextInt(NumSequences);
            if (!mFXSprite.GetIsAnimating())
                mFXSprite.playSequence(((EffectAnim)Sequences.elementAt(CurShot)).ID, false);
        }
        else
        {
            if (!mFXSprite.GetIsAnimating())
                mFXSprite.playSequence(((EffectAnim)Sequences.elementAt(0)).ID, false);
        }
    }
}
