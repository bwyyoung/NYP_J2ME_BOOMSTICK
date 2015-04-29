/*
 * BSEffectsShotgun.java
 *
 * Created on June 27, 2007, 4:22 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSEffectsShotgun extends BSEffects {
    
    /** Creates a new instance of BSEffectsShotgun */
    public BSEffectsShotgun(BSHoleManager theMgr, BSMultiHole theHole) {
        Init("BoomstickFX" , 40, 60, 0, 0, 20, 30, theMgr, theHole);
        int Shotg[] = { 0, 1, 2, 3, 4, 5 };
        AddEffect(Shotg);
    }    
}
