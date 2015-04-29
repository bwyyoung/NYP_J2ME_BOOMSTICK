import java.util.Random;
/*
 * BSPopster.java
 *
 * Created on June 14, 2007, 2:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSPopster extends BSMonsterClass {
    Random gen;
    boolean Thrown;
            
    /** Creates a new instance of BSPopster */
    public BSPopster(BSHoleManager theMgr, BSMultiHole theHole) {
        Init(theMgr, theHole, 1, 2000);
        mMonsterSprite = mMgr.mApp.mGraphics.NewSprite(mMgr.mApp.mGameCanvas.mResources.GetImageThrow("Popster"), 30, 38);
        int Popping[] = { 0, 1, 2, 3 };
        int Idle[] = { 4, 5 };
        int Empty[] = {6};
        int ReturntoHole[] = { 11, 10, 9, 8, 6 };
        int Death[] = { 12, 13, 14, 15, 6 };
        int Attack[] = {7};
        
        mMonsterSprite.addFrameSequence(Popping);
        mMonsterSprite.addFrameSequence(Idle);
        mMonsterSprite.addFrameSequence(Empty);
        mMonsterSprite.addFrameSequence(ReturntoHole);
        mMonsterSprite.addFrameSequence(Death);
        Monster_Attack = mMonsterSprite.addFrameSequence(Attack);
        
        Monster_Appear = 0;
        Monster_Idle = 1;
        Monster_Gone = 2;
        Monster_Return = 3;
        Monster_Death = 4;
        
        gen = new Random();
        Thrown = false;
        
        Reset();
    }

    public Random getGen() {
        return gen;
    }
    
    public void Reset()
    {
        mState = 0;
        mLifeSpan = mResetLifespan;
        ReturnHole = false;
        mAlive = true;
        mHealth = mResetHP;
        Thrown = false;
        mMonsterSprite.playSequence(Monster_Appear, false);
    }
    
    public void Update()
    {
        mMonsterSprite.Update();
        mLifeSpan -= BSCanvas.MSPF;
        
        if (mLifeSpan < mResetLifespan - BSCanvas.MSPF * 4 && mLifeSpan > BSCanvas.MSPF * 4 && mAlive == true)
        {
            if (Thrown != true)
                mMonsterSprite.playSequence(Monster_Idle, true);
            
            if (!Thrown)
            {
                int r = gen.nextInt(10);
                if (r == 1)
                {
                    Thrown = true;
                    //Add player damage here.
                    //System.out.println("Thrown!");
                    mMonsterSprite.playSequence(Monster_Attack, false);
                    mMgr.mApp.mGameCanvas.mPlayerModel.Damage(1);
                }
            }
        }
        
        if (mLifeSpan < BSCanvas.MSPF * 4 && ReturnHole == false)
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
