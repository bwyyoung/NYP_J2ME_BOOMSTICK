import javax.microedition.lcdui.Graphics;
/*
 * BSWinScreen.java
 *
 * Created on June 29, 2007, 2:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Bryan
 */
public class BSWinScreen {
    BSGameCanvas mApp;
    BSSprite Boomsticker;
    
    /** Creates a new instance of BSWinScreen */
    public BSWinScreen(BSGameCanvas theCanvas) {
        mApp = theCanvas;
        Boomsticker = mApp.mGraphics.NewSprite(mApp.mResources.GetImageThrow("Boomsticker"), 220, 176);
        int Seq[] = { 0, 1 };
        Boomsticker.addFrameSequence(Seq);
        Boomsticker.playSequence(0, true);
    }
    
    public void Update()
    {
        Boomsticker.setPosition(0, 0);
        Boomsticker.Update();
    }
    
    public void Draw()
    {
        Boomsticker.Render();
        mApp.mGraphics.drawBMString("Mission Accomplished!", 110, 20, 0, 255, 255, 255);
        mApp.mGraphics.drawBMString("Hamsters Killed: ", 110, 40, 0, 255, 255, 255);
        mApp.mGraphics.drawBMString("Press any key to continue...", 110, 160, 0, 255, 255, 255);
    }
}
