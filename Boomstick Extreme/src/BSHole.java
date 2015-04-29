/*
 * BSHole.java
 *
 * Created on May 13, 2007, 11:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSHole {
    int X, Y;
    private BSSprite HoleSprite;
    
    final static int HOLE_EMPTY = 0;
    final static int HOLE_FILLED = 1;
    final static int HOLE_SEALED = 2;
    final static int HOLE_BURNING = 3;
    
    final static int ENTITY_EMPTY = 0;
    final static int ENTITY_POPSTER = 1;
    final static int ENTITY_JUMPSTER = 2;
    final static int ENTITY_HEALTHPACK = 3;
    final static int ENTITY_AMMOPACK = 4;
    
    int mLifeSpan;
    
    /** Creates a new instance of BSHole */
    public BSHole() {
        
    }
    
    public void Init(BSHoleManager theMgr)
    {
        HoleSprite = theMgr.mApp.mGraphics.NewSprite(theMgr.mApp.mGameCanvas.mResources.GetImageThrow("Popster"), 30, 38);
        HoleSprite.setRefPixel(0, 0);
        int Hole[] = {6};
        HoleSprite.addFrameSequence(Hole);
        HoleSprite.playSequence(0, false);
    }
    
    public void Render()
    {
        HoleSprite.setPosition(X, Y);
        HoleSprite.Render();
    }
    
    public void Reset()
    {
        
    }
    
    public void Update()
    {
        HoleSprite.Update();
    }
    
    public void Shot(int theDamage)
    {
        //Calls this when the hole is shot.
        //in this case, nothing happens.
    }
}
