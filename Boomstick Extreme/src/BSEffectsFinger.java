/*
 * BSEffectsFinger.java
 *
 * Created on June 27, 2007, 4:12 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSEffectsFinger extends BSEffects {
    
    /** Creates a new instance of BSEffectsFinger */
    public BSEffectsFinger(BSHoleManager theMgr, BSMultiHole theHole) {
        Init("FingerFX",48 , 48, 0, 0, 24, 24, theMgr, theHole);
        int FX1[] = { 0, 1, 2, 3, 4, 5, 6 };
        int FX2[] = { 7, 8, 9, 10, 11, 12, 6 };
        int FX3[] = { 14, 15, 16, 17, 18, 19, 6 };
        int FX4[] = { 21, 22, 23, 24, 25, 26, 6 };
        AddEffect(FX1);
        AddEffect(FX2);
        AddEffect(FX3);
        AddEffect(FX4);
    }
    
}
