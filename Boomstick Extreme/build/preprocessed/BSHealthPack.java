/*
 * BSHealthPack.java
 *
 * Created on June 21, 2007, 11:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSHealthPack extends BSItem {
    
    /** Creates a new instance of BSHealthPack */
    public BSHealthPack(BSHoleManager theMgr, BSMultiHole theHole) {
        Init(theMgr, theHole, "Health", 3000);
    }
    
    public void Effect()
    {
        mMgr.mApp.mGameCanvas.mPlayerModel.Damage(-1);
        mMgr.mApp.mGameCanvas.mPlayerModel.AddScore(50);
    }
}
