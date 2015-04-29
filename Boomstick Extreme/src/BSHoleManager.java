/*
 * BSHoleManager.java
 *
 * Created on May 13, 2007, 4:55 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.*;
import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;

/**
 *
 * @author Bryan Yeo
 */

class BSMultiHole
{
    public int DrawIndex;
    BSHole Holes[];
    BSHoleManager mMgr;
    int mHoleState;
    BSEffects mEffects[];
    
    int WeapID = -1;
            
    int X;
    int Y;
    
    public int GetEntity()
    {
        return DrawIndex;
    }
    
    public BSMultiHole(BSHoleManager theMgr)
    {
        mMgr = theMgr;
        Holes = new BSHole[5];
        Holes[0] = new BSHole();
        Holes[0].Init(mMgr);
        Holes[1] = new BSPopster(mMgr, this);
        Holes[2] = new BSJumpster(mMgr,this);
        Holes[3] = new BSHealthPack(mMgr, this);
        Holes[4] = new BSAmmo(mMgr, this);
        
        mEffects = new BSEffects[3];
        mEffects[BSPlayerModel.FINGERSNAME] = new BSEffectsFinger(mMgr, this);
        mEffects[BSPlayerModel.BOOMSTICKNAME] = new BSEffectsShotgun(mMgr, this);
        mEffects[BSPlayerModel.NAPALMNAME] = null;
        
        DrawIndex = 0;
        mHoleState = BSHole.HOLE_EMPTY;
    }
    
    void SyncHoles(int x, int y)
    {
        X = x;
        Y = y;
        for (int i = 0; i < 5; i ++)
        {
            Holes[i].X = X;
            Holes[i].Y = Y;
        }
        
        for (int i = 0; i < 3; i ++)
        {
            if (mEffects[i] != null)
            {
                mEffects[i].x = x + 15;
                mEffects[i].y = y + 19;
            }
        }
    }
    
    void SetDrawIndex(int i)
    {
        DrawIndex = i;
        Holes[i].Reset();
    }
    
    void SyncHoles()
    {
        Holes[DrawIndex].X = X;
        Holes[DrawIndex].Y = Y;
        
        for (int i = 0; i < 3; i ++)
        {
            if (mEffects[i] != null)
            {
                mEffects[i].x = X + 15;
                mEffects[i].y = Y + 19;
            }
        }
    }
    
    void Update()
    {
        Holes[DrawIndex].Update();
        if (WeapID != -1)
            if (mEffects[WeapID] != null)
                mEffects[WeapID].Update();
    }
    
    void Render()
    {
        Holes[DrawIndex].Render();
        if (WeapID != -1)
            if (mEffects[WeapID] != null)
                mEffects[WeapID].Render();
    }
}

public class BSHoleManager
{
    BSMainApp mApp;
    BSMap mCurMap = null;
    public BSMultiHole mHoles[];
    
    long mTimePass = 0;
    long mLastTime = 0;
    int mCurSequence = 0;
    
    int X = 0;
    int Y = 0;
    
    /** Creates a new instance of BSHoleManager */
    public BSHoleManager(BSGameCanvas theCanvas) 
    {
        mApp = theCanvas.mApp;
        mHoles = new BSMultiHole[9];
        
        X = 45;
        Y = 40;
        
        int x = X;
        int y = Y;
        for (int i = 0; i < 3; i ++)
        {
            for (int j = 0; j < 3; j ++)
            {
                int ind = i * 3 + j;
                mHoles[ind] = new BSMultiHole(this);
                mHoles[ind].SyncHoles(x, y);
                x += 50;
            }
            x = X;
            y += 28;
        }
    }
    
    public void SetMap(BSMap theMap)
    {
        mCurMap = theMap;
    }
    
    // BASE: Untested - Updates holes based on map.
    public void Update()
    {
        for (int i = 0; i < 9; i ++)
            mHoles[i].Update();
        
        if (mCurMap != null)
        {
            long curTime = System.currentTimeMillis();
            if (mLastTime > 0)
                mTimePass += curTime - mLastTime;
            
            mLastTime = curTime;
        }
        
        if (mTimePass >= (long) mCurMap.mSequenceTime)
        {
            //Time to update...
            mTimePass = 0;
            int x = 0;
            int y = 0;
            for (int i = 0; i < 3; i ++)
            {
                for (int j = 0; j < 3; j ++)
                {
                    int ind = i * 3 + j;
                    
                    if (mHoles[ind].mHoleState == BSHole.HOLE_EMPTY || mHoles[ind].mHoleState == BSHole.HOLE_BURNING)
                        switch (mCurMap.mHoles[mCurSequence][ind])
                        {
                            case 0:
                                mHoles[ind].SetDrawIndex(0);
                                mHoles[ind].mHoleState = BSHole.HOLE_EMPTY;
                                break;
                            //Monster - Popster
                            case 1:
                                mHoles[ind].SetDrawIndex(1);
                                mHoles[ind].mHoleState = BSHole.HOLE_FILLED;
                                break;
                            //Monster - Jumpster
                            case 2:
                                mHoles[ind].SetDrawIndex(2);
                                mHoles[ind].mHoleState = BSHole.HOLE_FILLED;
                                break;
                            //Item - Health Pack
                            case 3:
                                mHoles[ind].SetDrawIndex(3);
                                mHoles[ind].mHoleState = BSHole.HOLE_FILLED;
                                break;
                            //Item - Ammo Pack
                            case 4:
                                mHoles[ind].SetDrawIndex(4);
                                mHoles[ind].mHoleState = BSHole.HOLE_FILLED;
                                break;
                        }
                }
            }
            
            mCurSequence ++;
            
            if (mCurSequence >= mCurMap.mSequences)
            {
                mApp.mGameCanvas.mWonGame = true;
                if (mApp.mCurLevel + 1 != mApp.UnlockedLevels.length)
                    mApp.UnlockedLevels[mApp.mCurLevel + 1] = true;
                BSRms.SaveUnlockedLevels(mApp.UnlockedLevels, mApp.Rec);
            }
        }
    }
    
    // BASE: Untested - Renders the monsters
    public void Render()
    {
        if (mCurMap != null)
            for (int i = 0; i < 9; i ++)
            {
                    mHoles[i].Render();
            }
    }
    
    public void HoleIsShot(int HoleNum, int theDamage, int theWeapon)
    {
        mHoles[HoleNum].WeapID = theWeapon;
        if (mHoles[HoleNum].mEffects[theWeapon] != null)
            mHoles[HoleNum].mEffects[theWeapon].Shot();
        mHoles[HoleNum].Holes[mHoles[HoleNum].DrawIndex].Shot(theDamage);
    }
}
