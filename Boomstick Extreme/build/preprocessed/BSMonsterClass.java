/*
 * BSMonsterClass.java
 *
 * Created on May 13, 2007, 4:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan Yeo
 */

public class BSMonsterClass extends BSHole {
    
    String mName;
    int mHealth;
    int mATKDamage;
    int mScoreWorth;
    int mPowerUpType;
    int mState = 0;
    
    BSSprite mMonsterSprite;
    BSHoleManager mMgr;
    
    int Monster_Appear;
    int Monster_Idle;
    int Monster_Gone;
    int Monster_Return;
    int Monster_Death;
    int Monster_Attack;
    
    BSMultiHole mHole;
    
    boolean mAlive;
    boolean ReturnHole;
    
    int mResetHP;
    int mResetLifespan;
    
    /** Creates a new instance of BSMonsterClass */
    public BSMonsterClass() 
    {
        
    }
    
    public void Init(BSHoleManager theMgr, BSMultiHole theHole, int theHealth, int theLifespan)
    {
        mMgr = theMgr;
        mHole = theHole;
        
        mResetHP = theHealth;
        mResetLifespan = theLifespan;
        
        ReturnHole = false;
        mAlive = true;
    }
    
    public void Reset()
    {
        mState = 0;
        mLifeSpan = mResetLifespan;
        ReturnHole = false;
        mAlive = true;
        mHealth = mResetHP;
        mMonsterSprite.playSequence(Monster_Appear, false);
    }
    
    public void Update()
    {
        
    }
    
    public void Render()
    {
        mMonsterSprite.setPosition(X, Y);
        mMonsterSprite.Render();
    }
    
    public void Shot(int theDamage)
    {
        if (mAlive)
        {
            mHealth -= theDamage;
            
            if (mHealth <= 0)
            {
                mAlive = false;
                mState = 1;
                
                ReturnHole = true;
                mMonsterSprite.playSequence(Monster_Death, false, BSCanvas.MSPF);
            }
        }
    }
}
