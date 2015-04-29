/*
 * BSStateManager.java
 *
 * Created on May 12, 2007, 11:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import javax.microedition.lcdui.*;

/**
 *
 * @author Bryan
 */
public class BSStateManager {
    BSMainApp mApp;
    BSState mState;
    BSState mPrevState;
    private boolean bInit;
    
    /** Creates a new instance of BSStateManager */
    //NORMINAL
    public BSStateManager(BSMainApp theApp) {
        mApp = theApp;
        bInit = false;
    }
    
    //NORMINAL
    public BSState NewState(BSState theState)
    {
        theState.mApp = mApp;
        theState.mGraphics = mApp.mGraphics;
        
        return theState;
    }
    
    //NORMINAL
    public void SetState(BSState theState)
    {
        if (bInit)
            mPrevState = mState;
        else
            mPrevState = mApp.mLoadScreen;
        mState = theState;
        bInit = true;
        mState.EnterState();
    }
    
    //NORMINAL
    public void Update()
    {
        mState.Update();
    }
    
    //NORMINAL
    public void Draw()
    {
        mState.Draw();
    }
}
