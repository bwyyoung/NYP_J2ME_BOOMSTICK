/*
 * BSMainApp.java
 *
 * Created on May 9, 2007, 10:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

/**
 *
 * @author Bryan
 */
public class BSMainApp {
    public boolean is_RightHanded;
    public boolean music_is_on;
    public boolean sound_is_on;
    public BSCanvas mCanvas;
    
    BSGraphics mGraphics;
    BSStateManager mStateManager;
    BSLoadScreen mLoadScreen;
    BSGameCanvas mGameCanvas;
    BSResourceManager mResourceManager;
    
    /* Cosmo's Stuff */
    State_Intro mState_Intro;
    BS_MenuManager mMenuManager;
    int mCurLevel;
    String mLevelPaths[];
    
    RecordStore Rec;
    boolean UnlockedLevels[];
    
    /** Creates a new instance of BSMainApp */
    //BASE
    public BSMainApp(BSCanvas theCanvas) {
        
        //is_RightHanded = true;
        //music_is_on = sound_is_on = true;
        try
        {
            //RecordStore.deleteRecordStore("Boomstick");
            Rec = RecordStore.openRecordStore("Boomstick", true, RecordStore.AUTHMODE_ANY, true);
            
            byte b[] = {0};
            
            while (Rec.getNumRecords() <= 3)
                Rec.addRecord(b, 0, b.length);
            
            byte bData[] = Rec.getRecord(1);
            if (bData[0] == 1)
                is_RightHanded = true;
            else
                is_RightHanded = false;
            if (bData[1] == 1)
                music_is_on = true;
            else
                music_is_on = false;
            if (bData[2] == 1)
                sound_is_on = true;
            else
                sound_is_on = false;
        }
        catch(Exception e)
        {
            is_RightHanded = music_is_on = sound_is_on = true;
        }
        
        mCanvas = theCanvas;
        mStateManager = new BSStateManager(this);
        mGraphics = new BSGraphics(this);
        mResourceManager = new BSResourceManager(this);
        mMenuManager = new BS_MenuManager(mStateManager);
        
        mLevelPaths = null;
        
        mGameCanvas = new BSGameCanvas(mMenuManager);
        mState_Intro = new State_Intro(mMenuManager);
        mMenuManager.LoadMenus();   
        
        //Loads to Main Game
        //mLoadScreen = new BSLoadScreen(mMenuManager, "/Resources.txt", mGameCanvas.mResources, mGameCanvas, true);
        //Loads to Main Menu
        mLoadScreen = new BSLoadScreen(mMenuManager, "/Resource_Menu.txt", mMenuManager.mResources, mState_Intro, true);
        
        mStateManager.SetState(mLoadScreen);
        mCurLevel = 0;
        //mStateManager.SetState(mGameCanvas);
    }
    
    // Put updating and logic code here
    //NOMINAL
    public void Update()
    {
        mStateManager.Update();
    }
    
    public void RestartGame()
    {
        mGameCanvas.Destroy();
        mGameCanvas = null;
        System.gc();
        mGameCanvas = new BSGameCanvas(mMenuManager);
        mState_Intro = new State_Intro(mMenuManager);
        mMenuManager.LoadMenus();   
        
        //Loads to Main Game
        //mLoadScreen = new BSLoadScreen(mMenuManager, "/Resources.txt", mGameCanvas.mResources, mGameCanvas, true);
        //Loads to Main Menu
        mLoadScreen = new BSLoadScreen(mMenuManager, "/Resource_Menu.txt", mMenuManager.mResources, mState_Intro, true);
        
        mStateManager.SetState(mLoadScreen);
        mCurLevel = 0;
    }
    
    public void LoadNextLevel()
    {
        mGameCanvas.Destroy();
        mGameCanvas = null;
        System.gc();
        mGameCanvas = new BSGameCanvas(mMenuManager);
        mCurLevel ++;
        mGameCanvas.StartLevel(mLevelPaths[mCurLevel]);
        BSLoadScreen LoadScreen = new BSLoadScreen(mMenuManager, "/Resources.txt", mGameCanvas.mResources, mGameCanvas, false);
        mStateManager.SetState(LoadScreen);
    }
    
    // Put BSImage rendering code here
    //NOMINAL
    public void Draw()
    {
        mStateManager.Draw();
    }
    
    // Flip the back buffer here
    //NOMINAL
    public void Render(Graphics g)
    {
        mGraphics.Flip(g);
    }
    
    public void keyPressed(int keyCode)
    {
        mStateManager.mState.GetKeyPressed(keyCode);
    }
    
    public void keyReleased(int keyCode)
    {
        
    }
    
    //Call this function if you want to change the key mode
    public void SetKeysMode(boolean Right_Handed)
    {
        is_RightHanded = Right_Handed;
        mMenuManager.SetButtonMode(Right_Handed);
    }
    
    //Call this function if you want to toggle sound On/Off
    public void SetSound(boolean On)
    {
        sound_is_on = On;
    }
    
    //Call this function if you want to toggle music On/Off
    public void SetMusic(boolean On)
    {
        music_is_on = On;
    }
}
