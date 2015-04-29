/*
 * State_ExitMenu.java
 *
 * Created on May 15, 2007, 11:15 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Israel
 */
public class State_ExitMenu extends BSState {
    
    private int mCursor;        //The option which the cursor is pointing to
    private int mNumOptions;    //The total number options in the menu
    public BS_MenuManager mMM;  //Instance to menu manager
    
    /** Creates a new instance of State_ExitMenu */
    public State_ExitMenu(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
        mCursor = 1;
        mNumOptions = 2;
    }
    
    public void Init()
    {
        
    }
    
    //NULL
    public void Update() {
        
    }
    
    //NULL
    public void Draw() {
        System.out.println("Exit Menu"+mCursor);
    }
    
    //NULL
    /* Gets the key pressed and passes into the parameter */
    public void GetKeyPressed(int KeyPressed)
    {
        if (KeyPressed == mMM.B_DOWN || KeyPressed == mMM.B_RIGHT) {
            Scroll(true);
        }
        else if (KeyPressed == mMM.B_UP || KeyPressed == mMM.B_LEFT) {
            Scroll(false);
        }
        else if (KeyPressed == mMM.B_CTR) Select();
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
    }
    
    //BASE
    /* Performs an action (like traversing into another menu)
     * when an option in the menu is selected. */
    public void Select() {
        if (mCursor == 0) {      
            mApp.mCanvas.mApp.notifyDestroyed();
        }
        else if (mCursor == 1) { //"No" option: Back To Main Menu
            mMM.mSM.SetState(mMM.mStates[0]);
        }
    }
    
    public void EnterState()
    {
        mIsEnteringState = true;
    }
    
    public void ExitState()
    {
        mIsExitingState = true;
    }
    public void Destroy()
    {
        
    }
}
