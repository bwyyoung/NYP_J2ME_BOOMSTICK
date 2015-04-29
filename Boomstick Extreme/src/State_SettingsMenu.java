/*
 * State_SettingsMenu.java
 *
 * Created on May 15, 2007, 11:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.*;
import javax.microedition.rms.*;

/**
 *
 * @author Israel
 */

public class State_SettingsMenu extends BSState{
    
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    public BS_MenuManager mMM;  //Instance to menu manager
    
    //Images
    private BSImage imBackground_Background;
    private BSImage imBackground;
    private BSImage imGameMode[];
    private BSImage imOk;
    private Imago imTick[];
    private int mOffset;
    private BSImage imPencil;
    private int mPencilPosX[];
    private int mPencilPosY[];
    
    //Animation
    private int mSpeed;
    private long mStartTime;
    private long mDuration;
    
    /** Creates a new instance of State_SettingsMenu */
    public State_SettingsMenu(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 0;
        mNumOptions = 3;
    }
    
    public void Init()
    {
        int distance = 200;
        
        imBackground_Background = mMM.mResources.GetImageThrow("MainMenu");
        imBackground = mMM.mResources.GetImageThrow("Settings");
        imGameMode = new BSImage [2];
        imGameMode[0] = mMM.mResources.GetImageThrow("RightHanded");
        imGameMode[1] = mMM.mResources.GetImageThrow("LeftHanded");
        imOk = mMM.mResources.GetImageThrow("Ok");
        imTick = new Imago[2];
        imTick[0] = new Imago(mMM.mResources.GetImageThrow("Tick"));
        imTick[1] = new Imago(mMM.mResources.GetImageThrow("Tick"));
        
        mPencilPosX = new int [mNumOptions];
        mPencilPosY = new int [mNumOptions];
        mPencilPosX[0] = 100; mPencilPosY[0] = 34;
        mPencilPosX[1] = 100; mPencilPosY[1] = 50;
        mPencilPosX[2] = 120; mPencilPosY[2] = 80;
        imPencil = mMM.mResources.GetImageThrow("Pencil");
        
        mOffset = 56;
        if (mApp.is_RightHanded) {
            imBackground.setPosition(0-distance,0);
            imGameMode[0].setPosition(0-distance,0);
            imGameMode[1].setPosition(0-distance,0);
            imOk.setPosition(170+distance,4);
            imTick[0].setPosition(26-distance,45);
            imTick[1].setPosition(28-distance,60);
            imPencil.setPosition(mPencilPosX[mCursor],mPencilPosY[mCursor]);
        }
        else {
            imBackground.setPosition(mOffset+distance,0);
            imGameMode[0].setPosition(mOffset+distance,0);
            imGameMode[1].setPosition(mOffset+distance,0);
            imOk.setPosition(4-distance,4);
            imTick[0].setPosition(26 + mOffset+distance,45);
            imTick[1].setPosition(28 + mOffset+distance,60);
            imPencil.setPosition(mPencilPosX[mCursor]+mOffset,mPencilPosY[mCursor]);
        }
        
        //Animation stuff
        mSpeed = distance / mApp.mCanvas.FPS;
    }
    
    //NULL
    public void Update() {
        if (mIsEnteringState) {
            if (System.currentTimeMillis() > mStartTime + mDuration) {
                UpdatePositions();
                mIsEnteringState = false;
            }
            else if (mApp.is_RightHanded) {
                imBackground.Translate(mSpeed,0);
                imGameMode[0].Translate(mSpeed,0);
                imGameMode[1].Translate(mSpeed,0);
                imTick[0].Translate(mSpeed,0);
                imTick[1].Translate(mSpeed,0);
                
                imOk.Translate(mSpeed,0);
            }
            else if (!mApp.is_RightHanded) {
                imBackground.Translate(0-mSpeed,0);
                imGameMode[0].Translate(0-mSpeed,0);
                imGameMode[1].Translate(0-mSpeed,0);
                imTick[0].Translate(0-mSpeed,0);
                imTick[1].Translate(0-mSpeed,0);
                
                imOk.Translate(0-mSpeed,0);
            }
        }
        if (mIsExitingState) {
            if (System.currentTimeMillis() > mStartTime + mDuration) {
                mIsExitingState = false;
                mMM.mSM.SetState(mMM.mStates[0]);
            }
            else if (mApp.is_RightHanded) {
                imBackground.Translate(0-mSpeed,0);
                imGameMode[0].Translate(0-mSpeed,0);
                imGameMode[1].Translate(0-mSpeed,0);
                imTick[0].Translate(0-mSpeed,0);
                imTick[1].Translate(0-mSpeed,0);
                
                imOk.Translate(mSpeed,0);
            }
            else if (!mApp.is_RightHanded) {
                imBackground.Translate(mSpeed,0);
                imGameMode[0].Translate(mSpeed,0);
                imGameMode[1].Translate(mSpeed,0);
                imTick[0].Translate(mSpeed,0);
                imTick[1].Translate(mSpeed,0);
                
                imOk.Translate(0-mSpeed,0);
            }
        }
    }
    
    //NULL
    public void Draw() {
        imBackground_Background.Render();
        imBackground.Render();
        if (mApp.is_RightHanded) {
            imGameMode[0].Render();
        }
        else {
            imGameMode[1].Render();
        }
        imOk.Render();
        if (mApp.sound_is_on) imTick[0].Render();
        if (mApp.music_is_on) imTick[1].Render();
        if (!mIsEnteringState && !mIsExitingState) imPencil.Render();
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
        else if (KeyPressed == mMM.B_TOP) ExitState();
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
        if (mApp.is_RightHanded)
            imPencil.setPosition(mPencilPosX[mCursor], mPencilPosY[mCursor]);
        else
            imPencil.setPosition(mPencilPosX[mCursor]+mOffset,mPencilPosY[mCursor]);
    }
    
    //BASE
    /* Performs an action (like traversing into another menu)
     * when an option in the menu is selected. */
    public void Select() {
        if (mCursor == 0) {      //Turn Sound Effects On/Off
            mApp.SetSound(!mApp.sound_is_on);
        }
        else if (mCursor == 1) { //Turn Music On/Off
            mApp.SetMusic(!mApp.music_is_on);
        }
        else if (mCursor == 2) { //Set Left-Handed or Right-Handed
            mApp.SetKeysMode(!mApp.is_RightHanded);
            UpdatePositions();
        }
    }
    
    public void UpdatePositions()
    {
        if (mApp.is_RightHanded) {
            imBackground.setPosition(0,0);
            imGameMode[0].setPosition(0,0);
            imGameMode[1].setPosition(0,0);
            imOk.setPosition(170,4);
            imTick[0].setPosition(26,45);
            imTick[1].setPosition(28,60);
            imPencil.setPosition(mPencilPosX[mCursor],mPencilPosY[mCursor]);
        }
        else {
            imBackground.setPosition(mOffset,0);
            imGameMode[0].setPosition(mOffset,0);
            imGameMode[1].setPosition(mOffset,0);
            imOk.setPosition(4,4);
            imTick[0].setPosition(26 + mOffset,45);
            imTick[1].setPosition(28 + mOffset,60);
            imPencil.setPosition(mPencilPosX[mCursor]+mOffset,mPencilPosY[mCursor]);
        }
    }
    
    public void EnterState()
    {
        mStartTime = System.currentTimeMillis();
        mDuration = 1000;
        mIsEnteringState = true;
    }
    
    public void ExitState()
    {
        try
        {
            byte bData[] = new byte[3];
            if (mApp.is_RightHanded)
                bData[0] = 1;
            else
                bData[0] = 0;
            if (mApp.music_is_on)
                bData[1] = 1;
            else
                bData[1] = 0;
            if (mApp.sound_is_on)
                bData[2] = 1;
            else
                bData[2] = 0;
            
            mApp.Rec.setRecord(1, bData,0, bData.length);
        }
        catch (Exception e)
        {
            
        }
        
        mStartTime = System.currentTimeMillis();
        mDuration = 1000;
        mIsExitingState = true;
    }
    public void Destroy()
    {
        imBackground_Background = null;
        imBackground = null;
        imGameMode[0] = imGameMode[1] = null;
        imOk = null;
        imTick[0] = imTick[1] = null;
        imPencil = null;
    }
}
