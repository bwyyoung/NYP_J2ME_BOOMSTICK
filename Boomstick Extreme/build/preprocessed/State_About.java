/*
 * State_About.java
 *
 * Created on June 29, 2007, 3:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class State_About extends BSState{
    public BS_MenuManager mMM;  //Instance to menu manager
    Imago mOk;
    /** Creates a new instance of State_About */
    public State_About(BS_MenuManager theManager) {
        mMM = theManager;
        mMM.mSM.NewState(this);
    }
    public void Init() {
        mOk = new Imago(mMM.mResources.GetImageThrow("Ok"));
    }
    public void EnterState()
    {
        
    }
    public void ExitState()
    {
        mMM.mSM.SetState(mMM.mStates[mMM.MAIN]);
    }
    public void Update()
    {
        if (mApp.is_RightHanded)
            mOk.setPosition(170,4);
        else
            mOk.setPosition(4,4);
    }
    public void Draw()
    {
        mOk.Render();
        if (mApp.is_RightHanded)
            mApp.mGraphics.drawBMTextBox("Boomstick Extreme is created by Simian Softwerks.",0,0,29,0,255,255,255,BSGraphics.LEFT|BSGraphics.TOP);
        else
            mApp.mGraphics.drawBMTextBox("Boomstick Extreme is created by Simian Softwerks.",50,0,29,0,255,255,255,BSGraphics.LEFT|BSGraphics.TOP);
        
    }
    public void GetKeyPressed(int KeyPressed) {
        if (KeyPressed == mMM.B_TOP) ExitState();
    }
    public void Destroy()
    {
        mOk = null;
    }
}
