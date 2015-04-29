/*
 * BSAmmo.java
 *
 * Created on June 22, 2007, 12:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSAmmo extends BSItem {
    
    /** Creates a new instance of BSAmmo */
    public BSAmmo(BSHoleManager theMgr, BSMultiHole theHole) {
        Init(theMgr, theHole, "Ammo", 3000);
    }
    
    public void Effect()
    {
        mMgr.mApp.mGameCanvas.mPlayerModel.AddScore(50);
        mMgr.mApp.mGameCanvas.mPlayerModel.sStock++;
    }
}
