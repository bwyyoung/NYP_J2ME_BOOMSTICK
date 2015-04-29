/*
 * BSMainApp.java
 *
 * Created on May 9, 2007, 10:35 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.lcdui.*;

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
    //State_Intro mState_Intro;
    BS_MenuManager mMenuManager;
    
    /** Creates a new instance of BSMainApp */
    //BASE
    public BSMainApp(BSCanvas theCanvas) {
        is_RightHanded = true;
        music_is_on = sound_is_on = true;
        mCanvas = theCanvas;
        mStateManager = new BSStateManager(this);
        mGraphics = new BSGraphics(this);
        mResourceManager = new BSResourceManager(this);
        
        //mLoadScreen = new BSLoadScreen(mStateManager);
        //mStateManager.SetState(mLoadScreen);
        
        mMenuManager = new BS_MenuManager(mStateManager);
        
        /* Cosmo's Stuff */
        // int Shot[] = {0,1,2,3,4,5};
        //mStateManager.SetState(mMenuManager.mStates[BS_MenuManager.LOAD]);
        
        mGameCanvas = new BSGameCanvas(mMenuManager);
        //mState_Intro = new State_Intro(mMenuManager);
        mMenuManager.LoadMenus();
        mLoadScreen = new BSLoadScreen(mMenuManager, "/Resource_Menu.txt", mGameCanvas.mResources, mMenuManager.mStates[mMenuManager.MAIN], true);
        
        mStateManager.SetState(mLoadScreen);
        //mStateManager.SetState(mGameCanvas);
    }
    
    // Put updating and logic code here
    //NOMINAL
    public void Update()
    {
        mStateManager.Update();
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
