/*
 * BSState.java
 *
 * Created on May 12, 2007, 11:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public abstract class BSState {
    BSMainApp mApp;
    BSGraphics mGraphics;
    protected boolean mIsEnteringState;
    protected boolean mIsExitingState;
    
    /** Creates a new instance of BSState */
    public BSState() {
        mIsEnteringState = false;
        mIsExitingState = false;
    }
    public abstract void Init();
    public abstract void EnterState();
    public abstract void ExitState();
    public abstract void Update();
    public abstract void Draw();
    public abstract void GetKeyPressed(int KeyPressed);
    public abstract void Destroy();
}
